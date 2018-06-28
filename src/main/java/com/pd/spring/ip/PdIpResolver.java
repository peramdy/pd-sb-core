package com.pd.spring.ip;

import com.pd.spring.utils.IpUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pd 2018/3/9.
 */
public class PdIpResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        /*return methodParameter.hasParameterAnnotation(PdIP.class);*/
        boolean flag = methodParameter.hasParameterAnnotation(PdIP.class);
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        PdIP ip = methodParameter.getParameterAnnotation(PdIP.class);
        if (ip != null) {
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            return IpUtil.parseIp(request);
        }
        return null;
    }


}
