package com.java.chengyu;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;

public class TestMe
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   private static String debugLogGenerator(String statement)
   {
      System.out.println("Enter debugLogGenerator : " + statement);
      return "Enter debugLogGenerator : " + statement;
   }

   private static String errorLogGenerator(String statement)
   {
      System.out.println("Enter errorLogGenerator : " + statement);
      return "Enter errorLogGenerator : " + statement;
   }

   public static void main(String[] args)
   {
      System.out.println("Enter parse PinYin Collection file!");
      FUNCTION.info("Enter TestMe!");
      PropertyConfigurator.configure("./src/main/java/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
      FUNCTION.debug(debugLogGenerator("DEBUG"));
      if (FUNCTION.isDebugEnabled())
      {
         FUNCTION.debug(debugLogGenerator("DEBUG ENABLED"));
      }
      FUNCTION.error(errorLogGenerator("ERROR"));
      if (FUNCTION.isEnabledFor(Priority.ERROR))
      {
         FUNCTION.error(errorLogGenerator("ERROR ENABLED"));
      }
      FUNCTION.info("Finish TestMe!");
      System.out.println("Leave parse PinYin Collection file!");
   }

}
