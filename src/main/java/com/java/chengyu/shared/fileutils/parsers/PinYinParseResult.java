package com.java.chengyu.shared.fileutils.parsers;

import java.util.Collection;

import com.java.chengyu.shared.pronunciation.PinYin;
import com.java.chengyu.shared.pronunciation.PinYinDictionary;

public class PinYinParseResult extends ParseResult
{
   PinYinDictionary dict;

   PinYinParseResult()
   {
      dict = new PinYinDictionary();
   }

   public void addItem(PinYin pinyin)
   {
      dict.putPinYinUnderDisplay(pinyin.getBase(), pinyin);
   }

   public PinYin getItem(String base)
   {
      return dict.getPinYinFromDisplay(base);
   }

   public Collection<PinYin> getAllItems()
   {
      return dict.values();
   }

   public int getSize()
   {
      return dict.size();
   }
}
