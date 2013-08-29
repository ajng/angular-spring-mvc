package com.splendidcode.angular.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
public class IndexController {

   @Value("${angular.welcome.message:No message set!}")
   String message; 
   
   @Inject
   Environment environment;
   
   @RequestMapping("/")
   public String index(){
      System.out.println("MESSAGE WAS: "+message);    
      return "redirect:/ui/sample.html";
   }
}
