package com.java.chengyu.shared.fileutils.parsers;


public class StringSource extends ParseSource
{
   StringSource(String content)
   {
      this.content = content;
   }

   private String content;

   public String getSource()
   {
      return this.content;
   }

   public void setSource(String content)
   {
      this.content = content;
   }

}
