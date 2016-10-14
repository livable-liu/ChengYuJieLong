package com.java.chengyu.shared.pronunciation;

import java.util.List;

public class ChengYu
{
   List<Charactor> charactors;
   
   public ChengYu()
   {
      
   }
   
   public ChengYu(List<Charactor> charactors)
   {
      this.charactors = charactors;
   }
   

   public Pronunciation getFirstPronunciation()
   {
      return charactors.get(0).getPronunciation();
   }
   
   public Pronunciation getLastPronunciation()
   {
      return charactors.get(charactors.size() - 1).getPronunciation();
   }
   
   public List<Charactor> getCharactors()
   {
      return charactors;
   }
   
   public String toString()
   {
      StringBuilder sb = new StringBuilder();
      for (int i = 0 ; i < charactors.size(); i ++)
      {
         Charactor charactor = charactors.get(i);
         sb.append(charactor.getCharactorString());
         sb.append("(");
         sb.append(charactor.getPronunciation().getPinYin().getByIndex(charactor.getPronunciation().getTone()));
         sb.append(")");
      }
      return sb.toString();
   }
   
}
