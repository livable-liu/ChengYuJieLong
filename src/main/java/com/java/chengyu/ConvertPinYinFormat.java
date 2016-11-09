package com.java.chengyu;

import java.io.IOException;
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

   private static PinYinParseResult parsePinYinCollectionFile(String path)
   {
      // generate pinyin dictionary
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      Parser parser = new PinYinParser();
      parser.setSource(new StringSource(content));
      parser.setSplitter("[a-z]+\\s+=\\s+");
      parser.parse();
      PinYinParseResult result = (PinYinParseResult) parser.getResult();
      return result;
   }

   private static String towardConverter(String path, PinYinParseResult result)
   {
      // e1 bao3 zhi1 gong1 -> ē bǎo zhī gōng
      String raw1 = FileUtils.readStringFromLocalFile(path, "UTF-8");
      StringBuffer sb = new StringBuffer();
      if (raw1 != null)
      {
         int start = 0;
         String splitter = "\\s+";
         int index = raw1.indexOf("\r\n", start);

         while (index > 0 && index + "\r\n".length() < raw1.length() - 1)
         {
            String tmp = raw1.substring(start, index);
            String[] array = tmp.split(splitter);
            sb.append(array[0]);// chengyu string
            sb.append("  ");
            for (int i = 1; i < array.length; i++)
            {
               String tone = array[i].replace("[^0-9]", "");
               String display = array[i].replace("[0-9\\s]", "");
               PinYin pinyin = result.getItem(display);
               sb.append(pinyin.getByIndex(Integer.valueOf(tone)));
               sb.append(" ");
            }
            sb.append("\r\n");
         }
      }
      return sb.toString();
   }

   private static String backwardConverter(String path, PinYinParseResult result)
   {
      // ē bǎo zhī gōng -> e1 bao3 zhi1 gong1
      String raw2 = FileUtils.readStringFromLocalFile(path, "UTF-8");
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
      // for test
      // Printer.printHashMap(map, false);

      StringBuffer sb = new StringBuffer();
      if (raw2 != null)
      {
         int start = 0;
         String splitter = "\\s+";
         int index = raw2.indexOf("\r\n", start);

         while (index > 0 && index + "\r\n".length() < raw2.length() - 1)
         {
            String tmp = raw2.substring(start, index);
            String[] array = tmp.split(splitter);
            if (array.length != 5)
            {
               start = index + "\r\n".length();
               index = raw2.indexOf("\r\n", start);
               continue;
            }
            sb.append(array[0]);// chengyu string
            sb.append("  ");
            for (int i = 1; i < array.length; i++)
            {
               String display = array[i];
               String form = map.get(display.trim());
               sb.append(" ");
               sb.append(form);
            }
            sb.append("\r\n");
            start = index + "\r\n".length();
            index = raw2.indexOf("\r\n", start);
         }
      }
      return sb.toString();
   }

   public static void main(String[] args)
   {
      System.out.println("Enter convert PinYin format!");
      FUNCTION.info("Enter convert!");
      PropertyConfigurator.configure("./src/main/resources/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/resources/log4j.properties", 60000L);
      PinYinParseResult result = parsePinYinCollectionFile("./src/main/resources/pinyin.txt");



      try
      {
         FileUtils.writeStringToLocalFile("./log/chengyu_tone.txt", backwardConverter("./src/main/resources/chengyu_utf8.txt", result),
               "UTF-8");
      }
      catch (IOException e)
      {
         // TODO implement catch IOException
         FUNCTION.error("Unexpected Exception", e);
         throw new UnsupportedOperationException("Unexpected Exception", e);

      }
      FUNCTION.info("Finish convert!");
      System.out.println("Leave convert PinYin format!");
   }
}
