
/**
 * 
 */
package enixlin.jmrc.mail.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.http.impl.client.CloseableHttpClient;

import com.snxoa.email.applet.DownloadApplet;
import com.snxoa.email.applet.DownloadTask;

import enixlin.jmrc.mail.entity.User;
import enixlin.jmrc.mail.service.NetService;
import enixlin.jmrc.mail.service.UserService;

/**
 * 
 * @author linzhenhuan
 *
 */
public class MainWindow {

    public JFrame		   frame;
    private CloseableHttpClient	   httpClient;
    private NetService		   netService;

    private User		   currentUser;	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 	  // 选择的用户
    private DefaultMutableTreeNode totalTreeNode; // 存量机构树
    private DefaultMutableTreeNode addTreeNode;	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 	    // 要新插入的节点
    private JTabbedPane		   tabbedPane;
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    private UserService		   userService;
    private JTextField		   textField_1;
    private JTextField		   textField_2;
    private JTextField		   textField;
    private JTable		   table;
    private DownloadTask	   dt;
    private DownloadApplet	   da;
    private User		   user;
    private DefaultMutableTreeNode root;

    /**
     * 
     */
    public MainWindow(NetService netService, User user) {
	this.user = user;
	initialize(netService);
	UIManager um = new UIManager();

	try {
	    um.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (UnsupportedLookAndFeelException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private void initialize(NetService netService) {

	// 主容器
	frame = new JFrame();
	frame.setFont(new Font("微软雅黑", Font.PLAIN, 18));
	frame.setBounds(200, 200, 1400, 700);
	frame.getContentPane().setLayout(null);
	frame.setTitle("新OA客户端");

	userService = new UserService(netService);
	ArrayList<User> userList = userService.getUserList("100327");
	root = userService.makeUserTree(userList);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(15, 50, 170, 590);
	frame.getContentPane().add(scrollPane);

	// DefaultMutableTreeNode root=
	// userService.makeUserTree(userService.makeUserList(content));

	// 用户树型选择控件
	JTree tree = new JTree(root);
	scrollPane.setViewportView(tree);
	tree.setFont(new Font("微软雅黑", Font.PLAIN, 18));
	tree.setAutoscrolls(true);
	tree.addTreeSelectionListener(new TreeSelectionListener() {
	    @Override
	    public void valueChanged(TreeSelectionEvent e) {
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
		    @Override
		    protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			user = (User) node.getUserObject();
			System.out.println("leaf=.............");
			System.out.println(user.getId());
			System.out.println(user.getName());
			System.out.println(user.getParentId());
			System.out.println(user.getMobileno());

			if (user.getIsLeaf().equals("\"n\"")) {
			    System.out.println("done.............");
			    userService.updateUserList(user.getId().replace("\"", ""), root);
			} else {
			    if (tabbedPane.getTabCount() == 0) {
		
				tabbedPane.addTab("邮件管理", initListBox(user, netService));
				//tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(jp_content), jp_tab);
			    } else {
				tabbedPane.setComponentAt(0, initListBox(user, netService));
				tabbedPane.setTitleAt(0, "邮件管理");
			    }

			}

			return null;
		    }

		    @Override
		    protected void done() {
			// TODO Auto-generated method stub
			super.done();
			tree.repaint(1);
		    }
		};
		sw.execute();

	    }
	});

	tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	tabbedPane.setBounds(200, 10, 1180, 630);

	frame.getContentPane().add(tabbedPane);

	JLabel label_2 = new JLabel("用户选择");
	label_2.setHorizontalAlignment(SwingConstants.CENTER);
	label_2.setAlignmentX(Component.CENTER_ALIGNMENT);
	label_2.setBounds(15, 13, 170, 21);
	frame.getContentPane().add(label_2);

	System.out.println(root);

    }

    /**
     * 初始化邮件列表
     * 
     * @return
     */

    public JPanel initListBox(User user, NetService netService) {

	return (JPanel) new MailListPanel(user, netService,this).getMainContainer();

    }
	/**
	 * 添加TAB控件的标签卡
	 * 
	 * @return
	 */
	protected static JPanel CreateTabLabel(String title) {

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
//				tabList.remove(tabbedPane.indexOfTabComponent(jp) - 1);
//				tabbedPane.remove(tabbedPane.indexOfTabComponent(jp));
			
			}

			
		});
		jp.add(jl_title);
		jp.add(jl_close);

		return jp;

	}

}
