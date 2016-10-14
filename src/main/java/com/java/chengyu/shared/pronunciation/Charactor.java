package com.java.chengyu.shared.pronunciation;

public class Charactor
{
   String charactorString;
   Pronunciation pronunciation;
   
   public Charactor(String charactorString, Pronunciation pronunciation)
   {
      this.charactorString = charactorString;
      this.pronunciation = pronunciation;
   }
   
   public Pronunciation getPronunciation()
   {
      return this.pronunciation;
   }
   
   public String getCharactorString()
   {
      return this.charactorString;
   }
}
