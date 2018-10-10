
package enixlin.jmrc.mail.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import enixlin.jmrc.mail.entity.User;
import enixlin.jmrc.mail.service.NetService;
import enixlin.jmrc.mail.service.UserService;

/**
 * 新邮件系统客户端的登录界面 这是整个应用的启动点，使用swing编写 功能清单<br>
 * 登录界面会完成以下工作：<br>
 * 1.收集登录用户的用户名和密码<br>
 * 2.密码用md5加密<br>
 * 3.提交登录请求到服务器<br>
 * 4.生成登录的session<br>
 * 5.取得整个邮件系统的所有用户信息，以树型结构返回<br>
 * 
 * @author enixlin
 * @version 2.0
 * @date 2018-8-16
 *
 */
public class Login {

    private JFrame     frame;
    private JTextField tf_loginName;
    private JTextField tf_password;
    private NetService netService;
    private User       user;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    Login window = new Login();
		    window.frame.setVisible(true);

		    UIManager um = new UIManager();

		    try {
			um.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
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

		} catch (Exception e) {
		    e.printStackTrace();

		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public Login() {
	this.netService = new NetService();
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

	frame = new JFrame();
	frame.setBounds(100, 100, 450, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);

	JLabel lblNewLabel = new JLabel("用户");
	lblNewLabel.setBounds(15, 37, 81, 21);
	frame.getContentPane().add(lblNewLabel);

	JLabel lblNewLabel_1 = new JLabel("密码");
	lblNewLabel_1.setBounds(15, 93, 81, 21);
	frame.getContentPane().add(lblNewLabel_1);

	// 用户名
	tf_loginName = new JTextField();
	tf_loginName.setText("linzhenhuan");
	tf_loginName.setBounds(127, 34, 224, 27);
	frame.getContentPane().add(tf_loginName);
	tf_loginName.setColumns(10);
	// 用户密码
	tf_password = new JPasswordField();
	tf_password.setText("enixlin1981");
	tf_password.setColumns(10);
	tf_password.setBounds(127, 90, 224, 27);
	frame.getContentPane().add(tf_password);

	// 登录按钮
	JButton btnNewButton = new JButton("登录");
	// 处理登录按钮事件
	btnNewButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		String name = tf_loginName.getText();
		String password = tf_password.getText();

		// 显示主功能界面，并且将登录窗口隐藏
		UserService us = new UserService(netService);

		// User user=us.Login(name, password);
		if (us.Login("linzhenhuan", "enixlin1981")) {
		    showMainWindow(netService);
		    frame.setVisible(false);
		}

		// showMainWindow(netService);
		// frame.setVisible(false);

	    }
	});
	btnNewButton.setBounds(127, 132, 224, 63);
	frame.getContentPane().add(btnNewButton);
    }

    /**
     * 显示主功能界面<br>
     * 由于主功能界面都需要使用网络请求，所以将http客户端传过来使用
     * 
     * @param httpClients
     */
    public void showMainWindow(NetService netService) {
	System.out.println("shwo main window");
	user = new User();
	user.setId("1242546");
	user.setName("林振焕");
	MainWindow mw = new MainWindow(netService, user);
	mw.frame.setVisible(true);
    }

    // 用md5加密用户密码,旧的邮件系统需要将密码加密后发送到服务器，但新版的反而不需要，真是奇怪？？
    public String encryptByMD5(String input) {
	String output = null;
	ScriptEngineManager m = new ScriptEngineManager();
	// 获取JavaScript执行引擎
	ScriptEngine engine = m.getEngineByName("JavaScript");
	// js文件的路径
	String strFile = this.getClass().getResource("").getPath() + "md5.js";
	System.out.println(strFile);

	try {
	    // 执行JavaScript代码
	    Reader reader = new FileReader(new File(strFile));
	    engine.eval(reader);
	    if (engine instanceof Invocable) {
		Invocable invoke = (Invocable) engine;
		// 调用add方法，并传入两个参数
		int c = (int) invoke.invokeFunction("add", 2, 5);
		System.out.println("c = " + c);
	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return output;

    }
}
