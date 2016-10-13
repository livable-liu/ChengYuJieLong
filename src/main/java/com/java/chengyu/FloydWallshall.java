package com.java.chengyu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.java.chengyu.shared.pronunciation.ChengYu;
import com.java.chengyu.shared.pronunciation.PinYin;

public class FloydWallshall
{

   int MAX = 1024;
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
   public void floydWallshall(int N)
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
      FloydWallshall fW = new FloydWallshall();
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
         
         fW.f[1][2] = 1; //风起云涌
         fW.pMap[1][2] = new ChengYu(Arrays.asList("风","起","云","涌"), Arrays.asList(new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f')));
         fW.f[1][3] = 1; //封疆大吏
         fW.pMap[1][3] = new ChengYu(Arrays.asList("封","疆","大","吏"), Arrays.asList(new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f')));
         fW.f[2][3] = 1; //用心竭力
         fW.pMap[2][3] = new ChengYu(Arrays.asList("用","心","竭","力"), Arrays.asList(new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f')));
         fW.f[3][6] = 1; //礼崩乐坏
         fW.pMap[3][6] = new ChengYu(Arrays.asList("礼","崩","乐","坏"), Arrays.asList(new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'))); 
         fW.f[3][4] = 1; //李代桃僵
         fW.pMap[3][4] = new ChengYu(Arrays.asList("李","代","桃","僵"), Arrays.asList(new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f')));
         fW.f[4][5] = 1; //江河日下
         fW.pMap[4][5] = new ChengYu(Arrays.asList("江","河","日","下"), Arrays.asList(new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f')));
         fW.f[5][1] = 1; //狭路相逢
         fW.pMap[5][1] = new ChengYu(Arrays.asList("狭","路","相","逢"), Arrays.asList(new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f')));
         fW.f[6][5] = 1; //怀柔天下
         fW.pMap[6][5] = new ChengYu(Arrays.asList("怀","柔","天","下"), Arrays.asList(new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f'), new PinYin("fēng", "fēng", "fēng", "fēng", "fēng", 'f')));

      }

      fW.floydWallshall(N);
   }

}
