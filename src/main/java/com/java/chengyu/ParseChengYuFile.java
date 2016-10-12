package com.java.chengyu;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.java.chengyu.shared.fileutils.FileUtils;
import com.java.chengyu.shared.fileutils.parsers.ChengYuFileParser;
import com.java.chengyu.shared.fileutils.parsers.ChengYuParseResult;
import com.java.chengyu.shared.fileutils.parsers.Parser;
import com.java.chengyu.shared.fileutils.parsers.StringSource;

public class ParseChengYuFile
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   public static void main(String[] args)
   {
      System.out.println("Enter parse ChengYu file!");
      FUNCTION.info("Enter parse!");
      PropertyConfigurator.configure("./src/main/java/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
      String path = "./chengyu.txt";
      String content = FileUtils.readStringFromLocalFile(path, "GB2312");
      Parser parser = new ChengYuFileParser();
      parser.setSource(new StringSource(content));
      parser.setSplitter("\\s+");
      parser.setDictionary(ParsePinYinFile.parsePinYinFile("./pinyin.txt").getDictionary());
      parser.parse();
      ChengYuParseResult result = (ChengYuParseResult) parser.getResult();
      System.out.println(result.getSize());

      FUNCTION.info("Finish parse!");
      System.out.println("Leave parse PinYin Collection file!");
   }
}
