package com.java.chengyu.shared.httputils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import com.java.chengyu.shared.objects.ByteBuffer;

public class HTTPSimulator
{
   public static String sendRawSocketGet(String host, int port, String queryPath, String referer,
         String encoding)
      throws UnknownHostException,
         IOException
   {
      System.out.println("Enter sendRawSocketGet!!!!!!!!!!!");
      Socket socket;
      try
      {
         socket = new Socket(host, port);
         socket.setSoTimeout(300000);
         socket.setKeepAlive(true);
      }
      catch (Exception e)
      {
         // e.printStackTrace();
         return "";
      }
      BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
      wr.write("GET " + queryPath + " HTTP/1.1\r\n");
      wr.write("Host: " + host + "\r\n");
      wr.write("Connection: keep-alive" + "\r\n");
      wr.write("Cache-Control: max-age=0" + "\r\n");
      wr.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" + "\r\n");
      wr.write("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36"
            + "\r\n");
      wr.write("Referer: " + referer + "\r\n");
      // wr.write("Accept-Encoding: gzip, deflate\r\n");
      wr.write("Accept-Language: zh-CN,zh;q=0.8\r\n");
      wr.write("\r\n");

      wr.flush();

      BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream(), encoding));
      String line;
      StringBuffer sb = new StringBuffer();
      while ((line = rd.readLine()) != null)
      {
         sb.append(line);
         System.out.println(line);
      }
      wr.close();
      socket.close();
      return sb.toString();
   }

   public static String getHTMLPageFromUrl(String paramString, String referer, String charset)
   {
      try
      {
         URL localURL = new URL(paramString);
         HttpURLConnection localHttpURLConnection = (HttpURLConnection) localURL.openConnection();
         localHttpURLConnection.setRequestMethod("GET");
         localHttpURLConnection.setRequestProperty("User-Agent",
                     "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
         localHttpURLConnection
               .setRequestProperty(
                     "Accept",
               "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
         localHttpURLConnection.setRequestProperty("Cache-Control", "no-cache");
         localHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
         localHttpURLConnection.setRequestProperty("Referer", referer);
         localHttpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
         localHttpURLConnection.setConnectTimeout(10000);
         localHttpURLConnection.setInstanceFollowRedirects(true);
         localHttpURLConnection.connect();
         int i = localHttpURLConnection.getResponseCode();
         if (i != 200)
         {
            localHttpURLConnection.disconnect();
            return null;
         }
         int contentLength = localHttpURLConnection.getContentLength();
         if (contentLength <= 0)
            contentLength = Integer.MAX_VALUE;
         String str = localHttpURLConnection.getHeaderField("Content-Type");
         if (charset == null || "".equals(charset))
         {
            if ((str == null) || (str.indexOf("charset=") < 0))
            {
               str = null;
            }
            else
            {
               str = str.substring(str.indexOf("charset=") + "charset=".length());
            }
         }
         else
         {
            str = charset;
         }
         InputStream localInputStream = localHttpURLConnection.getInputStream();
         byte[] arrayOfByte = new byte[1024];
         int j = 0;
         int len = 0;
         ByteBuffer localByteBuffer = new ByteBuffer(16);
         while (len < contentLength && (j = localInputStream.read(arrayOfByte)) >= 0)
         {
            localByteBuffer.append(arrayOfByte, 0, j);
            len += j;
         }
         localInputStream.close();
         localHttpURLConnection.disconnect();
         if (str == null)
         {
            str = EncodingInformation.getEncodingFromBytes(localByteBuffer.getByteArrayRef());
         }
         return new String(localByteBuffer.getBytes(), str);
      }
      catch (Exception localException)
      {
         // localException.printStackTrace();
      }
      return "";
   }
}
