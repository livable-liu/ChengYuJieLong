package com.java.chengyu.shared.fileutils.parsers;

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

         while (index > 0 && index + "\r\n".length() < source.length() - 1)
         {
            String tmp = source.substring(start, index);
            String[] array = tmp.split(splitter);
            PinYin pinyin = new PinYin(array[1], array[2], array[3], array[4], array[5], array[7].charAt(0));
            result.addItem(pinyin);
            start = index + "\r\n".length();
            index = source.indexOf("\r\n", start);
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

}
