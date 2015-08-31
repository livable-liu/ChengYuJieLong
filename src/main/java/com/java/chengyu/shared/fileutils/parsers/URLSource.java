package com.java.chengyu.shared.fileutils.parsers;

import java.net.URL;

public class URLSource extends ParseSource
{
   URLSource(URL url)
   {
      this.url = url;
   }

   private URL url;

   public URL getSource()
   {
      return this.url;
   }

   public void setSource(URL url)
   {
      this.url = url;
   }
}
