package com.java.chengyu;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.java.chengyu.shared.fileutils.FileUtils;
import com.java.chengyu.shared.fileutils.parsers.ChengYuFileParser;
import com.java.chengyu.shared.fileutils.parsers.ChengYuParseResult;
import com.java.chengyu.shared.fileutils.parsers.Parser;
import com.java.chengyu.shared.fileutils.parsers.PinYinParseResult;
import com.java.chengyu.shared.fileutils.parsers.PinYinParser;
import com.java.chengyu.shared.fileutils.parsers.StringSource;
import com.java.chengyu.shared.pronunciation.ChengYu;
import com.java.chengyu.shared.pronunciation.Dictionary;
import com.java.chengyu.shared.pronunciation.PinYin;

/**
 * Find the non-toned chengyu
 */
public class ChengYuDataWash
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

   

   public static void main(String[] args)
   {
      System.out.println("Enter ChengYuDataWash!");
      FUNCTION.info("Enter wash!");
      PropertyConfigurator.configure("./src/main/java/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
      
      String path = "./chengyu_utf8.txt";
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      Parser parser = new ChengYuFileParser();
      parser.setSource(new StringSource(content));
      parser.setSplitter("\\s+");
      PinYinParseResult pinyinRes = ParsePinYinFile.parsePinYinFile("./pinyin.txt");
      Dictionary pinYinDic = pinyinRes.getDictionary();
      parser.setDictionary(pinYinDic);
      parser.parse();
      ChengYuParseResult result = (ChengYuParseResult) parser.getResult();
      
      Collection<PinYin> pinyins = pinyinRes.getAllItems();
      Map<Integer, String> index2pinyin = new HashMap<Integer, String>();
      Map<String, Integer> pinyin2Index = new HashMap<String, Integer>();
      
      Iterator<PinYin> pinyinIt = pinyins.iterator();
      int index = 1;
      while (pinyinIt.hasNext())
      {
         PinYin pinyin = pinyinIt.next();
         for (int i = 1; i <= 4; i ++)
         {
            if (pinyin.getByIndex(i) != null)
            {
               index2pinyin.put(index, pinyin.getByIndex(i));
               pinyin2Index.put(pinyin.getByIndex(i), index);
               index ++;
            }
         }
      }
      
      Collection<ChengYu> collects = result.getAllItems();
      Iterator<ChengYu> its = collects.iterator();
      while (its.hasNext())
      {
         ChengYu chengyu = its.next();
         try
         {
            if (pinyin2Index.get(chengyu.getFirstPronunciation().getDisplay()) == null)
            {
               String tmp = "";
               for (int i = 0; i < chengyu.getCharactors().size(); i ++)
               {
                  tmp += chengyu.getCharactors().get(i).getCharactorString();
               }
               FUNCTION.info(tmp);
            }
         }
         catch (Exception e)
         {
            System.out.println(chengyu);
            e.printStackTrace();
         }
      }
      
      FUNCTION.info("Finish wash!");
      System.out.println("Leave ChengYuDataWash!");
   }
}
