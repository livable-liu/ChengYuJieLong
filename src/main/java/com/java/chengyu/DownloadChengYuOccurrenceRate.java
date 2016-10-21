package com.java.chengyu;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.java.chengyu.shared.domutils.HtmlCleanerUtil;
import com.java.chengyu.shared.domutils.W3cDocumentUtil;
import com.java.chengyu.shared.fileutils.FileUtils;
import com.java.chengyu.shared.httputils.HTMLUnit;
import com.java.chengyu.shared.httputils.HTTPSimulator;

/**
 * Add ChengYu occurrence rate for distance computing
 * e.g.
 * 1. '招蜂引蝶' 百度为您找到相关结果约2,870,000个
 * 2. '招蜂惹蝶' 百度为您找到相关结果约122,000个
 * 3. '赵公元帅' 百度为您找到相关结果约215,000个
 * 4. '招架不住' 百度为您找到相关结果约8,360,000个
 * 5. '赵家姊妹' 百度为您找到相关结果约2,670个
 * 6. '赵郊坑肉' 百度为您找到相关结果约255个
 */
public class DownloadChengYuOccurrenceRate
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   public static void main(String[] args)
   {
      System.out.println("Enter download ChengYu occurence rate from Baidu!");
      FUNCTION.info("Enter download!!");
      PropertyConfigurator.configure("./src/main/java/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
      String path = "./chengyu.txt";
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      StringBuffer sb = new StringBuffer();
      if (content != null)
      {
         int start = 0;
         String splitter = "\\s+";
         int index = content.indexOf("\r\n", start);

//         HTMLUnit unit = new HTMLUnit("http://www.baidu.com/s?wd=abc");
         
         while (index > 0 && index + "\r\n".length() <= content.length())
         {
            String tmp = content.substring(start, index);
            String[] array = tmp.split(splitter);
            String countStr = "";

            // get link from Baidu
            try
            {
               /*
                * Download by HTTPSimulator
                */
//               String temp = HTTPSimulator.getHTMLPageFromUrl("http://cn.bing.com/search?q="
//                     + URLEncoder.encode(array[0].trim(), "UTF-8"),
//                     "http://cn.bing.com/", "UTF-8");
               String temp = HTTPSimulator.getHTMLPageFromUrl("http://www.baidu.com/s?wd="
                     + URLEncoder.encode(array[0].trim(), "UTF-8"),
                     "http://www.baidu.com/", "UTF-8");
               if (temp != null && !"".equals(temp))
               {
                  // Get by xpath
//                  TagNode node = HtmlCleanerUtil.parse(temp);
//                  if (node != null)
//                  {
//                     Object[] objects = node.evaluateXPath("//div[@class='nums']/text()");
//                     // get content
//                     if (objects != null && objects.length > 0)
//                     {
//                        String result = objects[0].toString();
//                        countStr = result.replaceAll("[^0-9]", "");
//                     }
//                  }
                  // Get regular expression
//                  Matcher matcher = Pattern.compile("<span\\s*class=\"sb_count\">(.*?)</span>",
//                        Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CASE).matcher(temp);
                  Matcher matcher = Pattern.compile("百度为您找到相关结果约(.*?)个",
                      Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CASE).matcher(temp);
                  if (matcher.find())
                  {
                     countStr = matcher.group(1);
                     countStr = countStr.replaceAll("[^0-9]", "");
                  }
               }
               
               Thread.sleep(1000);
               
               /*
                * Download by HTMLUnit
                */
//               String countStr = HTMLUnit.getHTMLPageFromUrl("http://www.baidu.com/s?wd="
//                     + URLEncoder.encode(array[0].trim(), "UTF-8"), "//*[@class='nums']");
               
//               String countStr = unit.searchFromSearchEngine(array[0].trim(), "//input[@id='kw']", "//input[@id='su']", "//*[@class='nums']");
               
               
               if (!StringUtils.isEmpty(countStr))
               {
                  sb.append(tmp);
                  sb.append(" ");
                  sb.append(countStr.replaceAll("[^0-9]", ""));
                  sb.append("\r\n");
                  System.out.println(tmp + " " + countStr.replaceAll("[^0-9]", ""));
               }
               else
               {
                  FUNCTION.error("pinyin = " + array[0].trim());
               }
            }
            catch (Exception e)
            {
               FUNCTION.error("IOException", e);
               FUNCTION.error("pinyin = " + array[0].trim());
            }
         
            start = index + "\r\n".length();
            index = content.indexOf("\r\n", start);

         }
      }

      try
      {
         FileUtils.writeStringToLocalFile("chengyu_weight.log", sb.toString(), "UTF-8");
      }
      catch (IOException e)
      {
         FUNCTION.error("IOException", e);
      }
      FUNCTION.info("Finish download!!");
      System.out.println("Leave download ChengYu occurence rate from Baidu!");
   }
}
