package com.jyw.learn.zuul.filter;

import com.jyw.learn.zuul.bean.AjaxResult;
import com.jyw.learn.zuul.feign.OauthFeign;
import com.jyw.learn.zuul.util.RedisUUId;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器
 */
@Component
public class AccessTokenFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessTokenFilter.class);
    @Override
    public String filterType() {
        return "pre";//前后i过滤器
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Resource
    private RedisUUId redisUUId;
    @Autowired
    OauthFeign oauthFeign;
    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("请求url==={}",request.getRequestURI());
        //增加zuul转发标识，===start
        String secretKey = redisUUId.create("secretKey");
        ctx.getZuulRequestHeaders().remove("secretKey");
        ctx.getZuulRequestHeaders().put("secretKey",secretKey);
        //增加zuul转发标识，===end
        if(request.getRequestURI().contains("/userlogin/getVerfyCode")||request.getRequestURI().contains("/userlogin/login")){
            return null;
        }

        String accessToken = request.getHeader("token");
        if(StringUtils.isEmpty(accessToken)){
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(403);// 返回错误码
            ctx.setResponseBody(buildResponse(10901,"Request illegal!the token is null"));// 返回错误内容
            ctx.set("isSuccess", false);
            return null;
        }
        //校验token是否有效
        AjaxResult checkResult = oauthFeign.checkToken(accessToken);

        if(checkResult.getCode().equals("10992")){
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(500);// 返回错误码
            ctx.setResponseBody(buildResponse(10900,checkResult.getMsg()));// 返回错误内容
            ctx.set("isSuccess", false);
            return null;
        }
        return null;
    }

    public static String buildResponse(int code, String msg) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"code\":").append("\"").append(code).append("\"").append(",");
        builder.append("\"msg\":").append("\"").append(msg).append("\"");
        builder.append("}");
        return builder.toString();
    }
}
