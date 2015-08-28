package com.java.chengyu.shared.pronunciation;

public class PinYin
{
   public PinYin(String base, String one, String two, String three, String four, char cluster)
   {
      this.base = base; // 拼音（不带声调）
      this.one = one;// 阴平
      this.two = two;// 阳平
      this.three = three;// 上声
      this.four = four;// 去声
      this.cluster = cluster;// 'A-Z'
   }
   
   private String base;
   private String one;
   private String two;
   private String three;
   private String four;
   private char cluster;

   public String getBase()
   {
      return this.base;
   }

   public String getByIndex(int index)
   {
      if (index == 0)
         return this.base;
      else if (index == 1)
      {
         return this.one;
      }
      else if (index == 2)
      {
         return this.two;
      }
      else if (index == 3)
      {
         return this.three;
      }
      else if (index == 4)
      {
         return this.four;
      }
      else
      {
         return this.base;
      }
   }

   public String getFirst()
   {
      return this.one;
   }

   public String getSecond()
   {
      return this.two;
   }

   public String getThird()
   {
      return this.three;
   }

   public String getLast()
   {
      return this.four;
   }

   public String getSilence()
   {
      return this.base;
   }

   public char getCluster()
   {
      return this.cluster;
   }

   @Override
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("base = ");
      sb.append(this.base);
      sb.append(" first = ");
      sb.append(this.one);
      sb.append(" second = ");
      sb.append(this.two);
      sb.append(" third = ");
      sb.append(this.three);
      sb.append(" last = ");
      sb.append(this.four);
      sb.append(" silence = ");
      sb.append(this.base);
      sb.append(" cluster = ");
      sb.append(this.cluster);
      return sb.toString();
   }
}
