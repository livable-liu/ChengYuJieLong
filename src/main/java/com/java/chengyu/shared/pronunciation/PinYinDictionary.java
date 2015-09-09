package com.java.chengyu.shared.pronunciation;

import java.util.Collection;
import java.util.HashMap;

public class PinYinDictionary
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
