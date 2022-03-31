package com.jyw.learn.oauth.interceptor;

import cn.hutool.core.util.StrUtil;
import com.jyw.learn.oauth.util.RedisUUId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截器，如果用户不是zuul转发请求，而是直接访问服务 拦截并提示
 * @author jyw
 * 21点17分
 */
@Component
public class MyTheadInterceptor implements HandlerInterceptor {

    @Getter
    @Setter
    private RedisUUId redisUUID;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.比较头部是否有secretKey键值对
        //2.校验secretKey对应value值是否有效  都满足 则为zuul转发而来
        String secretKey = request.getHeader("secretKey");
        if(StrUtil.isNotBlank(secretKey)){
            String key = (String) redisUUID.get("secretKey");
            if(!StrUtil.isBlank(key) && secretKey.equals(key)){
                return true;
            }
        }
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("Unauthorized access");
        return false;
    }
}
