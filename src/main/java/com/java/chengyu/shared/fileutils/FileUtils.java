package com.java.chengyu.shared.fileutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.java.chengyu.shared.objects.ByteBuffer;

public class FileUtils
{
   static final Logger logger = Logger.getLogger(FileUtils.class);
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

   public static String readStringFromLocalFile(String path, String encoding)
   {
      File f = new File(path);
      FileInputStream fIn;
      try
      {
         fIn = new FileInputStream(f);

         byte[] arrayOfByte = new byte[1024];
         int j = 0;
         ByteBuffer localByteBuffer = new ByteBuffer(16);
         while ((j = fIn.read(arrayOfByte)) >= 0)
         {
            localByteBuffer.append(arrayOfByte, 0, j);
         }
         fIn.close();
         return new String(localByteBuffer.getBytes(), encoding);
      }
      catch (FileNotFoundException e)
      {
         logger.error("Could not read file because of file not found : ", e);
      }
      catch (IOException io)
      {
         logger.error("IOException : ", io);
      }
      return null;
   }
}
