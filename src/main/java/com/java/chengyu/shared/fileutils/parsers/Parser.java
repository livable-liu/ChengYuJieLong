package com.java.chengyu.shared.fileutils.parsers;


public interface Parser
{
   ParseResult parse();

   void setSplitter(String splitter);

   String getSplitter();

   void setSource(ParseSource source);

   ParseSource getSource();

   ParseResult getResult();

}
