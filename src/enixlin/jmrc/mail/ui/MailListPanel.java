<<<<<<< HEAD
/**
 * 
 */
package enixlin.jmrc.mail.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.snxoa.email.applet.DownloadApplet;
import com.snxoa.email.applet.DownloadTask;
import com.snxoa.email.applet.MessageFile;

import enixlin.jmrc.mail.entity.User;
import enixlin.jmrc.mail.service.MailService;
import enixlin.jmrc.mail.service.NetService;
import enixlin.jmrc.mail.util.OrderNumberComparator;

import java.awt.Font;

/**
 * @author linzhenhuan
 *
 */
public class MailListPanel extends JPanel {

    private JPanel	      mainContainer;
    private DownloadTask      dt;
    private User	      user;
    private String	      listType;
    private String	      startDay;
    private String	      endDay;
    private DefaultTableModel dtm;
    private JTable	      mailListTable;
    private JComboBox<String> boxTypeComboBox;
    private JTextField	      tf_startDay;
    private JTextField	      tf_keyWord;
    private JTable	      table;
    private JLabel	      label_1;
    private JTextField	      tf_endDay;
    private JLabel	      label_2;
    private JLabel	      label_9;
    private JLabel	      label_11;
    private JLabel	      label_12;
    private JLabel	      label_13;
    private JLabel	      label_14;
    private JLabel	      label_15;
    private JLabel	      label_16;
    private JLabel	      label_17;
    private JLabel	      label_18;
    private JLabel	      label_20;
    private JLabel	      label_3;
    private JLabel	      lbl_userName;
    private DownloadApplet    da;
    private MailService	      mailService;
    private JScrollPane	      scrollPane;
    private MainWindow	      mainWindow;
    private ArrayList<String> tabList = new ArrayList<>();

    public MailService getMailService() {
	return mailService;
    }

    public void setMailService(MailService mailService) {
	this.mailService = mailService;
    }

    /**
     * @param netService
     * @param mainWindow
     * @wbp.parser.entryPoint
     */
    public MailListPanel(User user, NetService netService, MainWindow mainWindow) {
	this.user = user;
	this.mainWindow = mainWindow;
	init(user);
	startDay = tf_startDay.getText();
	endDay = tf_endDay.getText();
	this.mailService = new MailService(netService);
	tabList.add("邮件管理");

    }

    public void init(User user) {

	// 主容器，在主容器里面有一个工具栏和一个邮件列表
	mainContainer = new JPanel();
	mainContainer.setSize(new Dimension(1180, 630));
	mainContainer.setLayout(null);

	JComboBox cb_boxType = new JComboBox();
	cb_boxType.setBounds(374, 27, 87, 30);
	cb_boxType.setModel(new DefaultComboBoxModel(new String[] { "收件箱", "发件箱", "草稿箱", "废件箱" }));
	mainContainer.add(cb_boxType);

	label_1 = new JLabel("时间段");
	label_1.setHorizontalAlignment(SwingConstants.CENTER);
	label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
	label_1.setHorizontalTextPosition(SwingConstants.CENTER);
	label_1.setBounds(450, 27, 87, 30);
	mainContainer.add(label_1);

	tf_startDay = new JTextField();
	tf_startDay.setBounds(534, 27, 109, 30);
	tf_startDay.setColumns(10);
	mainContainer.add(tf_startDay);

	label_2 = new JLabel("至");
	label_2.setHorizontalAlignment(SwingConstants.CENTER);
	label_2.setBounds(657, 27, 29, 30);
	mainContainer.add(label_2);

	tf_endDay = new JTextField();
	tf_endDay.setBounds(686, 27, 109, 30);
	tf_endDay.setColumns(10);
	mainContainer.add(tf_endDay);

	JLabel label = new JLabel("关键字");
	label.setBounds(817, 27, 45, 30);
	mainContainer.add(label);

	tf_keyWord = new JTextField();
	tf_keyWord.setBounds(870, 27, 171, 30);
	tf_keyWord.setColumns(10);
	mainContainer.add(tf_keyWord);

	/**
	 * 这里是一个处理
	 */
	JButton btn_query = new JButton("查询");
	btn_query.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		System.out.println("show table1");
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

		    @Override
		    protected Void doInBackground() throws Exception {
			String userId = user.getId();
			mailService.setDa(new DownloadApplet());
			mailService.getDa().getDownLoadList(tf_startDay.getText(), tf_endDay.getText(),
				userId.replace("\"", ""), false);
			String boxType = cb_boxType.getSelectedItem().toString();
			listType = boxType;
			switch (boxType) {
			case "收件箱": {
			    DefaultTableModel dtm = mailService.ListToTableModel(mailService.getReciverList(),
				    listType);
//			    table.setcol
//				table.getColumnModel().getColumn(0).setPreferredWidth(60);
//				table.getColumnModel().getColumn(1).setPreferredWidth(560);
//				table.getColumnModel().getColumn(2).setPreferredWidth(60);
//				table.getColumnModel().getColumn(3).setPreferredWidth(150);
			    table.setModel(dtm);
			    // 对dtm排序
			    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dtm);
			   // sorter.setComparator(0, new OrderNumberComparator());
			    table.setRowSorter(sorter);
			    JTableHeader tableHeader = table.getTableHeader();
			    tableHeader.add(new JLabel("邮件编号"), 0);
			    tableHeader.add(new JLabel("标题"), 1);
			    tableHeader.add(new JLabel("发送人"), 2);
			    tableHeader.add(new JLabel("日期"), 3);
			    table.setTableHeader(tableHeader);
			    break;
			}
			case "发件箱": {
			    DefaultTableModel dtm = mailService.ListToTableModel(mailService.getSendList(), listType);
//				table.getColumnModel().getColumn(0).setPreferredWidth(60);
//				table.getColumnModel().getColumn(1).setPreferredWidth(560);
//				table.getColumnModel().getColumn(2).setPreferredWidth(60);
//				table.getColumnModel().getColumn(3).setPreferredWidth(150);
			    // 对dtm排序
			    table.setModel(dtm);
			    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dtm);
			    //sorter.setComparator(0, new OrderNumberComparator());
			    table.setRowSorter(sorter);
			    JTableHeader tableHeader = table.getTableHeader();
			    tableHeader.add(new JLabel("邮件编号"), 0);
			    tableHeader.add(new JLabel("标题"), 1);
			    tableHeader.add(new JLabel("发送人"), 2);
			    tableHeader.add(new JLabel("日期"), 3);
			    table.setTableHeader(tableHeader);
			    break;
			}
			}

			// System.out.println("show table2");
			return null;
		    }

		};
		sw.execute();

	    }
	});
	btn_query.setBounds(1056, 27, 87, 30);
	mainContainer.add(btn_query);

	scrollPane = new JScrollPane();
	scrollPane.setBounds(3, 70, 1172, 560);
	mainContainer.add(scrollPane);

	table = new JTable();
	table.setRowMargin(3);
	table.setAutoscrolls(true);
	table.setShowVerticalLines(false);
	table.setRowHeight(35);
	table.setFont(new Font("微软雅黑", Font.PLAIN, 18));

	 //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		int row = table.convertRowIndexToModel(table.getSelectedRow());
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		String title = (String) tm.getValueAt(row, 1);
		String MsgID = (String) String.valueOf(tm.getValueAt(row, 0));

		System.out.println(title);
		System.out.println(MsgID);
		String content = mailService.getMailContent((long) tm.getValueAt(row, 0));
		ArrayList<MessageFile> mal = mailService.getAttchBelongToMessage((Long) tm.getValueAt(row, 0),
			listType);
		// 添加新一个tab页，用来显示一封新的邮件
		// 先检测一下这个tab有没有被创建
		boolean tabIsExist = false;
		for (int i = 0; i < tabList.size(); i++) {
		    if (tabList.get(i).equals(title)) {
			tabIsExist = true;
		    }
		}

		if (tabIsExist) {
		    // 如果已经有的话，就显示已创建的tab
		    mainWindow.getTabbedPane()
			    .setSelectedComponent(mainWindow.getTabbedPane().getComponentAt(tabList.indexOf(title)));
		} else {
		    // 否则新创建一个新的tab
		    JEditorPane jp_content = new MailContentPanel(content, mal, mailService).getContentContainer();
		    JPanel jp_tablabel = CreateTabLabel(title);
		    mainWindow.getTabbedPane().add(jp_content);
		    mainWindow.getTabbedPane().addTab("", null, jp_content, title);
		    mainWindow.getTabbedPane()
			    .setTabComponentAt(mainWindow.getTabbedPane().indexOfComponent(jp_content), jp_tablabel);

		    System.out.println(content);
		}
	    }

	});
	table.setAutoCreateRowSorter(true);
	scrollPane.setViewportView(table);

	label_9 = new JLabel("");
	label_9.setBounds(90, 169, 87, 169);
	mainContainer.add(label_9);

	label_11 = new JLabel("");
	label_11.setBounds(264, 169, 87, 169);
	mainContainer.add(label_11);

	label_12 = new JLabel("");
	label_12.setBounds(351, 169, 87, 169);
	mainContainer.add(label_12);

	label_13 = new JLabel("");
	label_13.setBounds(438, 169, 87, 169);
	mainContainer.add(label_13);

	label_14 = new JLabel("");
	label_14.setBounds(525, 169, 87, 169);
	mainContainer.add(label_14);

	label_15 = new JLabel("");
	label_15.setBounds(612, 169, 87, 169);
	mainContainer.add(label_15);

	label_16 = new JLabel("");
	label_16.setBounds(699, 169, 87, 169);
	mainContainer.add(label_16);

	label_17 = new JLabel("");
	label_17.setBounds(3, 338, 87, 169);
	mainContainer.add(label_17);

	label_18 = new JLabel("");
	label_18.setBounds(90, 338, 87, 169);
	mainContainer.add(label_18);

	label_20 = new JLabel("");
	label_20.setBounds(264, 338, 87, 169);
	mainContainer.add(label_20);

	label_3 = new JLabel("当前用户：");
	label_3.setBounds(14, 33, 80, 18);
	mainContainer.add(label_3);

	lbl_userName = new JLabel("New label");
	lbl_userName.setBounds(108, 33, 72, 18);
	lbl_userName.setText(user.getName());
	mainContainer.add(lbl_userName);

	// 显示当前用户
	// lbl_userName.setText(user.getName());
	// 默认打开收件箱
	cb_boxType.setSelectedIndex(0);
	// 设定日段国当年的年头到本日
	// Calendar.set(Calendar.YEAR, year + 1900)

	Date dNow = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	String date = ft.format(dNow);
	String year = date.substring(0, 4);
	String month = date.substring(4, 5);
	String day = date.substring(6, 7);

	tf_startDay.setText(year + "-01" + "-01");
	tf_endDay.setText(date);

    }

    public Component getMainContainer() {
	return this.mainContainer;
    }

    /**
     * 添加TAB控件的标签卡
     * 
     * @return
     */
    public JPanel CreateTabLabel(String title) {

	tabList.add(title);
	final JPanel jp = new JPanel();
	// 以邮件的名称做标题
	String tab_sub_title = "";

	if (title.length() > 10) {
	    tab_sub_title = title.substring(0, 10) + "...";
	} else {
	    tab_sub_title = title;
	}
	JLabel jl_title = new JLabel(tab_sub_title);
	jl_title.setBackground(null);
	final JLabel jl_close = new JLabel(new ImageIcon("res/white_close.png"));
	jl_close.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		jl_close.setIcon(new ImageIcon("res/white_close.png"));
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		jl_close.setIcon(new ImageIcon("res/black_close.png"));

	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		tabList.remove(mainWindow.getTabbedPane().indexOfTabComponent(jp));
		mainWindow.getTabbedPane().remove(mainWindow.getTabbedPane().indexOfTabComponent(jp));

	    }
	});
	jp.add(jl_title);
	jp.add(jl_close);

	return jp;

    }

}
=======
/**
 * 
 */
package enixlin.jmrc.mail.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.snxoa.email.applet.DownloadApplet;
import com.snxoa.email.applet.DownloadTask;
import com.snxoa.email.applet.MessageFile;

import enixlin.jmrc.mail.entity.User;
import enixlin.jmrc.mail.service.MailService;
import enixlin.jmrc.mail.service.NetService;
import java.awt.Font;

/**
 * @author linzhenhuan
 *
 */
public class MailListPanel extends JPanel {

    private JPanel	      mainContainer;
    private DownloadTask      dt;
    private User	      user;
    private String	      listType;
    private String	      startDay;
    private String	      endDay;
    private DefaultTableModel dtm;
    private JTable	      mailListTable;
    private JComboBox<String> boxTypeComboBox;
    private JTextField	      tf_startDay;
    private JTextField	      tf_keyWord;
    private JTable	      table;
    private JLabel	      label_1;
    private JTextField	      tf_endDay;
    private JLabel	      label_2;
    private JLabel	      label_9;
    private JLabel	      label_11;
    private JLabel	      label_12;
    private JLabel	      label_13;
    private JLabel	      label_14;
    private JLabel	      label_15;
    private JLabel	      label_16;
    private JLabel	      label_17;
    private JLabel	      label_18;
    private JLabel	      label_20;
    private JLabel	      label_3;
    private JLabel	      lbl_userName;
    private DownloadApplet    da;
    private MailService	      mailService;
    private JScrollPane	      scrollPane;
    private MainWindow	      mainWindow;
    private ArrayList<String> tabList = new ArrayList<>();

    public MailService getMailService() {
	return mailService;
    }

    public void setMailService(MailService mailService) {
	this.mailService = mailService;
    }

    /**
     * @param netService
     * @param mainWindow
     * @wbp.parser.entryPoint
     */
    public MailListPanel(User user, NetService netService, MainWindow mainWindow) {
	this.user = user;
	this.mainWindow = mainWindow;
	init(user);
	startDay = tf_startDay.getText();
	endDay = tf_endDay.getText();
	this.mailService = new MailService(netService);
	tabList.add("邮件管理");

    }

    public void init(User user) {
	
	// 主容器，在主容器里面有一个工具栏和一个邮件列表
	mainContainer = new JPanel();
	mainContainer.setSize(new Dimension(1180, 630));
	mainContainer.setLayout(null);

	JComboBox cb_boxType = new JComboBox();
	cb_boxType.setBounds(374, 27, 87, 30);
	cb_boxType.setModel(new DefaultComboBoxModel(new String[] { "收件箱", "发件箱", "草稿箱", "废件箱" }));
	mainContainer.add(cb_boxType);

	label_1 = new JLabel("时间段");
	label_1.setHorizontalAlignment(SwingConstants.CENTER);
	label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
	label_1.setHorizontalTextPosition(SwingConstants.CENTER);
	label_1.setBounds(450, 27, 87, 30);
	mainContainer.add(label_1);

	tf_startDay = new JTextField();
	tf_startDay.setBounds(534, 27, 109, 30);
	tf_startDay.setColumns(10);
	mainContainer.add(tf_startDay);

	label_2 = new JLabel("至");
	label_2.setHorizontalAlignment(SwingConstants.CENTER);
	label_2.setBounds(657, 27, 29, 30);
	mainContainer.add(label_2);

	tf_endDay = new JTextField();
	tf_endDay.setBounds(686, 27, 109, 30);
	tf_endDay.setColumns(10);
	mainContainer.add(tf_endDay);

	JLabel label = new JLabel("关键字");
	label.setBounds(817, 27, 45, 30);
	mainContainer.add(label);

	tf_keyWord = new JTextField();
	tf_keyWord.setBounds(870, 27, 171, 30);
	tf_keyWord.setColumns(10);
	mainContainer.add(tf_keyWord);

	/**
	 * 这里是一个处理
	 */
	JButton btn_query = new JButton("查询");
	btn_query.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		System.out.println("show table1");
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

		    @Override
		    protected Void doInBackground() throws Exception {
			String userId = user.getId();
			mailService.setDa(new DownloadApplet());
			mailService.getDa().getDownLoadList(tf_startDay.getText(), tf_endDay.getText(),
				userId.replace("\"", ""), false);
			String boxType = cb_boxType.getSelectedItem().toString();
			listType = boxType;
			switch (boxType) {
			case "收件箱": {
			    DefaultTableModel dtm = mailService.ListToTableModel(mailService.getReciverList(),
				    listType);
			    JTableHeader tableHeader = table.getTableHeader();
			    tableHeader.add(new JLabel("邮件编号"), 0);
			    tableHeader.add(new JLabel("标题"), 1);
			    tableHeader.add(new JLabel("发送人"), 2);
			    tableHeader.add(new JLabel("日期"), 3);
			    table.setTableHeader(tableHeader);
			    table.setModel(dtm);
			    break;
			}
			case "发件箱": {
			    DefaultTableModel dtm = mailService.ListToTableModel(mailService.getSendList(), listType);
			    JTableHeader tableHeader = table.getTableHeader();
			    tableHeader.add(new JLabel("邮件编号"), 0);
			    tableHeader.add(new JLabel("标题"), 1);
			    tableHeader.add(new JLabel("发送人"), 2);
			    tableHeader.add(new JLabel("日期"), 3);
			    table.setTableHeader(tableHeader);
			    table.setModel(dtm);
			    break;
			}
			}

			// System.out.println("show table2");
			return null;
		    }

		};
		sw.execute();

	    }
	});
	btn_query.setBounds(1056, 27, 87, 30);
	mainContainer.add(btn_query);

	scrollPane = new JScrollPane();
	scrollPane.setBounds(3, 70, 1172, 560);
	mainContainer.add(scrollPane);

	table = new JTable();
	table.setRowMargin(3);
	table.setShowVerticalLines(false);
	table.setRowHeight(35);
	table.setFont(new Font("微软雅黑", Font.PLAIN, 18));
	// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		int row = table.convertRowIndexToModel(table.getSelectedRow());
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		String title = (String) tm.getValueAt(row, 1);
		String MsgID = (String) String.valueOf(tm.getValueAt(row, 0));

		System.out.println(title);
		System.out.println(MsgID);
		String content = mailService.getMailContent((long) tm.getValueAt(row, 0));
		ArrayList<MessageFile> mal = mailService.getAttchBelongToMessage((Long) tm.getValueAt(row, 0),
			listType);
		// 添加新一个tab页，用来显示一封新的邮件
		// 先检测一下这个tab有没有被创建
		boolean tabIsExist = false;
		for (int i = 0; i < tabList.size(); i++) {
		    if (tabList.get(i).equals(title)) {
			tabIsExist = true;
		    }
		}

		if (tabIsExist) {
		    // 如果已经有的话，就显示已创建的tab
		    mainWindow.getTabbedPane()
			    .setSelectedComponent(mainWindow.getTabbedPane().getComponentAt(tabList.indexOf(title)));
		} else {
		    //否则新创建一个新的tab
		    JEditorPane jp_content = new MailContentPanel(content, mal, mailService).getContentContainer();
		    JPanel jp_tablabel = CreateTabLabel(title);
		    mainWindow.getTabbedPane().add(jp_content);
		    mainWindow.getTabbedPane().addTab("", null, jp_content, title);
		    mainWindow.getTabbedPane()
			    .setTabComponentAt(mainWindow.getTabbedPane().indexOfComponent(jp_content), jp_tablabel);

		    System.out.println(content);
		}
	    }

	});
	table.setAutoCreateRowSorter(true);
	scrollPane.setViewportView(table);

	label_9 = new JLabel("");
	label_9.setBounds(90, 169, 87, 169);
	mainContainer.add(label_9);

	label_11 = new JLabel("");
	label_11.setBounds(264, 169, 87, 169);
	mainContainer.add(label_11);

	label_12 = new JLabel("");
	label_12.setBounds(351, 169, 87, 169);
	mainContainer.add(label_12);

	label_13 = new JLabel("");
	label_13.setBounds(438, 169, 87, 169);
	mainContainer.add(label_13);

	label_14 = new JLabel("");
	label_14.setBounds(525, 169, 87, 169);
	mainContainer.add(label_14);

	label_15 = new JLabel("");
	label_15.setBounds(612, 169, 87, 169);
	mainContainer.add(label_15);

	label_16 = new JLabel("");
	label_16.setBounds(699, 169, 87, 169);
	mainContainer.add(label_16);

	label_17 = new JLabel("");
	label_17.setBounds(3, 338, 87, 169);
	mainContainer.add(label_17);

	label_18 = new JLabel("");
	label_18.setBounds(90, 338, 87, 169);
	mainContainer.add(label_18);

	label_20 = new JLabel("");
	label_20.setBounds(264, 338, 87, 169);
	mainContainer.add(label_20);

	label_3 = new JLabel("当前用户：");
	label_3.setBounds(14, 33, 80, 18);
	mainContainer.add(label_3);

	lbl_userName = new JLabel("New label");
	lbl_userName.setBounds(108, 33, 72, 18);
	lbl_userName.setText(user.getName());
	mainContainer.add(lbl_userName);

	// 显示当前用户
	// lbl_userName.setText(user.getName());
	// 默认打开收件箱
	cb_boxType.setSelectedIndex(0);
	// 设定日段国当年的年头到本日
	// Calendar.set(Calendar.YEAR, year + 1900)

	Date dNow = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	String date = ft.format(dNow);
	String year = date.substring(0, 4);
	String month = date.substring(4, 5);
	String day = date.substring(6, 7);

	tf_startDay.setText(year + "-01" + "-01");
	tf_endDay.setText(date);

    }

    public Component getMainContainer() {
	return this.mainContainer;
    }

    /**
     * 添加TAB控件的标签卡
     * 
     * @return
     */
    public JPanel CreateTabLabel(String title) {

	tabList.add(title);
	final JPanel jp = new JPanel();
	// 以邮件的名称做标题
	String tab_sub_title = "";

	if (title.length() > 10) {
	    tab_sub_title = title.substring(0, 10) + "...";
	} else {
	    tab_sub_title = title;
	}
	JLabel jl_title = new JLabel(tab_sub_title);
	jl_title.setBackground(null);
	final JLabel jl_close = new JLabel(new ImageIcon("res/white_close.png"));
	jl_close.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		jl_close.setIcon(new ImageIcon("res/white_close.png"));
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		jl_close.setIcon(new ImageIcon("res/black_close.png"));

	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		tabList.remove(mainWindow.getTabbedPane().indexOfTabComponent(jp) );
		mainWindow.getTabbedPane().remove(mainWindow.getTabbedPane().indexOfTabComponent(jp));

	    }
	});
	jp.add(jl_title);
	jp.add(jl_close);

	return jp;

    }

}
>>>>>>> a05da3cbea95e88edb804f5d712c700cf95594e3
