package com.java.chengyu.shared.fileutils.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.java.chengyu.shared.pronunciation.Character;
import com.java.chengyu.shared.pronunciation.ChengYu;
import com.java.chengyu.shared.pronunciation.Dictionary;
import com.java.chengyu.shared.pronunciation.PinYin;
import com.java.chengyu.shared.pronunciation.PinYinDictionary;
import com.java.chengyu.shared.pronunciation.Pronunciation;

public class ChengYuFileParserWithWeight implements Parser
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");

   private String splitter;
   private StringSource content;
   private ChengYuParseResult result;
   private PinYinDictionary dict;

   public void parse()
   {
      String source = content.getSource();
      result = new ChengYuParseResult();
      int line = 1;
      if (content != null && splitter != null)
      {
         int start = 0;
         int index = source.indexOf("\r\n", start);

         while (index > 0 && index + "\r\n".length() <= source.length())
         {
            List<Character> characters = new ArrayList<Character>();
            String tmp = source.substring(start, index);
            String[] array = tmp.split(splitter);
            String[] chengyuStr = array[0].trim().split("");
            for (int i = 1; i < array.length - 1; i++)
            {
               PinYin pinyin = dict.getPinYinFromRawDisplay(array[i]);

               if (pinyin != null)
               {
                  Pronunciation pronunciation = new Pronunciation(pinyin, pinyin.getIndexByDisplay(array[i]));
                  Character character = new Character(chengyuStr[i - 1], pronunciation);
                  characters.add(character);
               }
            }
            String weightStr = array[array.length - 1];
            int weight = 0;
            if (weightStr != null)
            {
               weight = Integer.parseInt(weightStr.trim());
            }
            else
            {
               FUNCTION.error("ChengYuFileParserWithWeight : Wrong weight " + tmp);
            }
            ChengYu chengyu = new ChengYu(characters);
            chengyu.setWeight(weight);

            if (result.getItem(chengyu.toString()) != null)
            {
               FUNCTION.error("ChengYuFileParserWithWeight : item duplicated " + tmp);
            }
            else
            {
               result.addItem(chengyu);
            }
            start = index + "\r\n".length();
            index = source.indexOf("\r\n", start);
            line++;
         }

         // file not end with \r\n
         if (index == -1 && start < source.length() - 1)
         {
            List<Character> characters = new ArrayList<Character>();
            String tmp = source.substring(start);
            String[] array = tmp.split(splitter);
            String[] chengyuStr = array[0].trim().split("");
            for (int i = 1; i < array.length - 1; i++)
            {
               PinYin pinyin = dict.getPinYinFromRawDisplay(array[i]);
               if (pinyin != null)
               {
                  Pronunciation pronunciation = new Pronunciation(pinyin, pinyin.getIndexByDisplay(array[i]));
                  Character character = new Character(chengyuStr[i - 1], pronunciation);
                  characters.add(character);
               }
            }
            String weightStr = array[array.length - 1];
            int weight = 0;
            if (weightStr != null)
            {
               weight = Integer.parseInt(weightStr.trim());
            }
            else
            {
               FUNCTION.error("ChengYuFileParserWithWeight : Wrong weight" + tmp);
            }
            ChengYu chengyu = new ChengYu(characters);
            chengyu.setWeight(weight);

            if (result.getItem(chengyu.toString()) != null)
            {
               FUNCTION.error("ChengYuFileParserWithWeight : item duplicated " + tmp);
            }
            else
            {
               result.addItem(chengyu);
               result.addItem(new ChengYu(characters));
               line++;
            }
         }
         line--;
         System.out.println("Total line count is : " + line);
         System.out.println("ChengYu dictionary size is : " + result.getSize());
      }
   }

   public void setSplitter(String splitter)
   {
      this.splitter = splitter;
   }

   public String getSplitter()
   {
      return this.splitter;
   }

   public void setSource(ParseSource content)
   {
      this.content = (StringSource) content;

   }

   public ParseSource getSource()
   {
      return this.content;

   }

   public ParseResult getResult()
   {
      return this.result;

   }

   public PinYinDictionary getDictionary()
   {
      return this.dict;
   }

   public void setDictionary(Dictionary dict)
   {
      this.dict = (PinYinDictionary) dict;
   }

}
