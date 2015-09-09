package com.java.chengyu.shared.objects;

import java.util.Collection;
import java.util.HashMap;

/*
 * This is a debug toolkit to print the complex object
 */

public class Printer
{
   public static String printHashMap(HashMap<String, ? > map, boolean allKeys)
   {
      StringBuffer sb = new StringBuffer();
      if (allKeys)
      {
         Collection<String> en = map.keySet();
         for (String tmp : en)
         {
            sb.append(tmp);
            sb.append("\r\n");
         }
      }
      else
      {
         Collection< ? > en = map.values();
         for (Object obj : en)
         {
            sb.append(obj.toString());
            sb.append("\r\n");
         }
      }

      return sb.toString();
   }
}
