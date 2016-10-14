package com.java.chengyu.shared.pronunciation;

public class Character
{
   String characterString;
   Pronunciation pronunciation;
   
   public Character(String characterString, Pronunciation pronunciation)
   {
      this.characterString = characterString;
      this.pronunciation = pronunciation;
   }
   
   public Pronunciation getPronunciation()
   {
      return this.pronunciation;
   }
   
   public String getCharacterString()
   {
      return this.characterString;
   }
}
