package com.java.chengyu.shared.pronunciation;

import java.util.List;

public class ChengYu
{
   String firstCharactor;
   String lastCharactor;
   PinYin firstPinYin;
   PinYin lastPinYin;
   
   List<String> chengyuList;
   List<PinYin> pinyinList;
   
   public ChengYu()
   {
      
   }
   
   public ChengYu(List<String> chengyuList, List<PinYin> pinyinList)
   {
      this.chengyuList = chengyuList;
      this.pinyinList = pinyinList;
      this.firstCharactor = chengyuList.get(0);
      this.lastCharactor = chengyuList.get(chengyuList.size() - 1);
      this.firstPinYin = pinyinList.get(0);
      this.lastPinYin = pinyinList.get(pinyinList.size() - 1);
   }
   

   public PinYin getFirstPinYin()
   {
      return this.firstPinYin;
   }
   
   public PinYin getLastPinYin()
   {
      return this.lastPinYin;
   }
   
   public String toString()
   {
      StringBuilder sb = new StringBuilder();
      for (int i = 0 ; i < chengyuList.size(); i ++)
      {
         sb.append(chengyuList.get(i));
      }
      return sb.toString();
   }
   
}
