package com.java.chengyu.shared.fileutils.parsers;

import com.java.chengyu.shared.pronunciation.PinYin;


public class PinYinParser implements Parser
{
   private String splitter;
   private StringSource content;
   private PinYinParseResult result;

   public ParseResult parse()
   {
      String source = content.getSource();
      result = new PinYinParseResult();
      if (content != null && splitter != null)
      {
         int start = 0;
         int index = source.indexOf("\r\n", start);

         while (index > 0)
         {
            String tmp = source.substring(start, index);
            String[] array = tmp.split(splitter);
            for (int i = 0; i < array.length; i++)
            {
               PinYin pinyin = new PinYin(array[0], array[1], array[2], array[3], array[4],
                     array[6].charAt(0));
               result.addItem(pinyin);
            }
         }
      }
      return result;
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
