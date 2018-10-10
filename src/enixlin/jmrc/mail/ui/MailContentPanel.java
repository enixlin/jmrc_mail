

package enixlin.jmrc.mail.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snxoa.email.applet.MessageFile;

import enixlin.jmrc.mail.service.MailService;
import javax.swing.border.LineBorder;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JButton;

/**
 * @author linzhenhuan
 *
 */
public class MailContentPanel extends JPanel {

    private JEditorPane	ContentContainer;
    private JTextField	textField;
    private JTextField	textField_1;
    private JTextField	textField_2;

    /**
     * @param mal
     * @param mailService
     * 
     */
    public MailContentPanel(String content, ArrayList<MessageFile> mal, MailService mailService) {
	// super();
	// setLayout(null);

	// TODO Auto-generated constructor stub
	init(content, mal, mailService);
    }

    void init(String content, ArrayList<MessageFile> mal, MailService mailService) {

	JsonParser jp = new JsonParser();
	JsonObject jo = (JsonObject) jp.parse(content);
	JsonObject message = (JsonObject) jo.get("message");

	// 主容器：用于显示邮件主体的详细内容
	ContentContainer = new JEditorPane();
	ContentContainer.setBorder(new LineBorder(new Color(0, 0, 0)));
	ContentContainer.setSize(new Dimension(1180, 630));
	ContentContainer.setLayout(null);

	JScrollPane scrollPane = new JScrollPane();

	scrollPane.setBounds(10, 130, 1160, 330);

	ContentContainer.add(scrollPane);
	JTextPane textPane = new JTextPane();
	textPane.setEditorKit(new HTMLEditorKit());
	scrollPane.setViewportView(textPane);
	textPane.setText(message.get("content").toString().replace("/", ""));

	JLabel label = new JLabel("邮件标题");
	label.setBounds(14, 24, 72, 18);
	ContentContainer.add(label);

	textField = new JTextField();
	textField.setBounds(100, 21, 950, 24);
	textField.setText(message.get("title").toString());
	ContentContainer.add(textField);
	textField.setColumns(10);

	JLabel label_1 = new JLabel("发件人");
	label_1.setBounds(14, 55, 72, 18);
	ContentContainer.add(label_1);

	textField_1 = new JTextField();
	textField_1.setColumns(10);
	textField_1.setText(message.get("senderName").toString());
	textField_1.setBounds(100, 58, 950, 24);
	ContentContainer.add(textField_1);

	DefaultListModel<String> model = new DefaultListModel<>();
	for (int i = 0; i < mal.size(); i++) {
	    model.addElement(mal.get(i).getFilename().toString());
	}

	JScrollPane scrollPane_1 = new JScrollPane();
	
	scrollPane_1.setBounds(10, 470, 1160, 110);
	ContentContainer.add(scrollPane_1);

	JList list = new JList();
	list.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 2) {
		    int index = list.getSelectedIndex();
		    MessageFile mf = mal.get(index);
		    mailService.downloadAttchFile(mf);
		    // 下载完成后即时打开文件
		    try {
			File dir = new File(".");
			String path = dir.getCanonicalPath();
			// System.out.println(dir.getCanonicalPath());
			Desktop.getDesktop().open(new File(path + "/attachFile/" + (String) mf.getFilename()));
		    } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }

		}
	    }
	});
	scrollPane_1.setViewportView(list);
	list.setBackground(Color.WHITE);
	list.setModel(model);

	JLabel label_2 = new JLabel("抄送人");
	label_2.setBounds(14, 99, 72, 18);
	ContentContainer.add(label_2);

	textField_2 = new JTextField();
	textField_2.setColumns(10);
	textField_2.setText(message.get("chaoRecnames").toString());
	textField_2.setBounds(100, 95, 950, 24);
	ContentContainer.add(textField_2);

    }

    public JEditorPane getContentContainer() {
	return ContentContainer;
    }

    public void setContentContainer(JEditorPane contentContainer) {
	ContentContainer = contentContainer;
    }
}