package com.java.chengyu;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.java.chengyu.shared.fileutils.FileUtils;
import com.java.chengyu.shared.fileutils.parsers.Parser;
import com.java.chengyu.shared.fileutils.parsers.PinYinParseResult;
import com.java.chengyu.shared.fileutils.parsers.PinYinParser;
import com.java.chengyu.shared.fileutils.parsers.StringSource;
import com.java.chengyu.shared.pronunciation.PinYin;

/**
 * Convert PinYin format! e1 bao3 zhi1 gong1 <-> ē bǎo zhī gōng
 */
public class ConvertPinYinFormat
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   public static void main(String[] args)
   {
      System.out.println("Enter convert PinYin format!");
      FUNCTION.info("Enter convert!");
      PropertyConfigurator.configure("./src/main/java/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
      // generate pinyin dictionary
      String path = "./pinyin.txt";
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      Parser parser = new PinYinParser();
      parser.setSource(new StringSource(content));
      parser.setSplitter("[a-z]+\\s+=\\s+");
      parser.parse();
      PinYinParseResult result = (PinYinParseResult) parser.getResult();

      // e1 bao3 zhi1 gong1 -> ē bǎo zhī gōng
      // String sourcePath = "./FormatWithNo.txt";
      // String raw1 = FileUtils.readStringFromLocalFile(sourcePath, "GBK");
      // StringBuffer sb = new StringBuffer();
      // if (raw1 != null)
      // {
      // int start = 0;
      // String splitter = "\\s+";
      // int index = raw1.indexOf("\r\n", start);
      //
      // while (index > 0 && index + "\r\n".length() < raw1.length() - 1)
      // {
      // String tmp = raw1.substring(start, index);
      // String[] array = tmp.split(splitter);
      // sb.append(array[0]);// chengyu string
      // sb.append("  ");
      // for (int i = 1; i < array.length; i ++)
      // {
      // String tone = array[i].replace("[^0-9]", "");
      // String display = array[i].replace("[0-9\\s]", "");
      // PinYin pinyin = result.getItem(display);
      // sb.append(pinyin.getByIndex(Integer.valueOf(tone)));
      // sb.append(" ");
      // }
      // sb.append("\r\n");
      // }
      // }
      // ē bǎo zhī gōng -> e1 bao3 zhi1 gong1
      String sourcePath2 = "./RawPinYinWithTone.txt";
      String raw2 = FileUtils.readStringFromLocalFile(sourcePath2, "GBK");
      StringBuffer sb2 = new StringBuffer();
      Collection<PinYin> en = result.getAllItems();
      HashMap<String, String> map = new HashMap<String, String>();
      Iterator<PinYin> it = en.iterator();
      while (it.hasNext())
      {
         PinYin p = it.next();
         for (int i = 0; i <= 4; i++)
         {
            if (!StringUtils.isBlank(p.getByIndex(i)))
            {
               map.put(p.getByIndex(i).trim(), p.getBase().trim() + i);
            }
         }
      }
      if (raw2 != null)
      {
         int start = 0;
         String splitter = "\\s+";
         int index = raw2.indexOf("\r\n", start);

         while (index > 0 && index + "\r\n".length() < raw2.length() - 1)
         {
            String tmp = raw2.substring(start, index);
            String[] array = tmp.split(splitter);
            sb2.append(array[0]);// chengyu string
            sb2.append("  ");
            if (array.length != 5)
            {
               continue;
            }
            for (int i = 1; i < array.length; i ++)
            {
               String display = array[i];
               String form = map.get(display.trim());
               sb2.append(" ");
               sb2.append(form);
            }
            sb2.append("\r\n");
            start = index + "\r\n".length();
            index = raw2.indexOf("\r\n", start);
         }
      }
      System.out.println(sb2.toString());
      FUNCTION.info("Finish convert!");
      System.out.println("Leave convert PinYin format!");
   }
}
