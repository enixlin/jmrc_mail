package com.snxoa.email.applet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DownloadTask
  implements Serializable
{
  private static final long serialVersionUID = 3697346132684098095L;
  private List receiveList = new ArrayList(0);
  private List sendList = new ArrayList(0);
  private List draftList = new ArrayList(0);
  private List wasteList = new ArrayList(0);
  private List otherFileList = new ArrayList(0);
  private List receiveAttachList = new ArrayList(0);
  private List sendAttachList = new ArrayList(0);
  private List draftAttachList = new ArrayList(0);
  private List wasteAttachList = new ArrayList(0);
  private List otherFileAttachList = new ArrayList(0);
  
  public DownloadTask() {}
  
  public List getReceiveList()
  {
    return this.receiveList;
  }
  
  public void setReceiveList(List receiveList)
  {
    this.receiveList = receiveList;
  }
  
  public List getSendList()
  {
    return this.sendList;
  }
  
  public void setSendList(List sendList)
  {
    this.sendList = sendList;
  }
  
  public List getDraftList()
  {
    return this.draftList;
  }
  
  public void setDraftList(List draftList)
  {
    this.draftList = draftList;
  }
  
  public List getWasteList()
  {
    return this.wasteList;
  }
  
  public void setWasteList(List wasteList)
  {
    this.wasteList = wasteList;
  }
  
  public List getOtherFileList()
  {
    return this.otherFileList;
  }
  
  public void setOtherFileList(List otherFileList)
  {
    this.otherFileList = otherFileList;
  }
  
  public List getReceiveAttachList()
  {
    return this.receiveAttachList;
  }
  
  public void setReceiveAttachList(List receiveAttachList)
  {
    this.receiveAttachList = receiveAttachList;
  }
  
  public List getDraftAttachList()
  {
    return this.draftAttachList;
  }
  
  public void setDraftAttachList(List draftAttachList)
  {
    this.draftAttachList = draftAttachList;
  }
  
  public List getSendAttachList()
  {
    return this.sendAttachList;
  }
  
  public void setSendAttachList(List sendAttachList)
  {
    this.sendAttachList = sendAttachList;
  }
  
  public List getWasteAttachList()
  {
    return this.wasteAttachList;
  }
  
  public void setWasteAttachList(List wasteAttachList)
  {
    this.wasteAttachList = wasteAttachList;
  }
  
  public List getOtherFileAttachList()
  {
    return this.otherFileAttachList;
  }
  
  public void setOtherFileAttachList(List otherFileAttachList)
  {
    this.otherFileAttachList = otherFileAttachList;
  }
  
  public long getTotalFileSize()
  {
    long total = 0L;
    int i = 0;
    for (int len = this.receiveAttachList.size(); i < len; i++)
    {
      MessageFile obj = (MessageFile)this.receiveAttachList.get(i);
      total += obj.getFilesize();
    }
    int j = 0;
    for (int len = this.sendAttachList.size(); j < len; j++)
    {
      MessageFile obj = (MessageFile)this.sendAttachList.get(j);
      total += obj.getFilesize();
    }
    int k = 0;
    for (int len = this.draftAttachList.size(); k < len; k++)
    {
      MessageFile obj = (MessageFile)this.draftAttachList.get(k);
      total += obj.getFilesize();
    }
    int l = 0;
    for (int len = this.wasteAttachList.size(); l < len; l++)
    {
      MessageFile obj = (MessageFile)this.wasteAttachList.get(l);
      total += obj.getFilesize();
    }
    int m = 0;
    for (int len = this.otherFileAttachList.size(); m < len; m++)
    {
      MessageFile obj = (MessageFile)this.otherFileAttachList.get(m);
      total += obj.getFilesize();
    }
    return total;
  }
  
  public boolean isEmpty()
  {
    boolean isEmpty = false;
    
    isEmpty = this.receiveList.size() + this.receiveAttachList.size() + 
      this.sendList.size() + this.sendAttachList.size() + 
      this.draftList.size() + this.draftAttachList.size() + 
      this.wasteList.size() + this.wasteAttachList.size() == 0;
    
    return isEmpty;
  }
}


/* Location:              D:\tools\developt\jar反编译工具\Applet.jar!\com\snxoa\email\applet\DownloadTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */