package com.java.chengyu;

import org.htmlcleaner.TagNode;

import com.java.chengyu.shared.domutils.HtmlCleanerUtil;
import com.java.chengyu.shared.httputils.HTTPSimulator;
import com.java.chengyu.shared.pronunciation.PinYin;

/**
 * Download all PinYin collection from the Internet!
 *
 */
public class DownloadPinyinCollection 
{
    public static void main( String[] args )
    {
      System.out.println("Enter download PinYin Collection!");
      // download A cluster
      try
      {
         for (int i = 101; i < 106; i++)
         {
            String temp = HTTPSimulator.sendRawSocketGet("xh.5156edu.com", 80,
                  "/html2/p" + String.valueOf(i), "UTF-8");
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
               PinYin pinyin = new PinYin(objects[0].toString(), objects1[0].toString(),
                     objects2[0].toString(),
                     objects3[0].toString(), objects4[0].toString(), 'a');
            }
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      // download b - z cluster
      try
      {
         // i u v are kicked out
         for (int i = 97; i <= 122 && i != 105 && i != 117 && i != 118; i++)
         {
            for (int j = 1; j <= 37; j++)
            {
               String temp = HTTPSimulator.sendRawSocketGet("xh.5156edu.com", 80, "/html2/"
                     + (char) i + (j < 10 ? "0" : "") 
                     + String.valueOf(j), "UTF-8");
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
                  PinYin pinyin = new PinYin(objects[0].toString(), objects1[0].toString(),
                        objects2[0].toString(), objects3[0].toString(), objects4[0].toString(), (char) i);
               }
            }
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      System.out.println("Leave download PinYin Collection!");
    }
}
