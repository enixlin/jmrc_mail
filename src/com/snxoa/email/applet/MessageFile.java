package com.snxoa.email.applet;

import java.io.Serializable;
import java.util.Date;

public class MessageFile
  implements Serializable
{
  private static final long serialVersionUID = 7851058396057442022L;
  private long messageId;
  private long tid;
  private String senderName;
  private Date sendDate;
  private String title;
  private long filesize;
  private String filename;
  private String filePath;
  private long fileId;
  
  public MessageFile() {}
  
  public long getMessageId()
  {
    return this.messageId;
  }
  
  public void setMessageId(long messageId)
  {
    this.messageId = messageId;
  }
  
  public long getTid()
  {
    return this.tid;
  }
  
  public void setTid(long tid)
  {
    this.tid = tid;
  }
  
  public String getSenderName()
  {
    return this.senderName;
  }
  
  public void setSenderName(String senderName)
  {
    this.senderName = senderName;
  }
  
  public Date getSendDate()
  {
    return this.sendDate;
  }
  
  public void setSendDate(Date sendDate)
  {
    this.sendDate = sendDate;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public long getFilesize()
  {
    return this.filesize;
  }
  
  public void setFilesize(long filesize)
  {
    this.filesize = filesize;
  }
  
  public String getFilename()
  {
    return this.filename;
  }
  
  public void setFilename(String filename)
  {
    this.filename = filename;
  }
  
  public String getFilePath()
  {
    return this.filePath;
  }
  
  public void setFilePath(String filePath)
  {
    this.filePath = filePath;
  }
  
  public long getFileId()
  {
    return this.fileId;
  }
  
  public void setFileId(long fileId)
  {
    this.fileId = fileId;
  }
}


/* Location:              D:\tools\developt\jar反编译工具\Applet.jar!\com\snxoa\email\applet\MessageFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */