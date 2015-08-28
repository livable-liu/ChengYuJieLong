package com.java.chengyu.shared.objects;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class ByteBuffer
{
   public static final int DEFAULT_SIZE = 512;
   private byte[] array = null;
   private int used = 0;
   private int capacity = 0;

   public ByteBuffer(int paramInt)
   {
      this.capacity = paramInt;
      this.array = new byte[paramInt];
   }

   public ByteBuffer(byte[] paramArrayOfByte)
   {
      this(paramArrayOfByte, 0, paramArrayOfByte.length);
   }

   public ByteBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
   {
      this(paramInt2 + 32);
      append(paramArrayOfByte, paramInt1, paramInt2);
   }

   public ByteBuffer(StringBuffer paramStringBuffer)
   {
      this(paramStringBuffer.toString());
   }

   public ByteBuffer(StringBuffer paramStringBuffer, String paramString)
      throws UnsupportedEncodingException
   {
      this(paramStringBuffer.toString(), paramString);
   }

   public ByteBuffer(String paramString)
   {
      this(paramString.getBytes());
   }

   public ByteBuffer(String paramString1, String paramString2)
      throws UnsupportedEncodingException
   {
      this(paramString1.getBytes(paramString2));
   }

   public void purge()
   {
      purge(512);
   }

   public synchronized void purge(int paramInt)
   {
      this.capacity = paramInt;
      this.array = new byte[paramInt];
      this.used = 0;
   }

   public int length()
   {
      return this.used;
   }

   public int size()
   {
      return this.used;
   }

   public int capacity()
   {
      return this.capacity;
   }

   public synchronized void ensureCapacity(int paramInt)
   {
      if (paramInt > this.capacity)
      {
         this.capacity = Math.max((this.capacity + 1) * 2, paramInt);
         byte[] arrayOfByte = this.array;
         this.array = new byte[this.capacity];
         System.arraycopy(arrayOfByte, 0, this.array, 0, this.used);
      }
   }

   public synchronized void setLength(int paramInt)
      throws StringIndexOutOfBoundsException
   {
      if (paramInt < 0)
      {
         throw new StringIndexOutOfBoundsException(paramInt);
      }
      ensureCapacity(paramInt);
      this.used = paramInt;
   }

   public synchronized byte byteAt(int paramInt)
      throws ArrayIndexOutOfBoundsException
   {
      if (paramInt >= this.used)
      {
         throw new ArrayIndexOutOfBoundsException();
      }
      return this.array[paramInt];
   }

   public synchronized long longAt(int paramInt)
      throws ArrayIndexOutOfBoundsException
   {
      if (paramInt + 7 >= this.used)
      {
         throw new ArrayIndexOutOfBoundsException();
      }
      long l = this.array[paramInt] & 0xFF;
      for (int i = 1; i <= 7; i++)
      {
         l = l << 8 | this.array[(paramInt + i)] & 0xFF;
      }
      return l;
   }

   public synchronized byte[] getBytes()
   {
      byte[] arrayOfByte = new byte[this.used];
      System.arraycopy(this.array, 0, arrayOfByte, 0, this.used);
      return arrayOfByte;
   }

   public byte[] getByteArrayRef()
   {
      return this.array;
   }

   public synchronized void getBytes(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
      throws ArrayIndexOutOfBoundsException
   {
      if ((paramInt1 >= this.used) || (paramInt2 > this.used))
      {
         throw new ArrayIndexOutOfBoundsException();
      }
      if (paramInt1 < paramInt2)
      {
         System.arraycopy(this.array, paramInt1, paramArrayOfByte, paramInt3, paramInt2 - paramInt1);
      }
   }

   public synchronized void setByteAt(int paramInt, byte paramByte)
      throws ArrayIndexOutOfBoundsException
   {
      if (paramInt >= this.used)
      {
         throw new ArrayIndexOutOfBoundsException(paramInt);
      }
      this.array[paramInt] = paramByte;
   }

   public synchronized void append(byte paramByte)
   {
      ensureCapacity(this.used + 1);
      this.array[(this.used++)] = paramByte;
   }

   public synchronized void append(long paramLong)
   {
      ensureCapacity(this.used + 8);
      for (int i = 56; i >= 0; i -= 8)
      {
         this.array[(this.used++)] = ((byte) (int) (paramLong >> i & 0xFF));
      }
   }

   public synchronized void append(byte[] paramArrayOfByte)
   {
      append(paramArrayOfByte, 0, paramArrayOfByte.length);
   }

   public synchronized void append(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
   {
      ensureCapacity(this.used + paramInt2);
      System.arraycopy(paramArrayOfByte, paramInt1, this.array, this.used, paramInt2);
      this.used += paramInt2;
   }

   public synchronized void insert(int paramInt, byte[] paramArrayOfByte)
      throws ArrayIndexOutOfBoundsException
   {
      if ((paramInt < 0) || (paramInt > this.used))
      {
         throw new ArrayIndexOutOfBoundsException();
      }
      int i = paramArrayOfByte.length;
      ensureCapacity(this.used + i);
      System.arraycopy(this.array, paramInt, this.array, paramInt + i, this.used - paramInt);
      System.arraycopy(paramArrayOfByte, 0, this.array, paramInt, i);
      this.used += i;
   }

   public synchronized void insert(int paramInt, byte paramByte)
   {
      ensureCapacity(this.used + 1);
      System.arraycopy(this.array, paramInt, this.array, paramInt + 1, this.used - paramInt);
      this.array[paramInt] = paramByte;
      this.used += 1;
   }

   public synchronized void write(OutputStream paramOutputStream)
      throws IOException,
         ArrayIndexOutOfBoundsException
   {
      write(paramOutputStream, 0, this.used);
   }

   public synchronized void write(OutputStream paramOutputStream, int paramInt1, int paramInt2)
      throws IOException,
         ArrayIndexOutOfBoundsException
   {
      if (paramInt1 + paramInt2 > this.used)
      {
         throw new ArrayIndexOutOfBoundsException("Invalid offset or length");
      }
      paramOutputStream.write(this.array, paramInt1, paramInt2);
   }
}
