package com.java.chengyu;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.htmlcleaner.TagNode;

import com.java.chengyu.shared.domutils.HtmlCleanerUtil;
import com.java.chengyu.shared.fileutils.FileUtils;
import com.java.chengyu.shared.httputils.HTTPSimulator;
import com.java.chengyu.shared.pronunciation.PinYin;

/**
 * Download all PinYin collection from the Internet!
 *
 */
public class DownloadPinyinCollectionFrom5156edu
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   public static void main(String[] args)
   {
      System.out.println("Enter download PinYin Collection!");
      FUNCTION.info("Enter download!!");
      PropertyConfigurator.configure("./src/main/java/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
      StringBuffer s = new StringBuffer();
      // download A cluster
      for (int i = 101; i < 106; i++)
      {
         try
         {
            // String temp = HTTPSimulator.sendRawSocketGet("xh.5156edu.com", 80,
            // "/html2/p" + String.valueOf(i) + ".html", "http://xh.5156edu.com/pinyi.html", "GB2312");
            String temp = HTTPSimulator.getHTMLPageFromUrl(
                  "http://xh.5156edu.com/html2/p" + String.valueOf(i) + ".html",
                  "http://xh.5156edu.com/pinyi.html", "GBK");
            if (temp != null && !"".equals(temp))
            {
               TagNode node = HtmlCleanerUtil.parse(temp);
               if (node != null)
               {
                  Object[] objects = node.evaluateXPath("//*[@id='table1']/tbody/tr/td/font[1]/text()");
                  Object[] objects1 = node
                        .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr[2]/td[1]/p[1]/text()");
                  Object[] objects2 = node
                        .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr[3]/td[1]/p[1]/text()");
                  Object[] objects3 = node
                        .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr[4]/td[1]/p[1]/text()");
                  Object[] objects4 = node
                        .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr[5]/td[1]/p[1]/text()");
                  if (objects1.length == 0)
                  {
                     continue;
                  }
                  if (objects4.length == 0 || "未分类".equals(objects4[0].toString()))
                  {
                     String first = "";
                     String second = "";
                     String third = "";
                     String last = "";
                     String[] stringArray = new String[3];
                     stringArray[0] = objects1[0].toString();
                     if (objects2.length != 0 && !"未分类".equals(objects2[0].toString()))
                     {
                        stringArray[1] = objects2[0].toString();
                     }
                     else
                     {
                        stringArray[1] = null;
                     }
                     if (objects3.length != 0 && !"未分类".equals(objects3[0].toString()))
                     {
                        stringArray[2] = objects3[0].toString();
                     }
                     else
                     {
                        stringArray[2] = null;
                     }
                     for (int k = 0; k < stringArray.length; k++)
                     {
                        String tmp = stringArray[k];
                        if (tmp != null && !"".equals(tmp))
                        {
                           Object[] obj = node
                                 .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr["
                                       + (2 + k) + "]/td[2]/a[1]/@href");
                           String content = HTTPSimulator.getHTMLPageFromUrl("http://xh.5156edu.com/"
                                 + obj[0].toString(), "http://xh.5156edu.com/html2/p" + String.valueOf(i)
                                 + ".html", "GBK");
                           // System.out.println(content);
                           TagNode n = HtmlCleanerUtil.parse(content);
                           if (n != null)
                           {
                              Object[] o = n.evaluateXPath("//td[@width='70%']//script/text()");
                              if (o.length == 0)
                              {
                                 FUNCTION.error("===========================" + objects[0].toString());
                              }
                              for (int m = 0; m < o.length; m++)
                              {
                                 String pronunciation = o[0].toString();
                                 pronunciation = pronunciation.substring(pronunciation.indexOf('"') + 1,
                                       pronunciation.lastIndexOf('"'));
                                 if (!pronunciation.startsWith(objects[0].toString()))
                                 {
                                    continue;
                                 }
                                 pronunciation = pronunciation.replaceAll("[^0-9]", "");
                                 if (pronunciation.equals("1"))
                                 {
                                    first = tmp;
                                    break;
                                 }
                                 else if (pronunciation.equals("2") && !tmp.equals(first))
                                 {
                                    second = tmp;
                                    break;
                                 }
                                 else if (pronunciation.equals("3") && !tmp.equals(first)
                                       && !tmp.equals(second))
                                 {
                                    third = tmp;
                                    break;
                                 }
                                 else if (pronunciation.equals("4") && !tmp.equals(first)
                                       && !tmp.equals(second) && !tmp.equals(third))
                                 {
                                    last = tmp;
                                    break;
                                 }
                              }
                           }
                        }
                        else
                        {
                           break;
                        }
                     }
                     PinYin pinyin = new PinYin(objects[0].toString(), first, second, third, last, 'a');
                     // System.out.println(pinyin.toString());
                     s.append(pinyin.toString());
                     s.append("\r\n");
                     continue;
                  }
                  PinYin pinyin = new PinYin(objects[0].toString(), objects1[0].toString(),
                        objects2[0].toString(), objects3[0].toString(), objects4[0].toString(), 'a');
                  // System.out.println(pinyin.toString());
                  s.append(pinyin.toString());
                  s.append("\r\n");
               }
            }
            else
            {
               FUNCTION.error("http://xh.5156edu.com/html2/p" + String.valueOf(i) + ".html\r\n");
               // System.out.println("http://xh.5156edu.com/html2/p" + String.valueOf(i) + ".html");
            }
         }
         catch (Exception e)
         {
            // e.printStackTrace();
         }
      }
      // download b - z cluster
      // i u v are kicked out
      for (int i = 98; i <= 122; i++)
      {
         try
         {
            if (i == 105 || i == 117 || i == 118)
               continue;
            for (int j = 1; j <= 37; j++)
            {
               // String temp = HTTPSimulator.sendRawSocketGet("xh.5156edu.com", 80, "/html2/"
               // + (char) i + (j < 10 ? "0" : "")
               // + String.valueOf(j) + ".html", "http://xh.5156edu.com/pinyi.html", "GB2312");
               String temp = HTTPSimulator.getHTMLPageFromUrl("http://xh.5156edu.com/html2/" + (char) i
                     + (j < 10 ? "0" : "") + String.valueOf(j) + ".html", "http://xh.5156edu.com/pinyi.html",
                     "GBK");
               if (temp != null && !"".equals(temp))
               {
                  TagNode node = HtmlCleanerUtil.parse(temp);
                  if (node != null)
                  {
                     Object[] objects = node.evaluateXPath("//*[@id='table1']/tbody/tr/td/font[1]/text()");
                     Object[] objects1 = node
                           .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr[2]/td[1]/p[1]/text()");
                     Object[] objects2 = node
                           .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr[3]/td[1]/p[1]/text()");
                     Object[] objects3 = node
                           .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr[4]/td[1]/p[1]/text()");
                     Object[] objects4 = node
                           .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr[5]/td[1]/p[1]/text()");
                     if (objects1.length == 0)
                     {
                        continue;
                     }
                     if (objects4.length == 0 || "未分类".equals(objects4[0].toString()))
                     {
                        String first = "";
                        String second = "";
                        String third = "";
                        String last = "";
                        String[] stringArray = new String[3];
                        stringArray[0] = objects1[0].toString();
                        if (objects2.length != 0 && !"未分类".equals(objects2[0].toString()))
                        {
                           stringArray[1] = objects2[0].toString();
                        }
                        else
                        {
                           stringArray[1] = null;
                        }
                        if (objects3.length != 0 && !"未分类".equals(objects3[0].toString()))
                        {
                           stringArray[2] = objects3[0].toString();
                        }
                        else
                        {
                           stringArray[2] = null;
                        }
                        for (int k = 0; k < stringArray.length; k++)
                        {
                           String tmp = stringArray[k];
                           if (tmp != null && !"".equals(tmp))
                           {
                              Object[] obj = node
                                    .evaluateXPath("//*[@id='table1']/tbody/tr/td/table[2]/tbody/tr["
                                          + (2 + k) + "]/td[2]/a[1]/@href");
                              String content = HTTPSimulator.getHTMLPageFromUrl("http://xh.5156edu.com/"
                                    + obj[0].toString(), "http://xh.5156edu.com/html2/" + (char) i
                                    + (j < 10 ? "0" : "") + String.valueOf(j) + ".html", "GBK");
                              // System.out.println(content);
                              TagNode n = HtmlCleanerUtil.parse(content);
                              if (n != null)
                              {
                                 Object[] o = n.evaluateXPath("//td[@width='70%']//script/text()");
                                 if (o.length == 0)
                                 {
//                                    System.out.println("===========================" + objects[0].toString());
                                    FUNCTION.error("===========================" + objects[0].toString());
                                 }
                                 for (int m = 0; m < o.length; m++)
                                 {
                                    String pronunciation = o[m].toString();
                                    pronunciation = pronunciation.substring(pronunciation.indexOf('"') + 1,
                                          pronunciation.lastIndexOf('"'));
                                    if (!pronunciation.startsWith(objects[0].toString()))
                                    {
                                       continue;
                                    }
                                    pronunciation = pronunciation.replaceAll("[^0-9]", "");
                                    if (pronunciation.equals("1"))
                                    {
                                       first = tmp;
                                       break;
                                    }
                                    else if (pronunciation.equals("2") && !tmp.equals(first))
                                    {
                                       second = tmp;
                                       break;
                                    }
                                    else if (pronunciation.equals("3") && !tmp.equals(first)
                                          && !tmp.equals(second))
                                    {
                                       third = tmp;
                                       break;
                                    }
                                    else if (pronunciation.equals("4") && !tmp.equals(first)
                                          && !tmp.equals(second) && !tmp.equals(third))
                                    {
                                       last = tmp;
                                       break;
                                    }
                                 }
                              }
                           }
                           else
                           {
                              break;
                           }
                        }
                        PinYin pinyin = new PinYin(objects[0].toString(), first, second, third, last,
                              (char) i);
                        // System.out.println(pinyin.toString());
                        s.append(pinyin.toString());
                        s.append("\r\n");
                        continue;
                     }
                     // avoid duplicate pinyin
                     // http://xh.5156edu.com/html2/h09.html
                     String one = objects1[0].toString();
                     String two = objects2[0].toString();
                     String three = objects3[0].toString();
                     String four = objects4[0].toString();
                     if (one.equals(two))
                     {
                        two = "";
                     }
                     if (two.equals(three))
                     {
                        three = "";
                     }
                     if (three.equals(four))
                     {
                        four = "";
                     }
                     PinYin pinyin = new PinYin(objects[0].toString(), one, two, three, four, (char) i);
                     // System.out.println(pinyin.toString());
                     s.append(pinyin.toString());
                     s.append("\r\n");
                  }
               }
               else
               {
                  FUNCTION.error("http://xh.5156edu.com/html2/" + (char) i + (j < 10 ? "0" : "")
                        + String.valueOf(j) + ".html");
                  // System.out.println("http://xh.5156edu.com/html2/" + (char) i + (j < 10 ? "0" : "")
                  // + String.valueOf(j) + ".html");
               }
            }
         }
         catch (Exception e)
         {
            // e.printStackTrace();
         }
      }

      try
      {
         FileUtils.writeStringToLocalFile("result.log", s.toString(), "UTF-8");
      }
      catch (IOException e)
      {
         // TODO implement catch IOException
         FUNCTION.error("Unexpected Exception", e);
         throw new UnsupportedOperationException("Unexpected Exception", e);

      }
      FUNCTION.info("Finish download!!");
      System.out.println("Leave download PinYin Collection!");
   }
}
