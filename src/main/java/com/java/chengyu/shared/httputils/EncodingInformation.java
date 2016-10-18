package com.java.chengyu.shared.httputils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodingInformation
{
   public static String getEncodingFromBytes(byte[] paramArrayOfByte)
   {
      String str1 = null;
      int i = paramArrayOfByte.length;
      if (i > 2048)
      {
         i = 2048;
      }
      if (i > 0)
      {
         try
         {
            String str2 = new String(paramArrayOfByte, 0, i, "8859_1");
            str1 = getEncodingFromString(str2);
         }
         catch (Exception localException)
         {
            localException.printStackTrace();
         }
      }
      return str1;
   }

   public static String getEncodingFromString(String paramString)
   {
      String str1 = "";
      if (paramString.indexOf("<?xml") >= 0)
      {
         int i = paramString.indexOf("encoding");
         if (i >= 0)
         {
            i = paramString.indexOf("\"", i + 1);
            if (i > 0)
            {
               int j = paramString.indexOf("\"", i + 1);
               if ((i >= 0) && (j >= 0) && (j - i < 20))
               {
                  str1 = paramString.substring(i + 1, j);
                  return str1.toLowerCase();
               }
            }
         }
      }
      if (str1.equals(""))
      {
         String str2 = "<meta[^<]+content=(.*?)\\s*>";
         Matcher localMatcher = Pattern.compile(str2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CASE).matcher(paramString);
         while (localMatcher.find())
         {
            String str3 = localMatcher.group(1);
            str3 = str3.replace("\"", "").replace("'", "").replace("/", "");
            int k = str3.indexOf(";");
            if (k >= 0)
            {
               str3 = str3.substring(k + 1).replace(" ", "").toLowerCase();
               if (str3.startsWith("charset"))
               {
                  str3 = str3.substring("charset=".length()).toLowerCase();
                  return str3;
               }
            }
         }
      }
      return "gb2312";
   }

   public static void main(String[] paramArrayOfString)
   {
      String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><html><head><meta http-equiv=content-type content=\"text/html; charset=UTF-8\" ><title>&amp;���������� - Google ����</title><meta http-equiv=refresh content=\"1;url=http://www.baidu.com/\">";
      System.out.println(getEncodingFromString(str));
   }
}