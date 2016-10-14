package com.java.chengyu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.java.chengyu.shared.fileutils.parsers.ChengYuFileParser;
import com.java.chengyu.shared.fileutils.parsers.ChengYuParseResult;
import com.java.chengyu.shared.fileutils.parsers.PinYinParseResult;
import com.java.chengyu.shared.fileutils.parsers.StringSource;
import com.java.chengyu.shared.pronunciation.ChengYu;

public class FloydWarshall
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");
   
   int MAX = 4096;
   int INF = 65536;

   int f[][] = new int[MAX][MAX];
   ChengYu pMap[][] = new ChengYu[MAX][MAX];
   List< ? >[][] rMap = new List< ? >[MAX][MAX];

   private void mergePath(List<ChengYu> from, List<ChengYu> to)
   {
      for (int i = 0; i < to.size(); i++)
      {
         from.add(to.get(i));
      }
   }

   private void printPath(List< ? > path)
   {
      if (path == null)
      {
         System.out.print("No path between source and destiny!");
      }
      for (int i = 0; i < path.size(); i++)
      {
         System.out.print(path.get(i).toString());
         System.out.print("->");
      }
   }
   
   public void printPath(int from, int to)
   {
      printPath(rMap[from][to]);
   }
   
   // N is node count
   public void floydWarshall(int N)
   {
      int nLen = 65536;
      for (int k = 1; k <= N; k++)
      {
         for (int i = 1; i <= k; i++)
         {
            for (int j = 1; j <= k; j++)
            {
               nLen = Math.min(nLen, (pMap[i][j] == null ? 0 : 1) + f[i][k] + f[k][j]);
            }
         }

         for (int i = 1; i <= N; i++)
         {
            for (int j = 1; j <= N; j++)
            {
               if (f[i][k] + f[k][j] < f[i][j])
               {
                  f[i][j] = f[i][k] + f[k][j];
                  rMap[i][j] = new ArrayList<ChengYu>();
                  List<ChengYu> sPath = (List<ChengYu>) rMap[i][k];
                  List<ChengYu> dPath = (List<ChengYu>) rMap[k][j];

                  mergePath((List<ChengYu>) rMap[i][j], sPath);
//                  ((List<ChengYu>) rMap[i][j]).add(pMap[i][k]);
//                  ((List<ChengYu>) rMap[i][j]).add(pMap[k][j]);
                  mergePath((List<ChengYu>) rMap[i][j], dPath);
               }
               else
               {
                  if (f[i][j] == 1)
                  {
                     rMap[i][j] = new ArrayList<ChengYu>();
                     ((List<ChengYu>) rMap[i][j]).add(pMap[i][j]);
                  }
               }
            }
         }
      }

      System.out.println("Shortest Loop is " + nLen);

      for (int i = 1; i <= N; i++)
      {
         for (int j = 1; j <= N; j++)
         {
            if (f[i][j] == INF)
            {
               System.out.println("No path between i = " + i + " j = " + j);
            }
            else if (f[i][j] == 0)
            {
               System.out.println("Path between i = " + i + " j = " + j + " is 0");
            }
            else
            {
               System.out.println("Path between i = " + i + " j = " + j + " is " + f[i][j]);
               System.out.print(i + "->");
               printPath(rMap[i][j]);
               System.out.print(j);
               System.out.println();
            }
         }
      }
   }

   public static void main(String[] args)
      throws IOException
   {
      FloydWarshall fW = new FloydWarshall();
      int M = 8;// edge count
      int N = 6; // node count

      for (int i = 1; i <= N; i++)
      {
         for (int j = 1; j <= N; j++)
         {
            fW.pMap[i][j] = null;
            fW.f[i][j] = (i == j) ? 0 : fW.INF;
            fW.rMap[i][j] = new ArrayList<Integer>();
         }
      }

      {
         
         StringBuilder sb = new StringBuilder();
         sb.append("风起云涌 fēng qǐ yún yǒng\r\n");
         sb.append("封疆大吏 fēng jiāng dà lì\r\n");
         sb.append("用心竭力 yòng xīn jié lì\r\n");
         sb.append("礼崩乐坏 lǐ bēng yuè huài\r\n");
         sb.append("李代桃僵 lǐ dài táo jiāng\r\n");
         sb.append("江河日下 jiāng hé rì xià\r\n");
         sb.append("狭路相逢 xiá lù xiāng féng\r\n");
         sb.append("怀柔天下 huái róu tiān xià\r\n");
         
         ChengYuFileParser parser = new ChengYuFileParser();
         parser.setSource(new StringSource(sb.toString()));
         parser.setSplitter("\\s+");
         PinYinParseResult pinyinRes = ParsePinYinFile.parsePinYinFile("./pinyin.txt");
         parser.setDictionary(pinyinRes.getDictionary());
         parser.parse();
         
         ChengYuParseResult result = (ChengYuParseResult) parser.getResult();
         Collection<ChengYu> collects = result.getAllItems();
         Iterator<ChengYu> it = collects.iterator();
         
         Map<String, Integer> maps = new HashMap<String, Integer>();
         maps.put("feng", 1);
         maps.put("yong", 2);
         maps.put("li", 3);
         maps.put("jiang", 4);
         maps.put("xia", 5);
         maps.put("huai", 6);
         
         
         while (it.hasNext())
         {
            ChengYu chengyu = it.next();
            fW.f[maps.get(chengyu.getFirstPronunciation().getPinYin().getBase())][maps.get(chengyu.getLastPronunciation().getPinYin().getBase())] = 1;
            fW.pMap[maps.get(chengyu.getFirstPronunciation().getPinYin().getBase())][maps.get(chengyu.getLastPronunciation().getPinYin().getBase())] = chengyu;
         }

      }

      fW.floydWarshall(N);
   }

}
