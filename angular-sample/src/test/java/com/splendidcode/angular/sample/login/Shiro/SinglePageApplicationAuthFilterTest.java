package com.splendidcode.angular.sample.login.Shiro;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class SinglePageApplicationAuthFilterTest {

   private SinglePageApplicationAuthFilter filter;
   @Mock
   private HttpServletResponse response;


   @Before
   public void setup(){
      MockitoAnnotations.initMocks(this);
      filter = new SinglePageApplicationAuthFilter();
   }
   
   @Test
   public void when_requesting_login_url_the_filter_should_succeed() throws Exception {
      filter.setLoginUrl("/login");
      MockHttpServletRequest request = new MockHttpServletRequest("GET", "/login");
      boolean result = filter.onAccessDenied(request, response);
      assertThat(result).isTrue();
   }
   
   @Test
   public void when_requesting_other_url_ther_filter_should_fail() throws Exception {
      filter.setLoginUrl("/login");
      MockHttpServletRequest request = new MockHttpServletRequest("GET", "/logout");
      boolean result = filter.onAccessDenied(request, response);
      assertThat(result).isFalse();
   }
   
   @Test
   public void when_requesting_other_url_ther_filter_should_set_unauthorized_status_code() throws Exception {
      filter.setLoginUrl("/login");
      MockHttpServletRequest request = new MockHttpServletRequest("GET", "/home");
      filter.onAccessDenied(request, response);
      verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
   }
}
