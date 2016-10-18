package com.java.chengyu.shared.domutils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Document;

public class HtmlCleanerUtil
{
   private static final HtmlCleaner cleaner = new HtmlCleaner();

   public static Object[] findByXpath(TagNode dom, String xpath)
      throws XPatherException
   {
      Object[] nodes = dom.evaluateXPath(xpath);
      return nodes;
   }

   public static TagNode parse(String content)
   {
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
      // take default cleaner properties
      CleanerProperties props = cleaner.getProperties();
      props.setAllowHtmlInsideAttributes(true);
      props.setAllowMultiWordAttributes(true);
      props.setRecognizeUnicodeChars(true);
      props.setOmitComments(true);

      TagNode node = cleaner.clean(url);
      return node;
   }
   
   public static Document generateW3cDocument(TagNode node)
   {
      Document document;
      try
      {
         document = new DomSerializer(cleaner.getProperties()).createDOM(node);
      }
      catch (ParserConfigurationException e)
      {
         document = null;
      }
      return document;
   }
}
