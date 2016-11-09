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

public class ChengYuJieLong
{
   
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");
   
   public static void main(String[] args)
   {
      System.out.println("Enter ChengYuJieLong!");
      FUNCTION.info("Enter parse ChengYu file!");
      PropertyConfigurator.configure("./src/main/resources/log4j.properties");
      PropertyConfigurator.configureAndWatch("./src/main/resources/log4j.properties", 60000L);
//      String path = "./chengyu.txt";
      String path = "./src/main/resources/chengyu_utf8.txt";
      String content = FileUtils.readStringFromLocalFile(path, "UTF-8");
      Parser parser = new ChengYuFileParser();
      parser.setSource(new StringSource(content));
      parser.setSplitter("\\s+");
      PinYinParseResult pinyinRes = ParsePinYinFile.parsePinYinFile("./src/main/resources/pinyin.txt");
      Dictionary pinYinDic = pinyinRes.getDictionary();
      parser.setDictionary(pinYinDic);
      parser.parse();
      ChengYuParseResult result = (ChengYuParseResult) parser.getResult();
      System.out.println(result.getSize());
      FUNCTION.info("Finish parse ChengYu file!");
      
      //Use floyd-warshall algorithm
      //fW with no tone
      FloydWarshall fW = new FloydWarshall();
      int M = result.getSize();// edge count
      int N = pinYinDic.size(); // node count
      
      Collection<PinYin> pinyins = pinyinRes.getAllItems();
      Map<Integer, PinYin> index2pinyin = new HashMap<Integer, PinYin>();
      Map<String, Integer> pinyin2Index = new HashMap<String, Integer>();
      
      Iterator<PinYin> pinyinIt = pinyins.iterator();
      int index = 1;
      while (pinyinIt.hasNext())
      {
         PinYin pinyin = pinyinIt.next();
         index2pinyin.put(index, pinyin);
         pinyin2Index.put(pinyin.getBase(), index);
         index ++;
      }
      
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
            fW.f[pinyin2Index.get(chengyu.getFirstPronunciation().getPinYin().getBase())][pinyin2Index.get(chengyu.getLastPronunciation().getPinYin().getBase())] = 1;
            fW.pMap[pinyin2Index.get(chengyu.getFirstPronunciation().getPinYin().getBase())][pinyin2Index.get(chengyu.getLastPronunciation().getPinYin().getBase())] = chengyu;
         }
      }
      
      fW.floydWarshall(N);
      
      //Query jielong
      System.out.println();
      //分-> 秒
      fW.printPath(pinyin2Index.get("fen"), pinyin2Index.get("miao"));
      System.out.println();
      //日 -> 月
      fW.printPath(pinyin2Index.get("ri"), pinyin2Index.get("yue"));
      System.out.println();
      //南 -> 北
      fW.printPath(pinyin2Index.get("nan"), pinyin2Index.get("bei"));
      System.out.println();
      //男 -> 女
      fW.printPath(pinyin2Index.get("nan"), pinyin2Index.get("nü"));
      System.out.println();
      //阿 -> 作
      fW.printPath(pinyin2Index.get("a"), pinyin2Index.get("zuo"));
      System.out.println();
      //哥 -> 弟
      fW.printPath(pinyin2Index.get("ge"), pinyin2Index.get("di"));
      System.out.println();
      //山 -> 水
      fW.printPath(pinyin2Index.get("shan"), pinyin2Index.get("shui"));
      System.out.println();
      //坐 -> 末
      fW.printPath(pinyin2Index.get("zuo"), pinyin2Index.get("mo"));
      System.out.println();
      
      
      for (int i = 1; i <= N; i ++)
      {
         for (int j = 1; j <= N; j ++)
         {
            if (fW.f[i][j] > 4 && fW.f[i][j] != fW.INF)
            {
               System.out.print("From " + index2pinyin.get(i).getBase() + " To " + index2pinyin.get(j).getBase());
               fW.printPath(i, j);
               System.out.println();
            }
         }
      }
      
      System.out.println("Leave ChengYuJieLong!");
   }

}
