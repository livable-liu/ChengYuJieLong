package com.java.chengyu.shared.objects;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Configure
{
  public static String readValue(String paramString1, String paramString2)
  {
    Properties localProperties = new Properties();
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramString1));
      localProperties.load(localBufferedInputStream);
      localBufferedInputStream.close();
      String str = localProperties.getProperty(paramString2);
      return str;
    }
    catch (Throwable localThrowable) {}
    return null;
  }
  
  public static String readValue(String paramString1, String paramString2, String paramString3)
  {
    String str = null;
    try
    {
      str = readValue(paramString1, paramString2);
      if ((str == null) || (str.equals(""))) {
        str = paramString3;
      }
      return str;
    }
    catch (Throwable localThrowable) {}
    return paramString3;
  }
  
  public static String readFile(String paramString)
  {
    return readFile(paramString, "GBK");
  }
  
  public static String readFile(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    BufferedReader localBufferedReader = null;
    try
    {
      localBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(paramString1), paramString2));
      for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine())
      {
        localStringBuffer.append(str);
        localStringBuffer.append("\r\n");
      }
      localBufferedReader.close();
    }
    catch (Exception localException)
    {
      return "";
    }
    return localStringBuffer.toString();
  }
  
  public static void readProperties(String paramString)
  {
    Properties localProperties = new Properties();
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramString));
      localProperties.load(localBufferedInputStream);
      localBufferedInputStream.close();
      Enumeration localEnumeration = localProperties.propertyNames();
      for (int i = 0; localEnumeration.hasMoreElements(); i++)
      {
        String str1 = (String)localEnumeration.nextElement();
        String str2 = localProperties.getProperty(str1);
        System.out.println(str1 + "=" + str2);
      }
    }
    catch (Throwable localThrowable) {}
  }
  
  public static Map<String, String> getPropertiesMap(String paramString)
  {
    HashMap localHashMap = new HashMap();
    Properties localProperties = new Properties();
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramString));
      localProperties.load(localBufferedInputStream);
      localBufferedInputStream.close();
      Enumeration localEnumeration = localProperties.propertyNames();
      while (localEnumeration.hasMoreElements())
      {
        String str1 = (String)localEnumeration.nextElement();
        String str2 = localProperties.getProperty(str1);
        localHashMap.put(str1, str2);
      }
    }
    catch (Throwable localThrowable)
    {
      return localHashMap;
    }
    return localHashMap;
  }
  
  public static void writeProperties(String paramString1, String paramString2, String paramString3)
  {
    Properties localProperties = new Properties();
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString1);
      localProperties.load(localFileInputStream);
      localFileInputStream.close();
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString1);
      localProperties.setProperty(paramString2, paramString3);
      localProperties.store(localFileOutputStream, "Update '" + paramString2 + "' value");
      localFileOutputStream.close();
    }
    catch (IOException localIOException) {}
  }
  
  public static void writePropertiesMap(String paramString, Map<String, String> paramMap)
  {
    Properties localProperties = new Properties();
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      localProperties.load(localFileInputStream);
      localFileInputStream.close();
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      String str1 = "";
      String str2 = "";
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        str1 = (String)localIterator.next();
        str2 = (String)paramMap.get(str1);
        localProperties.setProperty(str1, str2);
      }
      localProperties.store(localFileOutputStream, "Update 'Map.size() = '" + paramMap.size() + " value");
      localFileOutputStream.close();
    }
    catch (IOException localIOException) {}
  }
  
  public static void test(String paramString)
  {
    Properties localProperties = new Properties();
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      localProperties.load(localFileInputStream);
      localFileInputStream.close();
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      if (localProperties.containsKey("user"))
      {
        localProperties.remove("user");
        localProperties.store(localFileOutputStream, "user=all");
      }
      else
      {
        localProperties.setProperty("user", "all");
        localProperties.store(localFileOutputStream, "Update 'parameterName' value");
      }
      localFileOutputStream.close();
    }
    catch (IOException localIOException) {}
  }
}

