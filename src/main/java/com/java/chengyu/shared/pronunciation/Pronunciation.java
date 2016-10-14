package com.java.chengyu.shared.pronunciation;

public class Pronunciation
{
   PinYin pinyin;
   int tone; // 0, 1, 2, 3, 4
   
   public Pronunciation(PinYin pinyin, int tone)
   {
      this.pinyin = pinyin;
      this.tone = tone;
   }
   
   public PinYin getPinYin()
   {
      return this.pinyin;
   }
   
   public int getTone()
   {
      return this.tone;
   }
   
   public String getDisplay()
   {
      return pinyin.getByIndex(tone);
   }
}
