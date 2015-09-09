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
 * Optimize pinyin from raw pin file!
 * The result has the following potential issues.
 * 1. should solve the incorrect '得' 先得我心 xian1 de0 wo3 xin1  -> xian1 de2 wo3 xin1
 * 2. should change the lv pronunciation 卸磨杀驴 xie4 mo2 sha1 lv2 -> xie4 mo2 sha1 lü2
 * 3. should solve the incorrect 一臧一否 yizangyifou -> yī zāng yī pǐ
 * 4. illegal pinyin 绅士风度 shen1 shi4 feng1 du4,shen1 shi4 pai4 tou2,shen1 shi4 qi4
 */
public class DownloadChengYuPinyinFromBaike
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   public static void main(String[] args)
   {
      System.out.println("Enter optimize ChengYu PinYin Collection!");
      FUNCTION.info("Enter optimize!!");
      PropertyConfigurator.configure("./src/main/java/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
      // download pinyin from internet
      String path = "./RawChengYuPinYin.txt";
      String content = FileUtils.readStringFromLocalFile(path, "GBK");
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
            // already converted
            if (array[1].replaceAll("[a-z]", "").replace(" ", "").length() != 0)
            {
               sb.append(tmp);
               sb.append("\r\n");
            }
            else
            {
               // get link from Baidu BaiKe
               try
               {
                  String temp = HTTPSimulator.getHTMLPageFromUrl("http://baike.baidu.com/search?word="
                        + URLEncoder.encode(array[0].trim(), "UTF-8") + "&pn=0&rn=0&enc=utf8",
                        "http://baike.baidu.com/", "UTF-8");
                  if (temp != null && !"".equals(temp))
                  {
                     TagNode node = HtmlCleanerUtil.parse(temp);
                     if (node != null)
                     {
                        Object[] objects = node.evaluateXPath("//a[@class='result-title']/@href");
                        // get content
                        if (objects != null && objects.length > 0)
                        {
                           String pinyin = HTTPSimulator.getHTMLPageFromUrl(objects[0].toString(),
                                 "http://baike.baidu.com/search?word=" + array[0].trim()
                                       + "&pn=0&rn=0&enc=utf8", "UTF-8");
                           TagNode n = HtmlCleanerUtil.parse(pinyin);
                           if (n != null)
                           {
                              Object[] o = n
                                    .evaluateXPath("//div[@class='para']/b/text()");
                              if (o == null || o.length == 0)
                              {
                                 o = n.evaluateXPath("//dt[contains(., '音')]/following::dd[1]/text()");
                              }
                              if (o != null && o.length > 0)
                              {
                                 sb.append(array[0]);
                                 sb.append(" ");
                                 if (o[0].toString().indexOf("(") >= 0)
                                 {
                                    sb.append(o[0].toString().substring(o[0].toString().indexOf("(") + 1,
                                          o[0].toString().indexOf(")")));
                                 }
                                 else
                                 {
                                    sb.append(o[0].toString());
                                 }
                                 sb.append("\r\n");
                              }
                           }
                        }
                     }
                  }
               }
               catch (Exception e)
               {
                  FUNCTION.error("IOException", e);
                  FUNCTION.error("pinyin = " + array[0].trim());
               }
            }
            start = index + "\r\n".length();
            index = content.indexOf("\r\n", start);

            System.out.println(tmp);
         }
      }

      try
      {
         FileUtils.writeStringToLocalFile("optimized1.log", sb.toString(), "UTF-8");
      }
      catch (IOException e)
      {
         FUNCTION.error("IOException", e);
      }
      FUNCTION.info("Finish optimize!!");
      System.out.println("Leave optimize ChengYu PinYin Collection!");
   }
}
