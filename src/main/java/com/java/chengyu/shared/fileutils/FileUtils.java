package com.java.chengyu.shared.fileutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FileUtils
{
   public static void writeStringToLocalFile(String path, String content, String encoding)
      throws IOException
   {
      File f = new File(path);
      FileOutputStream fOut = new FileOutputStream(f);
      try
      {
         fOut.write(content.getBytes(encoding));
         fOut.close();
      }
      catch (UnsupportedEncodingException e)
      {
         e.printStackTrace();
      }
   }
}
