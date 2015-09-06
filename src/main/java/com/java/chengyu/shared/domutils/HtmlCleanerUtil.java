package com.java.chengyu.shared.domutils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlCleanerUtil
{
   public static Object[] findByXpath(TagNode dom, String xpath)
      throws XPatherException
   {
      Object[] nodes = dom.evaluateXPath(xpath);
      return nodes;
   }

   public static TagNode parse(String content)
   {
      // create an instance of HtmlCleaner
      HtmlCleaner cleaner = new HtmlCleaner();

      // take default cleaner properties
      CleanerProperties props = cleaner.getProperties();
      props.setAllowHtmlInsideAttributes(true);
      props.setAllowMultiWordAttributes(true);
      props.setRecognizeUnicodeChars(true);
      props.setOmitComments(true);
      
      TagNode node = cleaner.clean(content);
      return node;
   }

   public static TagNode parse(InputStream in)
      throws IOException
   {
      // create an instance of HtmlCleaner
      HtmlCleaner cleaner = new HtmlCleaner();

      // take default cleaner properties
      CleanerProperties props = cleaner.getProperties();
      props.setAllowHtmlInsideAttributes(true);
      props.setAllowMultiWordAttributes(true);
      props.setRecognizeUnicodeChars(true);
      props.setOmitComments(true);

      TagNode node = cleaner.clean(in);
      return node;
   }

   public static TagNode parse(URL url)
      throws IOException
   {
      // create an instance of HtmlCleaner
      HtmlCleaner cleaner = new HtmlCleaner();

      // take default cleaner properties
      CleanerProperties props = cleaner.getProperties();
      props.setAllowHtmlInsideAttributes(true);
      props.setAllowMultiWordAttributes(true);
      props.setRecognizeUnicodeChars(true);
      props.setOmitComments(true);

      TagNode node = cleaner.clean(url);
      return node;
   }
}