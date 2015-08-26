package com.java.chengyu.shared.httputils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTPSimulator
{
   public static String sendRawSocketGet(String host, int port, String queryPath, String encoding)
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
         e.printStackTrace();
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
      wr.write("Referer: http://www.zdic.net/c/4/13c/301992.htm\r\n");
      // wr.write("Accept-Encoding: gzip, deflate\r\n");
      wr.write("Accept-Language: zh-CN,zh;q=0.8\r\n");
      wr.write("\r\n");

      wr.flush();

      InputStream in = socket.getInputStream();
      byte[] bytes = new byte[8192];
      int read = 0;
      StringBuffer sb = new StringBuffer();
      while ((read = in.read(bytes)) >= 0)
      {
         sb.append(new String(bytes, 0, read, encoding));
      }
      wr.close();
      socket.close();
      return sb.toString();
   }
}
