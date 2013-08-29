package com.splendidcode.angular.sample.login.Shiro;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class SinglePageApplicationAuthFilter extends AuthenticationFilter {
   @Override
   protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
      if(isLoginRequest(request, response)) {
         return true;
      }
      else {
         HttpServletResponse httpResponse = WebUtils.toHttp(response);
         httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         return false;
      }
   }
}
