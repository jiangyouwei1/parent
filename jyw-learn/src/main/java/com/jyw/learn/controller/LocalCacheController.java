package com.jyw.learn.controller;

import com.jyw.learn.model.GradesInfo;
import com.jyw.learn.service.ILocalCacheService;
import com.jyw.learn.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/local")
public class LocalCacheController {
    @Autowired
    private ILocalCacheService localCacheService;

    @RequestMapping("/putLocalCache")
    @ResponseBody
    public Response putLocalCache(@RequestBody GradesInfo gradesInfo){
        localCacheService.saveGragdeLocalCache(gradesInfo);
        return new Response(gradesInfo,Response.SUCCESS);
    }

    @RequestMapping("/getLocalCache/{id}")
    @ResponseBody
    public Response getLocalCache(@PathVariable("id") Integer id){
        GradesInfo gradesInfo = localCacheService.getGradeLocalCache(id);
        return new Response(gradesInfo,Response.SUCCESS);
    }

    public static void main(String[] args) {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("testId","测试value");
        dataMap.put("testId2","测试value");
        System.out.println(dataMap.keySet());
    }

    /**
     * 模拟数据初始化
     * @return
     */
    @RequestMapping("/dataInit")
    @ResponseBody
    public Response dataInit(){
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("testId"+i,"测试value"+i);
            System.out.println(dataMap.keySet());
            mapList.add(dataMap);
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("datainit",mapList);
        localCacheService.saveDataLocalCache(dataMap);
        return new Response("数据初始化成功",Response.SUCCESS);
    }
    @RequestMapping("/getData")
    @ResponseBody
    public Response getData(@RequestParam String keyName){
        return new Response(localCacheService.getInitDataInfo(keyName),Response.SUCCESS);
    }

}

