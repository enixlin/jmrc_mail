package com.snxoa.email.applet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Template
{
  public Template() {}
  
  public static String getTemplate(String filePath)
  {
    File rf = new File(filePath);
    StringBuffer sLine = new StringBuffer("");
    try
    {
      InputStreamReader read = new InputStreamReader(new FileInputStream(rf), "utf-8");
      
      BufferedReader reader = new BufferedReader(read);
      
      String line = new String();
      while ((line = reader.readLine()) != null) {
        sLine.append(line).append('\r').append('\n');
      }
      reader.close();
      read.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return sLine.toString();
  }
  
  public static String replace(String strSource, String strFrom, String strTo)
  {
    if (strSource == null) {
      return strSource;
    }
    String strDest = "";
    int intFromLen = strFrom.length();
    if (strTo == null) {
      strTo = "";
    }
    int intPos;
    while ((intPos = strSource.indexOf(strFrom)) != -1)
    {
      strDest = strDest + strSource.substring(0, intPos);
      strDest = strDest + strTo;
      strSource = strSource.substring(intPos + intFromLen);
    }
    strDest = strDest + strSource;
    
    return strDest;
  }
  
  public static void main(String[] args) {}
}


/* Location:              D:\tools\developt\jar反编译工具\Applet.jar!\com\snxoa\email\applet\Template.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */