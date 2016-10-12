package com.java.chengyu.shared.fileutils.parsers;

import java.util.Collection;

import com.java.chengyu.shared.pronunciation.Dictionary;

public abstract class ParseResult
{
   public abstract Collection< ? > getAllItems();

   public abstract int getSize();
   
   public abstract Dictionary getDictionary();
}
