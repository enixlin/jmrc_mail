package com.snxoa.email.applet;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class DiskTool
{
  private DiskTool() {}
  
  public static long getFreeDiskSpace(String dirName)
  {
    try
    {
      String temp = "";
      if (dirName.indexOf(":") > 0) {
        temp = dirName.substring(0, dirName.indexOf(":") + 1);
      }
      System.out.println(temp);
      
      String os = System.getProperty("os.name");
      String command;
      if (os.startsWith("Windows")) {
        command = "cmd.exe /c dir " + temp;
      } else {
        command = "command.com /c dir " + temp;
      }
      Runtime runtime = Runtime.getRuntime();
      Process process = null;
      process = runtime.exec(command);
      if (process == null) {
        return -1L;
      }
      BufferedReader in = new BufferedReader(new InputStreamReader(
        process.getInputStream()));
      
      String freeSpace = null;
      String line;
      while ((line = in.readLine()) != null)
      {
        freeSpace = line;
      }
      if (freeSpace == null)
      {
        System.out.println("freeSpace is null");
        return -1L;
      }
      process.destroy();
      
      freeSpace = freeSpace.trim();
      freeSpace = freeSpace.replaceAll("\\.", "");
      freeSpace = freeSpace.replaceAll(",", "");
      String[] items = freeSpace.split(" ");
      
      int index = 1;
      while (index < items.length) {
        try
        {
          return Long.parseLong(items[(index++)]);
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
      System.out.println("testing is -1");
      return -1L;
    }
    catch (Exception exception)
    {
      System.out.println("exception:" + exception);
    }
    return -1L;
  }
  
  public static void main(String[] args)
  {
    System.out.println(getFreeDiskSpace("d:"));
  }
  
  public static String createFolder(String folderPath)
  {
    String txt = folderPath;
    try
    {
      File myFilePath = new File(txt);
      txt = folderPath;
      if (!myFilePath.exists()) {
        myFilePath.mkdir();
      }
    }
    catch (Exception e)
    {
      System.out.println("����Ŀ¼��������");
    }
    return txt;
  }
  
  public static boolean checkPathExists(String folderPath)
  {
    boolean isExists = false;
    try
    {
      File myFilePath = new File(folderPath);
      isExists = myFilePath.exists();
    }
    catch (Exception e)
    {
      System.out.println("Ŀ¼������");
    }
    return isExists;
  }
}


/* Location:              D:\tools\developt\jar反编译工具\Applet.jar!\com\snxoa\email\applet\DiskTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */