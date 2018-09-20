package com.snxoa.email.applet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DownloadFeedback
  implements Serializable
{
  private static final long serialVersionUID = 5548425631895588265L;
  private List receiveList = new ArrayList();
  private List sendList = new ArrayList();
  private List draftList = new ArrayList();
  private List wasteList = new ArrayList();
  private List otherList = new ArrayList();
  private List midList = new ArrayList();
  
  public DownloadFeedback() {}
  
  public List getMidList()
  {
    return this.midList;
  }
  
  public void addMid(Long mid)
  {
    if (!this.midList.contains(mid)) {
      this.midList.add(mid);
    }
  }
  
  public void addReceive(Long tid)
  {
    if (!this.midList.contains(tid)) {
      this.receiveList.add(tid);
    }
  }
  
  public void addSend(Long tid)
  {
    if (!this.midList.contains(tid)) {
      this.sendList.add(tid);
    }
  }
  
  public void addDraft(Long tid)
  {
    if (!this.midList.contains(tid)) {
      this.draftList.add(tid);
    }
  }
  
  public void addWaste(Long tid)
  {
    if (!this.midList.contains(tid)) {
      this.wasteList.add(tid);
    }
  }
  
  public void addOther(Long tid)
  {
    if (!this.midList.contains(tid)) {
      this.otherList.add(tid);
    }
  }
  
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
  
  public List getOtherList()
  {
    return this.otherList;
  }
  
  public void setOtherList(List otherList)
  {
    this.otherList = otherList;
  }
  
  public void setMidList(List midList)
  {
    this.midList = midList;
  }
  
  public Long[] getReceiveIds()
  {
    Long[] result = new Long[this.receiveList.size()];
    for (int i = 0; i < this.receiveList.size(); i++) {
      result[i] = ((Long)this.receiveList.get(i));
    }
    return result;
  }
  
  public Long[] getSendIds()
  {
    Long[] result = new Long[this.sendList.size()];
    for (int i = 0; i < this.sendList.size(); i++) {
      result[i] = ((Long)this.sendList.get(i));
    }
    return result;
  }
  
  public Long[] getDraftIds()
  {
    Long[] result = new Long[this.draftList.size()];
    for (int i = 0; i < this.draftList.size(); i++) {
      result[i] = ((Long)this.draftList.get(i));
    }
    return result;
  }
  
  public Long[] getWasteIds()
  {
    Long[] result = new Long[this.wasteList.size()];
    for (int i = 0; i < this.wasteList.size(); i++) {
      result[i] = ((Long)this.wasteList.get(i));
    }
    return result;
  }
  
  public Long[] getOtherIds()
  {
    Long[] result = new Long[this.otherList.size()];
    for (int i = 0; i < this.otherList.size(); i++) {
      result[i] = ((Long)this.otherList.get(i));
    }
    return result;
  }
}


/* Location:              D:\tools\developt\jar反编译工具\Applet.jar!\com\snxoa\email\applet\DownloadFeedback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */