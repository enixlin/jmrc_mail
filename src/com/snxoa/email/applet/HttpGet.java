package com.snxoa.email.applet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class HttpGet
{
  public static final boolean DEBUG = false;
  private static int BUFFER_SIZE = 8192;
  private Vector vDownLoad = new Vector();
  private Vector vFileList = new Vector();
  private Vector vFileSize = new Vector();
  private Vector vType = new Vector();
  private Vector vAttachList = new Vector();
  
  public HttpGet() {}
  
  public void resetList()
  {
    this.vDownLoad.clear();
    this.vFileList.clear();
  }
  
  public void addItem(String url, String filename, Long filesize, String type, List attachIds)
  {
    this.vDownLoad.add(url);
    this.vFileList.add(filename);
    this.vFileSize.add(filesize);
    this.vType.add(type);
    this.vAttachList.add(attachIds);
  }
  
  public void downLoadByList(final ProgressListener listener)
  {
    Thread thread = new Thread(new Runnable()
    {
      public void run()
      {
        int total = HttpGet.this.vDownLoad.size();
        boolean isSuccess = false;
        
        int cnt = 0;
        try
        {
          String url = "";
          String filename = "";
          String type = "";
          List lstAttach = new ArrayList();
          Long tid = new Long(0L);
          Long mid = new Long(0L);
          for (int i = 0; i < total; i++)
          {
            url = (String)HttpGet.this.vDownLoad.get(i);
            filename = (String)HttpGet.this.vFileList.get(i);
            type = (String)HttpGet.this.vType.get(i);
            lstAttach = (List)HttpGet.this.vAttachList.get(i);
            int fileSize = ((Long)HttpGet.this.vFileSize.get(i)).intValue();
            listener.setCurrentProgress(fileSize);
            try
            {
              isSuccess = HttpGet.this.saveMessage(url, filename, listener, lstAttach);
              
              String[] params = url.substring(url.indexOf("?") + 1, url.length()).split("&");
              mid = new Long(params[1].substring(4, params[1].length()));
              tid = new Long(params[2].substring(4, params[2].length()));
              if (isSuccess)
              {
                listener.deleteFeedBack(true, type, tid, mid);
                cnt++;
              }
              else
              {
                String title = filename.substring(filename.indexOf("]") + 1, filename.lastIndexOf("."));
                if (!listener.fault(type, mid, title)) {
                  break;
                }
              }
              if (i % 10 == 0) {
                Thread.sleep(2000L);
              } else {
                Thread.sleep(200L);
              }
            }
            catch (Exception e)
            {
              System.out.println(e.getMessage());
              isSuccess = false;
              String title = filename.substring(filename.indexOf("]") + 1, filename.lastIndexOf("."));
              if (listener.fault(type, mid, title)) {
                //break label408;
              }
            }
            break;
            //label408:
           // listener.showProgress(i + 1, total);
          }
        }
        catch (Exception e)
        {
          isSuccess = false;
          e.printStackTrace();
          System.out.println(e.getMessage());
        }
        listener.finish(cnt == total);
      }
    });
    thread.start();
  }
  
  public boolean saveToFile(String destUrl, String fileName, ProgressListener listener)
    throws IOException, InterruptedException
  {
    boolean isSuccess = true;
    
    FileOutputStream fos = null;
    BufferedInputStream bis = null;
    HttpURLConnection httpUrl = null;
    URL url = null;
    byte[] buf = new byte[BUFFER_SIZE];
    int size = 0;
    try
    {
      url = new URL(destUrl);
      httpUrl = (HttpURLConnection)url.openConnection();
      
      httpUrl.connect();
      
      bis = new BufferedInputStream(httpUrl.getInputStream());
      
      fos = new FileOutputStream(fileName);
      while ((size = bis.read(buf)) != -1)
      {
        fos.write(buf, 0, size);
        listener.showCurrentProgress(size);
      }
      fos.close();
      bis.close();
      httpUrl.disconnect();
      
      Thread.sleep(10L);
    }
    catch (IOException e)
    {
      isSuccess = false;
      throw e;
    }
    catch (InterruptedException e)
    {
      isSuccess = false;
      throw e;
    }
    return isSuccess;
  }
  
  public boolean saveMessage(String destUrl, String fileName, ProgressListener listener, List lstAttach)
    throws IOException, InterruptedException
  {
    boolean isSuccess = false;
    
    isSuccess = saveToFile(destUrl, fileName, listener);
    if (!isSuccess) {
      return false;
    }
    for (int i = 0; i < lstAttach.size(); i++)
    {
      String attachUrl = (String)lstAttach.get(i);
      String[] aUrl = attachUrl.split("@@");
      if ((aUrl != null) && (aUrl.length == 2))
      {
        String url = aUrl[0];
        String filename = aUrl[1];
        
        isSuccess = saveToFile(url, filename, listener);
        if (!isSuccess) {
          return false;
        }
      }
    }
    return isSuccess;
  }
  
  public DownloadTask getDownloadTask(String strUrl)
  {
    HttpURLConnection httpUrl = null;
    URL url = null;
    
    DownloadTask task = null;
    try
    {
      url = new URL(strUrl);
      httpUrl = (HttpURLConnection)url.openConnection();
      
      httpUrl.connect();
      
      ObjectInputStream ois = new ObjectInputStream(httpUrl.getInputStream());
      
      task = (DownloadTask)ois.readObject();
      ois.close();
      
      httpUrl.disconnect();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println("DownloadTask error:" + e);
    }
    return task;
  }
  
  public String sendFeedBack(String strUrl, DownloadFeedback feed)
  {
    HttpURLConnection httpConn = null;
    URL url = null;
    String result = "success";
    try
    {
      StringBuffer info = new StringBuffer();
      
      info.append("&receiveIds=");
      Long[] receiveIds = feed.getReceiveIds();
      for (int i = 0; i < receiveIds.length; i++) {
        info.append(receiveIds[i]).append(",");
      }
      if (receiveIds.length > 0) {
        info.deleteCharAt(info.length() - 1);
      }
      info.append("&sendIds=");
      Long[] sendIds = feed.getSendIds();
      for (int i = 0; i < sendIds.length; i++) {
        info.append(sendIds[i]).append(",");
      }
      if (sendIds.length > 0) {
        info.deleteCharAt(info.length() - 1);
      }
      info.append("&draftIds=");
      Long[] draftIds = feed.getDraftIds();
      for (int i = 0; i < draftIds.length; i++) {
        info.append(draftIds[i]).append(",");
      }
      if (draftIds.length > 0) {
        info.deleteCharAt(info.length() - 1);
      }
      info.append("&wasteIds=");
      Long[] wasteIds = feed.getWasteIds();
      for (int i = 0; i < wasteIds.length; i++) {
        info.append(wasteIds[i]).append(",");
      }
      if (wasteIds.length > 0) {
        info.deleteCharAt(info.length() - 1);
      }
      info.append("&otherIds=");
      Long[] otherIds = feed.getOtherIds();
      for (int i = 0; i < otherIds.length; i++) {
        info.append(otherIds[i]).append(",");
      }
      if (otherIds.length > 0) {
        info.deleteCharAt(info.length() - 1);
      }
      info.append("&mIds=");
      List mIds = feed.getMidList();
      for (int i = 0; i < mIds.size(); i++) {
        info.append(mIds.get(i)).append(",");
      }
      if (mIds.size() > 0) {
        info.deleteCharAt(info.length() - 1);
      }
      url = new URL(strUrl + info);
      
      httpConn = (HttpURLConnection)url.openConnection();
      
      httpConn.setDoOutput(true);
      
      httpConn.connect();
      
      httpConn.connect();
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null)
      {
        System.out.println(line);
        result = line;
      }
      reader.close();
      
      httpConn.disconnect();
    }
    catch (Exception e)
    {
      result = "fail";
      e.printStackTrace();
    }
    return result;
  }
  
  public static void main(String[] argv)
  {
    HttpGet instance = new HttpGet();
    try
    {
      instance.downLoadByList(null);
    }
    catch (Exception err)
    {
      err.printStackTrace();
      System.out.println(err.getMessage());
    }
  }
  
  public String getSpeed(double s)
  {
    String size = "";
    if (s > 1024.0D)
    {
      s /= 1024.0D;
      size = new DecimalFormat("#,##0.00").format(s) + "MB";
    }
    else
    {
      size = new DecimalFormat("#,##0.00").format(s) + "KB";
    }
    return size;
  }
  
//  public String getTime(long ms)
//  {
//    int ss = 1000;
//    int mi = ss * 60;
//    int hh = mi * 60;
//    int dd = hh * 24;
//    
//    long day = ms / dd;
//    long hour = (ms - day * dd) / hh;
//    long minute = (ms - day * dd - hour * hh) / mi;
//    long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//    long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
//    
//    String strDay = day;
//    String strHour = hour;
//    String strMinute = minute;
//    String strSecond = second;
//    String strMilliSecond = milliSecond;
//    strMilliSecond = strMilliSecond;
//    if (day > 0L) {
//      return strDay + "天" + strHour + "时" + strMinute + "分" + strSecond + "秒";
//    }
//    if (hour > 0L) {
//      return strHour + "时" + strMinute + "分" + strSecond + "秒";
//    }
//    if (minute > 0L) {
//      return strMinute + "分" + strSecond + "秒";
//    }
//    if (second > 0L) {
//      return strSecond + "秒";
//    }
//    if (milliSecond > 0L) {
//      return strMilliSecond + "毫秒";
//    }
//    return ms + "毫秒";
//  }
}


/* Location:              D:\tools\developt\jar反编译工具\Applet.jar!\com\snxoa\email\applet\HttpGet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */