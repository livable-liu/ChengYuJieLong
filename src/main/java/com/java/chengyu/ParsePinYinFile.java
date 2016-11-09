package com.java.chengyu;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.java.chengyu.shared.fileutils.FileUtils;
import com.java.chengyu.shared.fileutils.parsers.Parser;
import com.java.chengyu.shared.fileutils.parsers.PinYinParseResult;
import com.java.chengyu.shared.fileutils.parsers.PinYinParser;
import com.java.chengyu.shared.fileutils.parsers.StringSource;

/**
 * Parse the download PinYin collection file!
 *
 */
public class ParsePinYinFile
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   public static PinYinParseResult parsePinYinFile(String path)
   {
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      Parser parser = new PinYinParser();
      parser.setSource(new StringSource(content));
      parser.setSplitter("[a-z]+\\s+=\\s+");
      parser.parse();
      PinYinParseResult result = (PinYinParseResult) parser.getResult();
      return result;
   }
   
   public static void main(String[] args)
   {
      System.out.println("Enter parse PinYin Collection file!");
      FUNCTION.info("Enter parse!");
      PropertyConfigurator.configure("./src/main/resources/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/resources/log4j.properties", 60000L);
      String path = "./src/main/resources/pinyin.txt";
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      Parser parser = new PinYinParser();
      parser.setSource(new StringSource(content));
      parser.setSplitter("[a-z]+\\s+=\\s+");
      parser.parse();
      PinYinParseResult result = (PinYinParseResult) parser.getResult();
      System.out.println(result.getSize());

      FUNCTION.info("Finish parse!");
      System.out.println("Leave parse PinYin Collection file!");
   }
}
