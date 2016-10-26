package com.java.chengyu.shared.pronunciation;

import java.util.List;

public class ChengYu
{
   List<Character> characters;
   
   int weight;
   
   public ChengYu()
   {
      
   }
   
   public ChengYu(List<Character> characters)
   {
      this.characters = characters;
   }
   

   public Pronunciation getFirstPronunciation()
   {
      return characters.get(0).getPronunciation();
   }
   
   public Pronunciation getLastPronunciation()
   {
      return characters.get(characters.size() - 1).getPronunciation();
   }
   
   public List<Character> getCharacters()
   {
      return characters;
   }
   
   public int getWeight()
   {
	   return weight;
   }
   
   public void setWeight(int weight)
   {
	   this.weight = weight;
   }
   
   public String toString()
   {
      StringBuilder sb = new StringBuilder();
      for (int i = 0 ; i < characters.size(); i ++)
      {
         Character character = characters.get(i);
         sb.append(character.getCharacterString());
         sb.append("(");
         sb.append(character.getPronunciation().getPinYin().getByIndex(character.getPronunciation().getTone()));
         sb.append(")");
      }
      return sb.toString();
   }
   
}
