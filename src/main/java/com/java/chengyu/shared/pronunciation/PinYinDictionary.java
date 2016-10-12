package com.java.chengyu.shared.pronunciation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class PinYinDictionary implements Dictionary
{
   private HashMap<String, PinYin> map;

   public PinYinDictionary()
   {
      map = new HashMap<String, PinYin>();
   }

   public PinYin getPinYinFromDisplay(String display)
   {
      return map.get(display);
   }
   
   public PinYin getPinYinFromRawDisplay(String rawDisplay)
   {
      Collection<PinYin> en = map.values();
      
      Iterator<PinYin> it = en.iterator();
      while(it.hasNext())
      {
         PinYin pinyin = it.next();
         for (int i = 0; i <= 4; i ++)
         {
            if (pinyin.getByIndex(i).equalsIgnoreCase(rawDisplay))
            {
               return pinyin;
            }
         }
      }
      return null;
   }

   public void putPinYinUnderDisplay(String display, PinYin pinyin)
   {
      map.put(display, pinyin);
   }

   public int size()
   {
      return map.size();
   }

   public Collection<PinYin> values()
   {
      return map.values();
   }
}
