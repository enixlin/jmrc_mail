package com.snxoa.email.applet;

public abstract interface ProgressListener
{
  public abstract void showProgress(int paramInt1, int paramInt2);
  
  public abstract void finish(boolean paramBoolean);
  
  public abstract void setCurrentProgress(int paramInt);
  
  public abstract void showCurrentProgress(int paramInt);
  
  public abstract boolean fault(String paramString1, Long paramLong, String paramString2);
  
  public abstract void deleteFeedBack(boolean paramBoolean, String paramString, Long paramLong1, Long paramLong2);
}


/* Location:              D:\tools\developt\jar反编译工具\Applet.jar!\com\snxoa\email\applet\ProgressListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */