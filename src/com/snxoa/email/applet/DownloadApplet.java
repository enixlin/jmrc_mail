package com.snxoa.email.applet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class DownloadApplet
  extends JApplet
  implements ActionListener, ProgressListener
{
  private boolean isClear = false;
  private static final long serialVersionUID = 1L;
  private String serverAddress = null;
  private JPanel pane = null;
  private JScrollPane scrolling = null;
  private JTextPane infoBox = null;
  private JTextField directoryField = null;
  private DownloadFeedback feedback = null;
  private JButton butLoad = null;
  private JButton butChoose = null;
  private JButton butSearch = null;
  private JFileChooser fileChooser;
  private JLabel tipsLabel = null;
  private JLabel direLabel = null;
  private DownloadTask task = null;
  private JProgressBar progressBar = new JProgressBar();
  private JProgressBar currentProgressBar = new JProgressBar();
  private JCheckBox jCheckBox = null;
  private JLabel totalLabel = null;
  private JLabel currentLabel = null;
  private String strUserId = "";
  private boolean history = false;
  StringBuffer faultInfo = new StringBuffer("下列邮件转移失败,请重新转移或者手工备份:\n");
  
  public DownloadApplet() {
      
      this.serverAddress="http://96.8.8.88:80/COA";
      //this.init();
  }
  
  
  public DownloadTask getTask() {
      return task;
  }
  @Override
public void init()
  {
    try
    {
      this.serverAddress = getParameter("serverAddress");
      
      resize(670, 325);
      if (this.serverAddress == null) {
        this.serverAddress = "http://localhost:8080/default";
      }
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private void jbInit()
    throws Exception
  {
    this.pane = new JPanel();
    this.pane.setBounds(new Rectangle(0, 0, 670, 325));
    this.pane.setLayout(null);
    this.pane.setBorder(BorderFactory.createEtchedBorder(1));
    
    this.pane.setBackground(Color.WHITE);
    
    this.direLabel = new JLabel("保存到:");
    this.direLabel.setBounds(new Rectangle(15, 10, 295, 35));
    this.pane.add(this.direLabel);
    
    this.directoryField = new JTextField();
    
    this.directoryField.setBounds(new Rectangle(65, 10, 425, 35));
    this.pane.add(this.directoryField);
    
    this.butChoose = new JButton();
    this.butChoose.setBounds(new Rectangle(500, 10, 100, 35));
    this.butChoose.setText("选择目录");
    this.butChoose.addActionListener(this);
    this.pane.add(this.butChoose);
    
    this.jCheckBox = new JCheckBox();
    this.jCheckBox.setBounds(new Rectangle(15, 50, 220, 35));
    this.jCheckBox.setText("下载完成后删除服务器端的邮件");
    this.jCheckBox.setBackground(Color.WHITE);
    this.pane.add(this.jCheckBox);
    
    this.butLoad = new JButton();
    this.butLoad.setBounds(new Rectangle(500, 60, 100, 35));
    this.butLoad.setText("下  载");
    this.butLoad.addActionListener(this);
    this.pane.add(this.butLoad);
    
    this.tipsLabel = new JLabel("下载任务统计:");
    this.tipsLabel.setBounds(new Rectangle(15, 75, 295, 35));
    this.pane.add(this.tipsLabel);
    
    this.infoBox = new JTextPane();
    this.infoBox.setText("");
    this.infoBox.setEditable(false);
    this.scrolling = new JScrollPane(this.infoBox);
    this.scrolling.setBounds(new Rectangle(15, 105, 580, 120));
    this.pane.add(this.scrolling);
    
    this.totalLabel = new JLabel("全部进度");
    this.totalLabel.setBounds(new Rectangle(15, 235, 55, 25));
    this.pane.add(this.totalLabel);
    
    this.progressBar.setBounds(new Rectangle(70, 235, 525, 22));
    
    this.progressBar.setStringPainted(true);
    this.progressBar.setMinimum(0);
    this.pane.add(this.progressBar);
    
    this.currentLabel = new JLabel("当前进度");
    this.currentLabel.setBounds(new Rectangle(15, 265, 55, 25));
    this.pane.add(this.currentLabel);
    
    this.currentProgressBar.setBounds(new Rectangle(70, 265, 525, 22));
    
    this.currentProgressBar.setStringPainted(true);
    this.currentProgressBar.setMinimum(0);
    this.pane.add(this.currentProgressBar);
    
    this.fileChooser = new JFileChooser("d:\\");
    this.fileChooser.setFileSelectionMode(1);
    this.fileChooser.setFont(new Font("宋体", 0, 12));
    this.fileChooser.setDialogTitle("选择邮件保存目录");
    
    setContentPane(this.pane);
  }
  
  public void getDownLoadList(String startDate, String endDate, String userId, boolean history)
  {
    try
    {
      //this.infoBox.setText("请稍候...");
      HttpGet http = new HttpGet();
      this.history = history;
      String url = this.serverAddress + "/messageDownload?method=getDownList&startDate=" + startDate + "&endDate=" + endDate + "&userId=" + userId + 
        "&history=" + history;
      this.task = http.getDownloadTask(url);
   
    }
    catch (Exception err)
    {
      err.printStackTrace();
      System.out.println("getDownLoadList error:" + err);
    }
  }
  
  @Override
public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == this.butLoad)
    {
      if ((this.task == null) || (this.task.isEmpty()))
      {
        JOptionPane.showMessageDialog(this.pane, "下载列表为空");
        return;
      }
      if ("".equals(this.directoryField.getText().trim()))
      {
        JOptionPane.showMessageDialog(this.pane, "请指定邮件保存目录!");
        return;
      }
      if (!DiskTool.checkPathExists(this.directoryField.getText()))
      {
        JOptionPane.showMessageDialog(this.pane, "邮件保存目录不存在!");
        return;
      }
      long emailSpace = this.task.getTotalFileSize();
      
      System.out.println("邮件附件总大小：emailSpace:" + emailSpace);
      
      long dirSpace = DiskTool.getFreeDiskSpace(this.directoryField.getText());
      
      System.out.println("用户可用空间：dirSpace:" + dirSpace);
      
      long restSpace = dirSpace - emailSpace;
      if (dirSpace == -1L)
      {
        int value = JOptionPane.showConfirmDialog(this.pane, "检查磁盘空间失败,请确保磁盘空间足够!", "提示", 0);
        if (value != 1) {}
      }
      else if (restSpace < 0L)
      {
        JOptionPane.showMessageDialog(this.pane, "所选目录空间不足,请重新选择!");
        return;
      }
      if ((dirSpace != -1L) && (this.directoryField.getText().startsWith("C")) && (restSpace < 209715200L))
      {
        JOptionPane.showMessageDialog(this.pane, "下载完成后C盘空间小于200MB,请重新选择!");
        return;
      }
      if (this.jCheckBox.isSelected()) {
        JOptionPane.showMessageDialog(this.pane, "邮件备份到本地后,服务端的对应邮件将被清除,请到本地文件浏览.");
      }
      this.butLoad.setEnabled(false);
      
      doDownload();
    }
    else if (e.getSource() == this.butChoose)
    {
      this.fileChooser.showSaveDialog(this);
      if (this.fileChooser.getSelectedFile() != null) {
        this.directoryField.setText(this.fileChooser.getSelectedFile().getPath());
      }
    }
    else if (e.getSource() == this.butSearch)
    {
      getDownLoadList("2017-06-26", "2018-06-30", "1203289", true);
    }
  }
  
  private void displayDownInfo()
  {
    StringBuffer info = new StringBuffer("");
    
    int emailCout = this.task.getReceiveList().size() + this.task.getDraftList().size() + this.task.getSendList().size() + this.task.getWasteList().size() + 
      this.task.getOtherFileList().size();
    
    int attachCount = this.task.getReceiveAttachList().size() + this.task.getDraftAttachList().size() + this.task.getSendAttachList().size() + 
      this.task.getWasteAttachList().size() + this.task.getOtherFileAttachList().size();
    
    info.append("收件箱[").append(this.task.getReceiveList().size()).append("]附件[").append(this.task.getReceiveAttachList().size()).append("]\n").append("发件箱[")
      .append(this.task.getSendList().size()).append("]附件[").append(this.task.getSendAttachList().size()).append("]\n").append("草稿箱[")
      .append(this.task.getDraftList().size()).append("]附件[").append(this.task.getDraftAttachList().size()).append("]\n").append("废件箱[")
      .append(this.task.getWasteList().size()).append("]附件[").append(this.task.getWasteAttachList().size()).append("]\n").append("其它文件夹[")
      .append(this.task.getOtherFileList().size()).append("]附件[").append(this.task.getOtherFileAttachList().size()).append("]\n").append("共计邮件[")
      .append(emailCout).append("]封,附件[").append(attachCount).append("]个.\n");
    
    info.append("需要磁盘空间:[").append(String.format("%.4f", new Object[] { Double.valueOf(this.task.getTotalFileSize() / 1048576.0D) })).append("]MB");
    
//    this.infoBox.setText(info.toString());
  }
  
  private void doDownload()
  {
    HttpGet instance = new HttpGet();
    
    Long mailSize = new Long(4096L);
    
    this.feedback = new DownloadFeedback();
    
    String filePath = DiskTool.createFolder(this.directoryField.getText() + "/收件箱");
    List temp = this.task.getReceiveList();
    List temp2 = this.task.getReceiveAttachList();
    setInstance(temp, temp2, instance, filePath, mailSize, "receive");
    
    filePath = DiskTool.createFolder(this.directoryField.getText() + "/发件箱");
    temp = this.task.getSendList();
    temp2 = this.task.getSendAttachList();
    setInstance(temp, temp2, instance, filePath, mailSize, "send");
    
    filePath = DiskTool.createFolder(this.directoryField.getText() + "/草稿箱");
    temp = this.task.getDraftList();
    temp2 = this.task.getDraftAttachList();
    setInstance(temp, temp2, instance, filePath, mailSize, "draft");
    
    filePath = DiskTool.createFolder(this.directoryField.getText() + "/废件箱");
    temp = this.task.getWasteList();
    temp2 = this.task.getWasteAttachList();
    setInstance(temp, temp2, instance, filePath, mailSize, "waste");
    
    filePath = DiskTool.createFolder(this.directoryField.getText() + "/其它文件夹");
    temp = this.task.getOtherFileList();
    temp2 = this.task.getOtherFileAttachList();
    setInstance(temp, temp2, instance, filePath, mailSize, "other");
    
    instance.downLoadByList(this);
  }
  
  public void setInstance(List temp, List temp2, HttpGet instance, String filePath, Long mailSize, String type)
  {
    int i = 0;
    for (int le = temp.size(); i < le; i++)
    {
      MessageFile dob = (MessageFile)temp.get(i);
      Long mid = Long.valueOf(dob.getMessageId());
      Long tid = Long.valueOf(dob.getTid());
      String senderName = dob.getSenderName();
      String sendDate = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss").format(dob.getSendDate());
      String url = this.serverAddress + "/messageDownload?method=getContent&mid=" + mid + "&tid=" + tid + "&history=" + this.history;
      
      String title = dob.getTitle();
      
      List attachIds = new ArrayList();
      long attachSize = 0L;
      int j = 0;
      for (int len = temp2.size(); j < len; j++)
      {
        MessageFile obj2 = (MessageFile)temp2.get(j);
        Long messageId = Long.valueOf(obj2.getMessageId());
        Long fileId = Long.valueOf(obj2.getFileId());
        
        String attachName = obj2.getFilename();
        attachName = "[" + senderName + " " + sendDate + "]" + attachName;
        String attachPath = obj2.getFilePath();
        Long aSize = Long.valueOf(obj2.getFilesize());
        if (mid.equals(messageId))
        {
          String attachUrl = this.serverAddress + "/fileDownOfficecom?processInsId=" + fileId + "&wftype=fj" + "@@" + 
            DiskTool.createFolder(new StringBuilder(String.valueOf(filePath)).append("/附件").toString()) + "/" + attachName;
          attachIds.add(attachUrl);
          attachSize += aSize.longValue();
        }
      }
      title = "[" + senderName + " " + sendDate + "]" + title;
      instance.addItem(url, filePath + "/" + title + ".html", new Long(mailSize.longValue() + attachSize), type, attachIds);
    }
  }
  
  @Override
public void deleteFeedBack(boolean isSuccess, String type, Long id, Long mid)
  {
    if ((isSuccess) && (this.jCheckBox.isSelected()))
    {
      if ("receive".equals(type)) {
        this.feedback.addReceive(id);
      }
      if ("send".equals(type)) {
        this.feedback.addSend(id);
      }
      if ("draft".equals(type)) {
        this.feedback.addDraft(id);
      }
      if ("waste".equals(type)) {
        this.feedback.addWaste(id);
      }
      if ("other".equals(type)) {
        this.feedback.addOther(id);
      }
      this.feedback.addMid(mid);
    }
  }
  
  private void displayFaultInfo()
  {
    this.infoBox.setText(this.faultInfo.toString());
    this.faultInfo = new StringBuffer("下列邮件转移失败,请重新转移或者手工备份:\n");
  }
  
  @Override
public boolean fault(String type, Long id, String message)
  {
    String msg = "邮件标题为: \"" + message + "\" 的邮件备份失败,是否继续备份其他邮件?";
    if (this.pane != null)
    {
      int value = JOptionPane.showConfirmDialog(this.pane, msg, "提示", 0);
      
      StringBuffer msgError = new StringBuffer("");
      if ("receive".equals(type)) {
        msgError.append("收件箱:");
      }
      if ("send".equals(type)) {
        msgError.append("发件箱:");
      }
      if ("draft".equals(type)) {
        msgError.append("草稿箱:");
      }
      if ("waste".equals(type)) {
        msgError.append("废件箱:");
      }
      if ("other".equals(type)) {
        msgError.append("其它文件夹:");
      }
      msgError.append("邮件ID(").append(id.longValue()).append("):邮件标题(").append(message).append(")\n");
      this.faultInfo.append(msgError);
      if (value == 1) {
        return false;
      }
    }
    return true;
  }
  
  @Override
public void showProgress(int current, int total)
  {
    this.progressBar.setMaximum(total);
    
    this.progressBar.setValue(current);
  }
  
  @Override
public void finish(boolean isSuccess)
  {
    this.task = null;
    String result = "success";
    if ((isSuccess) && (this.jCheckBox.isSelected()))
    {
      HttpGet instance = new HttpGet();
      result = instance.sendFeedBack(this.serverAddress + "/messageDownload?method=feedBack&userId=" + this.strUserId + "&history=" + this.history, this.feedback);
      this.isClear = true;
    }
    if ((isSuccess) && (this.progressBar.getValue() == this.progressBar.getMaximum()))
    {
      this.currentProgressBar.setValue(this.currentProgressBar.getMaximum());
      if ("success".equals(result)) {
        JOptionPane.showMessageDialog(this.pane, "邮件已成功转移到本地!");
      } else {
        JOptionPane.showMessageDialog(this.pane, "邮件已成功转移到本地!，但邮件删除失败！");
      }
    }
    else
    {
      JOptionPane.showMessageDialog(this.pane, "邮件备份部分成功,请重新备份!");
      displayFaultInfo();
    }
    this.butLoad.setEnabled(true);
  }
  
  public boolean isClear()
  {
    return this.isClear;
  }
  
  @Override
public void setCurrentProgress(int total)
  {
    this.currentProgressBar.setValue(0);
    this.currentProgressBar.setMaximum(total);
  }
  
  @Override
public void showCurrentProgress(int increaseSize)
  {
    this.currentProgressBar.setValue(this.currentProgressBar.getValue() + increaseSize);
  }
}


/* Location:              D:\tools\developt\jar反编译工具\Applet.jar!\com\snxoa\email\applet\DownloadApplet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */