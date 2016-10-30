package com.java.chengyu;

import org.easymock.EasyMock;

import com.java.chengyu.shared.fileutils.parsers.ChengYuFileParser;
import com.java.chengyu.shared.fileutils.parsers.ParseResult;
import com.java.chengyu.shared.fileutils.parsers.StringSource;
import com.java.chengyu.shared.pronunciation.PinYin;
import com.java.chengyu.shared.pronunciation.PinYinDictionary;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ChengYuFileParserTest extends TestCase
{
   /**
    * Create the test case
    *
    * @param testName name of the test case
    */
   public ChengYuFileParserTest(String testName)
   {
      super(testName);
   }

   /**
    * @return the suite of tests being tested
    */
   public static Test suite()
   {
      return new TestSuite(ChengYuFileParserTest.class);
   }

   /**
    * parse Normal
    */
   public void testParseNormal()
   {
      StringBuilder sb = new StringBuilder();
      sb.append("风风云云 fēng fēng yún yún \r\n");
      sb.append("大大小小 dà dà xiǎo xiǎo \r\n");
      
      PinYin feng = new PinYin("feng","fēng","féng","fěng","fèng",'f');
      PinYin yun = new PinYin("yun","yūn","yún","yǔn","yùn",'y');
      PinYin da = new PinYin("da","dā","dá","dǎ","dà",'d');
      PinYin xiao = new PinYin("xiao","xiāo","xiáo","xiǎo","xiào",'y');
      
      
      PinYinDictionary dictMock = EasyMock.createMock(PinYinDictionary.class);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("fēng")).andReturn(feng);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("fēng")).andReturn(feng);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("yún")).andReturn(yun);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("yún")).andReturn(yun);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("dà")).andReturn(da);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("dà")).andReturn(da);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("xiǎo")).andReturn(xiao);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("xiǎo")).andReturn(xiao);
      EasyMock.replay(dictMock);
      
      StringSource source = new StringSource(sb.toString());
      ChengYuFileParser parser = new ChengYuFileParser();
      parser.setSource(source);
      parser.setSplitter("\\s+");
      parser.setDictionary(dictMock);
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
      sb.append("风风云云   fēng fēng yún yún  \r\n");
      sb.append("大大小小  dà dà xiǎo xiǎo ");
      
      PinYin feng = new PinYin("feng","fēng","féng","fěng","fèng",'f');
      PinYin yun = new PinYin("yun","yūn","yún","yǔn","yùn",'y');
      PinYin da = new PinYin("da","dā","dá","dǎ","dà",'d');
      PinYin xiao = new PinYin("xiao","xiāo","xiáo","xiǎo","xiào",'y');
      
      
      PinYinDictionary dictMock = EasyMock.createMock(PinYinDictionary.class);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("fēng")).andReturn(feng);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("fēng")).andReturn(feng);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("yún")).andReturn(yun);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("yún")).andReturn(yun);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("dà")).andReturn(da);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("dà")).andReturn(da);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("xiǎo")).andReturn(xiao);
      EasyMock.expect(dictMock.getPinYinFromRawDisplay("xiǎo")).andReturn(xiao);
      EasyMock.replay(dictMock);
      
      StringSource source = new StringSource(sb.toString());
      ChengYuFileParser parser = new ChengYuFileParser();
      parser.setSource(source);
      parser.setSplitter("\\s+");
      parser.setDictionary(dictMock);
      parser.parse();
      
      ParseResult result = parser.getResult();
      Assert.assertEquals("", 2, result.getSize() );  
   }
}
