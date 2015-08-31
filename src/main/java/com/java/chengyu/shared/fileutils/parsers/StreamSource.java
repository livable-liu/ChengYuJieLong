package com.java.chengyu.shared.fileutils.parsers;

import java.io.InputStream;

public class StreamSource extends ParseSource
{
   StreamSource(InputStream in)
   {
      this.in = in;
   }

   private InputStream in;

   public InputStream getSource()
   {
      return this.in;
   }

   public void setSource(InputStream in)
   {
      this.in = in;
   }

}
