package com.java.chengyu.shared.fileutils.parsers;

import java.util.Collection;

import com.java.chengyu.shared.pronunciation.ChengYu;
import com.java.chengyu.shared.pronunciation.ChengYuDictionary;
import com.java.chengyu.shared.pronunciation.Dictionary;

public class ChengYuParseResult extends ParseResult
{
   ChengYuDictionary dict;

   ChengYuParseResult()
   {
      dict = new ChengYuDictionary();
   }

   public void addItem(ChengYu chengYu)
   {
      dict.putChengYuUnderDisplay(chengYu.toString(), chengYu);
   }

   public ChengYu getItem(String base)
   {
      return dict.getChengYuFromDisplay(base);
   }

   @Override
   public Collection<ChengYu> getAllItems()
   {
      return dict.values();
   }

   @Override
   public int getSize()
   {
      return dict.size();
   }

   @Override
   public Dictionary getDictionary()
   {
      return dict;
   }
}
