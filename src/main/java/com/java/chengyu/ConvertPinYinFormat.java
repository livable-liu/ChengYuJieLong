package com.java.chengyu;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.java.chengyu.shared.fileutils.FileUtils;
import com.java.chengyu.shared.fileutils.parsers.Parser;
import com.java.chengyu.shared.fileutils.parsers.PinYinParseResult;
import com.java.chengyu.shared.fileutils.parsers.PinYinParser;
import com.java.chengyu.shared.fileutils.parsers.StringSource;

/**
 * Convert PinYin format!
 * 1. should solve the incorrect '得' 先得我心 xian1 de0 wo3 xin1  -> xian1 de2 wo3 xin1
 * 2. should change the lv pronunciation 卸磨杀驴 xie4 mo2 sha1 lv2 -> xie4 mo2 sha1 lü2
 * 3. should solve the incorrect 一臧一否 yizangyifou -> yī zāng yī pǐ
 * 4. illegal pinyin 绅士风度 shen1 shi4 feng1 du4,shen1 shi4 pai4 tou2,shen1 shi4 qi4
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
      String path = "./pinyin.txt";
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      Parser parser = new PinYinParser();
      parser.setSource(new StringSource(content));
      parser.setSplitter("[a-z]+\\s+=\\s+");
      parser.parse();
      PinYinParseResult result = (PinYinParseResult) parser.getResult();
      System.out.println(result.getSize());

      FUNCTION.info("Finish convert!");
      System.out.println("Leave convert PinYin format!");
   }
}
