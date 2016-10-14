package com.java.chengyu;

import java.util.ArrayList;
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
import com.java.chengyu.shared.fileutils.parsers.StringSource;
import com.java.chengyu.shared.pronunciation.ChengYu;
import com.java.chengyu.shared.pronunciation.Dictionary;
import com.java.chengyu.shared.pronunciation.PinYin;

public class ChengYuJieLongWithTone
{
   
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");
   
   public static void main(String[] args)
   {
      System.out.println("Enter ChengYuJieLongWithTone!");
      FUNCTION.info("Enter parse ChengYu file!");
      PropertyConfigurator.configure("./src/main/java/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/java/log4j.properties", 60000L);
//      String path = "./chengyu.txt";
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
      System.out.println(result.getSize());
      FUNCTION.info("Finish parse ChengYu file!");
      
      //Use floyd-warshall algorithm
      //fW with tone
      FloydWarshall fW = new FloydWarshall();
      
      Collection<PinYin> pinyins = pinyinRes.getAllItems();
      Map<Integer, String> index2pinyin = new HashMap<Integer, String>();
      Map<String, Integer> pinyin2Index = new HashMap<String, Integer>();
      
      Iterator<PinYin> pinyinIt = pinyins.iterator();
      int index = 1;
      while (pinyinIt.hasNext())
      {
         PinYin pinyin = pinyinIt.next();
         for (int i = 0; i <= 4; i ++)
         {
            if (pinyin.getByIndex(i) != null)
            {
               index2pinyin.put(index, pinyin.getByIndex(i));
               pinyin2Index.put(pinyin.getByIndex(i), index);
               index ++;
            }
         }
      }
      
      int M = result.getSize();// edge count
      int N = index; // node count
      
      for (int i = 1; i <= N; i ++)
      {
         for (int j = 1; j <= N; j ++)
         {
            fW.f[i][j] = (i == j) ? 0 : fW.INF;
            fW.rMap[i][j] = new ArrayList<ChengYu>();
            fW.pMap[i][j] = null;
         }
      }
      
      //initialize the map using chengyu
      {
         Collection<ChengYu> collects = result.getAllItems();
         Iterator<ChengYu> its = collects.iterator();
         while (its.hasNext())
         {
            ChengYu chengyu = its.next();
            try
            {
               fW.f[pinyin2Index.get(chengyu.getFirstPronunciation().getDisplay())][pinyin2Index.get(chengyu.getLastPronunciation().getDisplay())] = 1;
               fW.pMap[pinyin2Index.get(chengyu.getFirstPronunciation().getDisplay())][pinyin2Index.get(chengyu.getLastPronunciation().getDisplay())] = chengyu;
            }
            catch (Exception e)
            {
               System.out.println(chengyu);
               e.printStackTrace();
            }
         }
      }
      
      fW.floydWarshall(N);
      
      //Query jielong
      System.out.println();
      //分-> 秒
      fW.printPath(pinyin2Index.get("fēn"), pinyin2Index.get("miǎo"));
      System.out.println();
      //日 -> 月
      fW.printPath(pinyin2Index.get("rì"), pinyin2Index.get("yuè"));
      System.out.println();
      //南 -> 北
      fW.printPath(pinyin2Index.get("nán"), pinyin2Index.get("běi"));
      System.out.println();
      //男 -> 女
      fW.printPath(pinyin2Index.get("nán"), pinyin2Index.get("nǚ"));
      System.out.println();
      //阿 -> 作
      fW.printPath(pinyin2Index.get("ā"), pinyin2Index.get("zuò"));
      System.out.println();
      //哥 -> 弟
      fW.printPath(pinyin2Index.get("gē"), pinyin2Index.get("dì"));
      System.out.println();
      //山 -> 水
      fW.printPath(pinyin2Index.get("shān"), pinyin2Index.get("shuǐ"));
      System.out.println();
      //坐 -> 末
      fW.printPath(pinyin2Index.get("zuò"), pinyin2Index.get("mò"));
      System.out.println();
      
      
      for (int i = 1; i <= N; i ++)
      {
         for (int j = 1; j <= N; j ++)
         {
            if (fW.f[i][j] > 4 && fW.f[i][j] != fW.INF)
            {
               System.out.print("From " + index2pinyin.get(i) + " To " + index2pinyin.get(j));
               fW.printPath(i, j);
               System.out.println();
            }
         }
      }
      
      System.out.println("Leave ChengYuJieLongWithTone!");
   }

}
