package com.splendidcode.angular.sample;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

public class IndexControllerTest {

   private MockMvc mockMvc;

   @Before
   public void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).build();
   }
   
   @Test
   public void redirects() throws Exception {
      mockMvc.perform(get("/")).andExpect(redirectedUrl("/ui/sample.html"));
   }
}
