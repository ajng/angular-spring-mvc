package com.splendidcode.angular.sample.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.ThreadState;
import org.junit.After;
import org.junit.AfterClass;

public class ShiroAwareTest {

   private static ThreadState subjectThreadState;

   protected void setSubject(Subject subject) {
      clearSubject();
      subjectThreadState = createThreadState(subject);
      subjectThreadState.bind();
   }

   protected Subject getSubject() {
      return SecurityUtils.getSubject();
   }

   protected ThreadState createThreadState(Subject subject) {
      return new SubjectThreadState(subject);
   }

   protected void clearSubject() {
      doClearSubject();
   }

   private static void doClearSubject() {
      if(subjectThreadState != null) {
         subjectThreadState.clear();
         subjectThreadState = null;
      }
   }

   protected static void setSecurityManager(SecurityManager securityManager) {
      SecurityUtils.setSecurityManager(securityManager);
   }

   protected static SecurityManager getSecurityManager() {
      return SecurityUtils.getSecurityManager();
   }
   
   @After
   public void after(){
     clearSubject(); 
   }

   @AfterClass
   public static void tearDownShiro() {
      doClearSubject();
      try {
         SecurityManager securityManager = getSecurityManager();
         LifecycleUtils.destroy(securityManager);
      } catch (UnavailableSecurityManagerException e) {
         //nop
      }
      setSecurityManager(null);
   }
}
