package com.jyw.learn.controller;

import com.jyw.learn.model.GradesInfo;
import com.jyw.learn.myrequest.CacheReloadRequest;
import com.jyw.learn.myrequest.DataUpdateRequest;
import com.jyw.learn.myrequest.Request;
import com.jyw.learn.service.IGradeService;
import com.jyw.learn.service.IRequestAsyncProcessService;
import com.jyw.learn.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeController {
    @Autowired
    private IGradeService gradeService;
    @Autowired
    private IRequestAsyncProcessService requestAsyncProcessService;
    @PostMapping("/update")
    @ResponseBody
    public Response updateNum(@RequestBody  GradesInfo gradesInfo){
        Request request = new DataUpdateRequest(gradesInfo,gradeService);
        //将请求路由到对应的内存队列中。然后用跟此队列的线程自动处理请求
        requestAsyncProcessService.process(request);
        return new Response("更新成功",Response.SUCCESS);
    }
    //读取逻辑
    @RequestMapping(value = "/getGradeInfo/{id}",method = RequestMethod.GET)
    @ResponseBody
    public GradesInfo getGradeInfo(@PathVariable("id") Integer id){
        GradesInfo gradesInfo = new GradesInfo();
        gradesInfo.setId(id);
        Request request = new CacheReloadRequest(gradesInfo,gradeService);
        requestAsyncProcessService.process(request);
        // 将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
        // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
        try{
            Long startTime = System.currentTimeMillis();
            Long endTime = 0L;
            Long waitTime = 0L;
            while (true){
                //等待200毫秒
                if(waitTime>200){
                    break;
                }
                //缓存中获取
                GradesInfo gradesInfoCache = gradeService.getGradeByCache(id);
                if(gradesInfoCache != null){
                    return gradesInfoCache;
                }
                Thread.sleep(20);
                endTime = System.currentTimeMillis();
                waitTime = startTime - endTime;
            }
            //缓存中未读到数据 则去库中找
            GradesInfo gradesInfoByDB = gradeService.getGradeById(id);
            if(gradesInfoByDB!=null){
                //将读取到的数据刷入缓存中
                requestAsyncProcessService.process(request);
                return gradesInfoByDB;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
