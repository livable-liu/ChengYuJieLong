package com.java.chengyu.shared.fileutils.parsers;

import com.java.chengyu.shared.pronunciation.Dictionary;


public interface Parser
{
   void parse();

   void setSplitter(String splitter);

   String getSplitter();

   void setSource(ParseSource source);

   ParseSource getSource();

   ParseResult getResult();
   
   void setDictionary(Dictionary dict);
   
}
