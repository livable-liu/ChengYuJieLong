package com.java.chengyu;

import org.easymock.EasyMock;

import com.java.chengyu.shared.fileutils.parsers.ChengYuFileParser;
import com.java.chengyu.shared.fileutils.parsers.ParseResult;
import com.java.chengyu.shared.fileutils.parsers.PinYinParser;
import com.java.chengyu.shared.fileutils.parsers.StringSource;
import com.java.chengyu.shared.pronunciation.PinYin;
import com.java.chengyu.shared.pronunciation.PinYinDictionary;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for PinYinParser.
 */
public class PinYinParserTest extends TestCase
{
   /**
    * Create the test case
    *
    * @param testName name of the test case
    */
   public PinYinParserTest(String testName)
   {
      super(testName);
   }

   /**
    * @return the suite of tests being tested
    */
   public static Test suite()
   {
      return new TestSuite(PinYinParserTest.class);
   }

   /**
    * parse Normal
    */
   public void testParseNormal()
   {
      StringBuilder sb = new StringBuilder();
      sb.append("base = a first = ā second = á third = ǎ last = à silence = a cluster = a\r\n");
      sb.append("base = ba first = bā second = bá third = bǎ last = bà silence = ba cluster = ba\r\n");
      
      StringSource source = new StringSource(sb.toString());
      PinYinParser parser = new PinYinParser();
      parser.setSource(source);
      parser.setSplitter("[a-z]+\\s+=\\s+");
      parser.parse();
      
      ParseResult result = parser.getResult();
      Assert.assertEquals("", 2, result.getSize() );  
   }
   
   /**
    * parse Special
    * 1. multiple blanks
    * 2. content not end with \r\n
    * 3. use \t as split
    */
   public void testParseSpecial()
   {
      StringBuilder sb = new StringBuilder();
      sb.append("base = a first = ā   second = á third = ǎ      last = à silence = a cluster = a\r\n");
      sb.append("base = ba first = bā second = bá third = bǎ last = bà silence = ba cluster = b");

      StringSource source = new StringSource(sb.toString());
      PinYinParser parser = new PinYinParser();
      parser.setSource(source);
      parser.setSplitter("[a-z]+\\s+=\\s+");
      parser.parse();

      ParseResult result = parser.getResult();
      Assert.assertEquals("", 2, result.getSize());
   }
}
