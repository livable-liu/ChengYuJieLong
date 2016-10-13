package com.java.chengyu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FloydWallshallRaw
{

   int MAX = 1024;
   int INF = 65536;
   
   int f[][] = new int[MAX][MAX];
   int pMap[][] = new int[MAX][MAX];
   List< ? >[][] rMap = new List< ? >[MAX][MAX];
   
   private void mergePath(List<Integer> from, List<Integer> to)
   {
      for (int i = 0; i < to.size(); i ++)
      {
         from.add(to.get(i));
      }
   }
   
   private void printPath(List<?> path)
   {
      for (int i = 0; i < path.size(); i ++)
      {
         System.out.print(path.get(i));
         System.out.print("->");
      }
   }
   
   //N is node count
   private void floydWallshall(int N)
   {
      int nLen = 65536;
      for (int k = 1; k <= N; k ++)
      {
         for (int i = 1; i <= k; i ++)
         {
            for (int j = 1; j <= k; j ++)
            {
               nLen = Math.min(nLen, pMap[i][j] + f[i][k] + f[k][j]);
            }
         }
         
         for (int i = 1; i <= N; i ++)
         {
            for (int j = 1; j <= N; j ++)
            {
               if (f[i][k] + f[k][j] < f[i][j])
               {
                  f[i][j] = f[i][k] + f[k][j];
                  rMap[i][j] = new ArrayList<Integer>();
                  List<Integer> sPath = (List<Integer>) rMap[i][k];
                  List<Integer> dPath = (List<Integer>) rMap[k][j];
                  
                  mergePath((List<Integer>)rMap[i][j], sPath);
                  ((List<Integer>)rMap[i][j]).add(k);
                  mergePath((List<Integer>)rMap[i][j], dPath);
               }
            }
         }
      }
      
      System.out.println("Shortest Loop is " + nLen);
      
      for (int i = 1; i <= N; i ++)
      {
         for (int j = 1; j <= N; j ++)
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
   
   public static void main(String[] args) throws IOException
   {
      FloydWallshallRaw fW = new FloydWallshallRaw();
      int M = 9;// edge count
      int N = 5; // node count
      
      for (int i = 1; i <= N; i ++)
      {
         for (int j = 1; j <= N; j ++)
         {
            fW.pMap[i][j] = fW.f[i][j] = (i == j) ? 0 : fW.INF;
            fW.rMap[i][j] = new ArrayList<Integer>();
         }
      }
      
      
      {
         fW.f[1][2] = fW.pMap[1][2] = 1;
         fW.f[1][3] =fW.pMap[1][3] = 1;
         fW.f[1][5] =fW.pMap[1][5] = 1;
         fW.f[2][5] =fW.pMap[2][5] = 1;
         fW.f[2][4] =fW.pMap[2][4] = 1;
         fW.f[3][2] =fW.pMap[3][2] = 1;
         fW.f[4][1] =fW.pMap[4][1] = 1;
         fW.f[4][3] =fW.pMap[4][3] = 1;
         fW.f[5][4] =fW.pMap[5][4] = 1;
         
      }
      
      fW.floydWallshall(N);
   }

}
