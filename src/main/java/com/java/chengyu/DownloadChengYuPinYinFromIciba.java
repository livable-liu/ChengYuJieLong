package com.java.chengyu;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.htmlcleaner.TagNode;

import com.java.chengyu.shared.domutils.HtmlCleanerUtil;
import com.java.chengyu.shared.fileutils.FileUtils;
import com.java.chengyu.shared.httputils.HTTPSimulator;

/**
 * Optimize ChengYu PinYin source from the Internet!
 *
 */
public class DownloadChengYuPinYinFromIciba
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   public static void main(String[] args)
   {
      System.out.println("Enter optimize ChengYu PinYin Collection2!");
      FUNCTION.info("Enter optimize!!");
      PropertyConfigurator.configure("./src/main/resources/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
      // download pinyin from internet
      String path = "./log/optimized.log";
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      StringBuffer sb = new StringBuffer();
      if (content != null)
      {
         int start = 0;
         String splitter = "\\s+";
         int index = content.indexOf("\r\n", start);

         while (index > 0 && index + "\r\n".length() < content.length() - 1)
         {
            String tmp = content.substring(start, index);
            String[] array = tmp.split(splitter);
            // get content from iciba
            try
            {
               String t = HTTPSimulator.getHTMLPageFromUrl(
                     "http://hanyu.iciba.com/hy/" + URLEncoder.encode(array[0].trim(), "UTF-8") + "/",
                     "http://hanyu.iciba.com/", "UTF-8");
               if (t != null && !"".equals(t))
               {
                  String temp = t;
                  int j = t.indexOf("url=");
                  int k = t.indexOf("\"", j);
                  if (temp.length() < 2000 && j > 0 && k > 0)
                  {
                     temp = t.substring(j + "url=".length(), k);
                     temp = HTTPSimulator.getHTMLPageFromUrl("http://hanyu.iciba.com" + temp,
                           "http://hanyu.iciba.com/hy/" + URLEncoder.encode(array[0].trim(), "UTF-8") + "/",
                           "UTF-8");
                  }

                  // apply to two cases.
                  // 1:<meta http-equiv="refresh" content="0; url=/cizu/168875.shtml" />
                  // 2:301 response
                  if (temp != null && !"".equals(temp))
                  {
                     TagNode node = HtmlCleanerUtil.parse(temp);
                     if (node != null)
                     {
                        Object[] objects = node
                              .evaluateXPath("//div[@class='cy12']/text()");
                        // get content
                        if (objects != null && objects.length > 0)
                        {
                           sb.append(array[0]);
                           sb.append("  ");
                           sb.append(objects[0].toString());
                           sb.append("\r\n");
                           FUNCTION.error(array[0] + "  " + objects[0].toString());
                        }
                        else
                        {
                           Object[] o = node.evaluateXPath("//div[@class='js12']/font/text()");
                           if (o != null && o.length > 0)
                           {
                              sb.append(array[0]);
                              sb.append(" ");
                              sb.append(o[0].toString());
                              sb.append("\r\n");
                              FUNCTION.error(array[0] + "  " + o[0].toString());
                           }
                           else
                           {
                              Object[] oo = node.evaluateXPath("//div[@class='wiki_mod04_l']/a/@href");
                              if (oo != null && oo.length > 0)
                              {
                                 sb.append(array[0]);
                                 sb.append(" ");
                                 sb.append(oo[0].toString());
                                 sb.append("\r\n");
                              }
                           }
                        }
                     }
                  }
                  else
                  {
                     sb.append(array[0]);
                     sb.append(" ");
                     sb.append("\r\n");
                  }
               }
               else
               {
                  sb.append(array[0]);
                  sb.append(" ");
                  sb.append("\r\n");
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
         FileUtils.writeStringToLocalFile("./log/optimized2.log", sb.toString(), "UTF-8");
      }
      catch (IOException e)
      {
         FUNCTION.error("IOException", e);
      }
      FUNCTION.info("Finish optimize!!");
      System.out.println("Leave optimize ChengYu PinYin Collection2!");
   }
}
