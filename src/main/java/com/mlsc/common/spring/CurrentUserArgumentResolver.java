package com.mlsc.common.spring;

import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取当前登录用户
 */
public class CurrentUserArgumentResolver implements WebArgumentResolver {
    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        if (methodParameter.getParameterType() != null
                && methodParameter.getParameterType().equals(User.class)) {
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            User user = LoginStatusUtils.getUserByTicketId(request.getParameter("ticketId"));
            return user;
        }
        return UNRESOLVED;
    }
}