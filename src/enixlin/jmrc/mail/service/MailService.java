<<<<<<< HEAD
/**
 * 
 */
package enixlin.jmrc.mail.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snxoa.email.applet.DownloadApplet;
import com.snxoa.email.applet.DownloadTask;
import com.snxoa.email.applet.MessageFile;

/**
 * 邮件服务实现类
 * 
 * @author enixlin
 *
 */
public class MailService {
    private NetService	   netService;
    private DownloadApplet da;
    private DownloadTask   dt;
    private String	   currentBoxType;

    /**
     * 构造函数 这里有一个网络服务的参数netService，主要的用途是因为要用cookie来保持登录状态
     * 在Login类中已登录验正的用户身份在邮件服务里要继续沿用，netService里有一个httpclient
     * 这个httpclient持有之前双难正的用户身份cookie
     */
    public MailService(NetService netService) {
	super();
	this.netService = netService;
	this.da = new DownloadApplet();

	// TODO Auto-generated constructor stub
    }

    public DownloadApplet getDa() {
	return this.da;
    }

    public void setDa(DownloadApplet da) {
	this.da = new DownloadApplet();
    }

    public DownloadTask getDt() {
	return this.da.getTask();
    }

    public void setDt(DownloadTask dt) {
	this.dt = dt;
    }

