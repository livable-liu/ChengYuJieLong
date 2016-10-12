package com.java.chengyu.shared.pronunciation;

import java.util.Collection;
import java.util.HashMap;

public class ChengYuDictionary implements Dictionary
{
   private HashMap<String, ChengYu> map;

   public ChengYuDictionary()
   {
      map = new HashMap<String, ChengYu>();
   }

   public ChengYu getChengYuFromDisplay(String display)
   {
      return map.get(display);
   }

   public void putChengYuUnderDisplay(String display, ChengYu chengYu)
   {
      map.put(display, chengYu);
   }

   public int size()
   {
      return map.size();
   }

   public Collection<ChengYu> values()
   {
      return map.values();
   }
}
