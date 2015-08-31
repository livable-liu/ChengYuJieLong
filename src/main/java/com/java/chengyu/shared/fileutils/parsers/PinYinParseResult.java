package com.java.chengyu.shared.fileutils.parsers;

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
}