    /**
     * 取得邮件的正文内容，以String返回
     */
    public String getMailContent(long msgID) {
	String html = "";
	String spec = "http://96.8.8.88/COA/email/com.snxoa.email.mailComponent.queryDetailMessage.biz.ext?id="
		+ String.valueOf(msgID);
	try {
	    html = netService.HttpGet(spec);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return html;

    }

    /**
     * 转换邮箱的类型
     * 
     * @param typeName
     *            类型名称（收件箱，发件箱，草稿箱。。。）
     * @return
     */
    public String changeMailBoxType(String typeName) {

	return this.currentBoxType = typeName;
    }

    /**
     * 刷新邮件列表
     * 
     * @param boxTypeName
     * @return
     */ 
    public boolean refreshBoxList(String boxTypeName) {

	return false;

    }

    /**
     * 取得收件箱列表
     * 
     * @return ArrayList
     */
    public ArrayList<MessageFile> getReciverList() {
	ArrayList<MessageFile> recList = (ArrayList<MessageFile>) da.getTask().getReceiveList();
	return recList;
    }

    /**
     * 取得收件箱的附件列表
     * 
     * @return
     */
    public ArrayList<MessageFile> getReceiveAttchList() {
	ArrayList<MessageFile> attachList = (ArrayList<MessageFile>) da.getTask().getReceiveAttachList();
	return attachList;
    }

    /**
     * 取得发件箱的附件列表
     * 
     * @return
     */
    public ArrayList<MessageFile> getSendAttchList() {
	ArrayList<MessageFile> attachList = (ArrayList<MessageFile>) da.getTask().getSendAttachList();
	return attachList;
    }

    /**
     * 取得发件箱列表
     * 
     * @return
     */
    public ArrayList<MessageFile> getSendList() {
	ArrayList<MessageFile> sendList = (ArrayList<MessageFile>) da.getTask().getSendList();
	return sendList;
    }

    /**
     * 将邮件列表转化为表格的模型 模型就是一个数据填充表格要显示的数据内容
     * 
     * @param list
     * @param listType
     * @return
     */
    public DefaultTableModel ListToTableModel(ArrayList<MessageFile> list, String listType) {
	DefaultTableModel dm = new DefaultTableModel();
	try {
	    Object[][] data = new Object[list.size()][4];
	    for (int i = 0; i < list.size(); i++) {
		data[i][0] = list.get(i).getMessageId();
		data[i][1] = list.get(i).getTitle();
		if (listType.equals("收件箱")) {
		    data[i][2] = list.get(i).getSenderName();

		}
		if (listType.equals("发件箱")) {
		    JsonParser jp = new JsonParser();
		    JsonObject jo = (JsonObject) jp.parse(getMailContent(list.get(i).getMessageId()));
		    data[i][2] = ((JsonObject) jo.get("message")).get("receiverName");
		}

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		data[i][3] = sf.format(list.get(i).getSendDate());
	    }
	    Object[] title = new Object[4];
	    title[0] = "邮件编号";
	    title[1] = "标题";
	    title[2] = "发件人";
	    title[3] = "收件时间";
	    dm.setDataVector(data, title);
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
	// dm.setDataVector(list)
	return dm;

    }

    /**
     * 按邮件编号来获取该邮件下的所有附件
     * 
     * @param MsgId
     *            邮件编号
     * @param listType
     *            邮件列表类型（收件箱、发件箱等。。。）
     * @return
     */
    public ArrayList<MessageFile> getAttchBelongToMessage(Long MsgId, String listType) {
	ArrayList<MessageFile> al = new ArrayList<>();
	if (listType.equals("收件箱")) {
	    al = getReceiveAttchList();
	}
	if (listType.equals("发件箱")) {
	    al = getSendAttchList();
	}
	ArrayList<MessageFile> mal = new ArrayList<>();
	for (int i = 0; i < al.size(); i++) {
	    if (al.get(i).getMessageId() == MsgId) {
		mal.add(al.get(i));
	    }
	}
	return mal;

    }

    /**
     * 下载附件
     * 
     * @param mf
     *            附件实体对象，其实这个附件实体对象的数据结构和邮件实件的数据结构是一样的
     */
    public void downloadAttchFile(MessageFile mf) {
	FileOutputStream fos = null;
	BufferedInputStream bis = null;
	HttpURLConnection httpUrl = null;
	URL url = null;
	String fileName = mf.getFilename();
	Long id = mf.getMessageId();
	Long fileId = mf.getFileId();
	String filePath = mf.getFilePath();

	byte[] buf = new byte[100000];
	int size = 0;
	try {
	    //由于文件名中可能有空格，所以要做url处理，将空格转换为%20
	    String destUrl = "http://96.8.8.88/COA/filedowncom?id=" + id.toString() + "&fileId=" + fileId.toString()
		    + "&filename=" +  fileName.replace(" ", "%20") + "&filepath=" + filePath;

	    System.out.println(destUrl);
	    url = new URL(destUrl);
	    httpUrl = (HttpURLConnection) url.openConnection();

	    httpUrl.connect();
	    bis = new BufferedInputStream(httpUrl.getInputStream());
	    fos = new FileOutputStream("attachFile\\" + fileName);
	    while ((size = bis.read(buf)) != -1) {
		fos.write(buf, 0, size);
	    }
	    fos.close();
	    bis.close();
	    httpUrl.disconnect();

	    try {
		Thread.sleep(10L);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
=======
/**
 * 
 */
package enixlin.jmrc.mail.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snxoa.email.applet.DownloadApplet;
import com.snxoa.email.applet.DownloadTask;
import com.snxoa.email.applet.MessageFile;

/**
 * 邮件服务实现类
 * 
 * @author enixlin
 *
 */
public class MailService {
    private NetService	   netService;
    private DownloadApplet da;
    private DownloadTask   dt;
    private String	   currentBoxType;

    /**
     * 
     */
    public MailService(NetService netService) {
	super();
	this.netService = netService;
	this.da = new DownloadApplet();

	// TODO Auto-generated constructor stub
    }

    public DownloadApplet getDa() {
	return this.da;
    }

    public void setDa(DownloadApplet da) {
	this.da = new DownloadApplet();
    }

    public DownloadTask getDt() {
	return this.da.getTask();
    }

    public void setDt(DownloadTask dt) {
	this.dt = dt;
    }

    /**
     * 取得邮件的正文内容，以String返回
     */
    public String getMailContent(long msgID) {
	String html = "";
	String spec = "http://96.8.8.88/COA/email/com.snxoa.email.mailComponent.queryDetailMessage.biz.ext?id="
		+ String.valueOf(msgID);
	try {
	    html = netService.HttpGet(spec);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return html;

    }

    /**
     * 转换邮箱的类型
     * 
     * @param typeName
     *            类型名称（收件箱，发件箱，草稿箱。。。）
     * @return
     */
    public String changeMailBoxType(String typeName) {

	return this.currentBoxType = typeName;
    }

    /**
     * 刷新邮件列表
     * 
     * @param boxTypeName
     * @return
     */
    public boolean refreshBoxList(String boxTypeName) {

	return false;

    }

    /**
     * 获取邮件的附件
     * 
     * @param MsgID
     *            邮件编号
     * 
     * @return 附件编号列表
     */
    public ArrayList<Long> getMailAttch(long MsgID) {
	return null;

    }

    public Object[][] makeMailListData(long UserId, String startDay, String endDay) {
	return null;

    }

    /**
     * 取得收件箱列表
     * 
     * @return ArrayList
     */
    public ArrayList<MessageFile> getReciverList() {
	ArrayList<MessageFile> recList = (ArrayList<MessageFile>) da.getTask().getReceiveList();
	return recList;
    }

    public ArrayList<MessageFile> getReceiveAttchList() {
	ArrayList<MessageFile> attachList = (ArrayList<MessageFile>) da.getTask().getReceiveAttachList();
	return attachList;
    }
    
    public ArrayList<MessageFile> getSendAttchList() {
	ArrayList<MessageFile> attachList = (ArrayList<MessageFile>) da.getTask().getSendAttachList();
	return attachList;
    }
    
    public ArrayList<MessageFile> getSendList() {
	ArrayList<MessageFile> sendList = (ArrayList<MessageFile>) da.getTask().getSendList();
	return sendList;
    }
    
    


    public DefaultTableModel ListToTableModel(ArrayList<MessageFile> list,String listType) {
	DefaultTableModel dm = new DefaultTableModel();
	try {
	    Object[][] data = new Object[list.size()][4];
	    for (int i = 0; i < list.size(); i++) {
		data[i][0] = list.get(i).getMessageId();
		data[i][1] = list.get(i).getTitle();
		if(listType.equals("收件箱")) {
			data[i][2] = list.get(i).getSenderName();
		
		}
		if(listType.equals("发件箱")) {
			JsonParser jp=new JsonParser();
			JsonObject jo=(JsonObject) jp.parse(getMailContent(list.get(i).getMessageId()));
			data[i][2] = ((JsonObject) jo.get("message")).get("receiverName");
		}
//		if(listType.equals("收件箱")) {			
//			data[i][2] = list.get(i).getSenderName();
//		}
//		if(listType.equals("发件箱")) {			
//			data[i][2] = list.get(i).getSenderName();
//		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		data[i][3] = sf.format(list.get(i).getSendDate());
	    }
	    Object[] title = new Object[4];
	    title[0] = "邮件编号";
	    title[1] = "发件人";
	    title[2] = "标题";
	    title[3] = "收件时间";
	    dm.setDataVector(data, title);
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
	// dm.setDataVector(list)
	return dm;

    }

    public ArrayList<MessageFile> getAttchBelongToMessage(Long MsgId,String listType) {
    	ArrayList<MessageFile> al=new ArrayList<>();
	if(listType.equals("收件箱")) {
		al = getReceiveAttchList();
	}
	if(listType.equals("发件箱")) {
		 al = getSendAttchList();
	}
	ArrayList<MessageFile> mal = new ArrayList<>();
	for (int i = 0; i < al.size(); i++) {
	    if (al.get(i).getMessageId() == MsgId) {
		mal.add(al.get(i));
	    }
	}
	return mal;

    }
    
    

    public void downloadAttchFile(MessageFile mf) {
	FileOutputStream fos = null;
	BufferedInputStream bis = null;
	HttpURLConnection httpUrl = null;
	URL url = null;
	String fileName = mf.getFilename();
	Long id = mf.getMessageId();
	Long fileId = mf.getFileId();
	String filePath = mf.getFilePath();

	byte[] buf = new byte[100000];
	int size = 0;
	/*     */try
	/*     */ {
	    // 名称 协议 方法 结果 内容类型 已接收 时间 发起程序
	    /// http://96.8.8.88/COA/filedowncom?id=8005457&fileId=16709614&filename=%E9%99%84%E4%BB%B62%EF%BC%9A%E9%B2%9C%E7%89%B9%E6%B1%87%E6%94%B6%E9%93%B6%E5%8F%B0%E5%95%86%E6%88%B7%E5%8F%98%E6%9B%B4%E4%B8%8E%E6%92%A4%E9%94%80%E7%94%B3%E8%AF%B7%E8%A1%A8.doc&filepath=/home/coanfs/oamailattach3/email/2018/09/07/201809071201596329187224321.doc&user=1228076&wftype=m01&source=01&uploadChannel=undefined
	    // HTTP GET 200 application/vnd.ms-word 66.97 毫秒

	    /* 169 */String destUrl = "http://96.8.8.88/COA/filedowncom?id=" + id.toString() + "&fileId="
		    + fileId.toString() + "&filename=" + fileName + "&filepath=" + filePath;

	    System.out.println(destUrl);
	    url = new URL(destUrl);
	    /* 170 */httpUrl = (HttpURLConnection) url.openConnection();
	    /*     */
	    /* 172 */httpUrl.connect();
	    /*     */
	    /* 174 */bis = new BufferedInputStream(httpUrl.getInputStream());
	    /*     */
	    /* 176 */fos = new FileOutputStream("attachFile\\"+fileName);

	    /* 183 */while ((size = bis.read(buf)) != -1) {
		/* 184 */fos.write(buf, 0, size);
	    }
	    /* 188 */fos.close();
	    /* 189 */bis.close();
	    /* 190 */httpUrl.disconnect();

	    /* 192 */try {
		Thread.sleep(10L);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
>>>>>>> a05da3cbea95e88edb804f5d712c700cf95594e3
