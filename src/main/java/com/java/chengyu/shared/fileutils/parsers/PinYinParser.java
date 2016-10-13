package com.java.chengyu.shared.fileutils.parsers;

import com.java.chengyu.shared.pronunciation.Dictionary;
import com.java.chengyu.shared.pronunciation.PinYin;


public class PinYinParser implements Parser
{
   private String splitter;
   private StringSource content;
   private PinYinParseResult result;

   public void parse()
   {
      String source = content.getSource();
      result = new PinYinParseResult();
      if (content != null && splitter != null)
      {
         int start = 0;
         int index = source.indexOf("\r\n", start);

         // index == -1 && start < source.length() - 1 : this condition is for (file not end with \r\n)
         while (index > 0 && index + "\r\n".length() <= source.length())
         {
            String tmp = source.substring(start, index);
            String[] array = tmp.split(splitter);
            PinYin pinyin = new PinYin(array[1].trim(), array[2].trim(), array[3].trim(), array[4].trim(), array[5].trim(), array[7].charAt(0));
            result.addItem(pinyin);
            start = index + "\r\n".length();
            index = source.indexOf("\r\n", start);
         }
         //file not end with \r\n
         if (index == -1 && start < source.length() - 1)
         {
            String tmp = source.substring(start);
            String[] array = tmp.split(splitter);
            PinYin pinyin = new PinYin(array[1].trim(), array[2].trim(), array[3].trim(), array[4].trim(), array[5].trim(), array[7].charAt(0));
            result.addItem(pinyin);
         }
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

   public void setDictionary(Dictionary dict)
   {
      // TODO Implement setDictionary
      throw new UnsupportedOperationException("setDictionary Not Implemented");
      
   }

}
