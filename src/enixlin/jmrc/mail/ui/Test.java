
/**
 * 
 */
package enixlin.jmrc.mail.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.awt.event.ActionEvent;

/**
 * @author enixl
 *
 */
public class Test {

    private JFrame     frame;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    Test window = new Test();
		    window.frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public Test() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frame = new JFrame();
	frame.setBounds(100, 100, 934, 668);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);

	textField = new JTextField();
	textField.setBounds(46, 59, 385, 27);
	frame.getContentPane().add(textField);
	textField.setColumns(10);

	JButton btnNewButton = new JButton("登录");
	btnNewButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {

	    }
	});
	btnNewButton.setBounds(446, 59, 123, 29);
	frame.getContentPane().add(btnNewButton);
	
	JButton btnAddNode = new JButton("add node");
	btnAddNode.setBounds(616, 58, 123, 29);
	frame.getContentPane().add(btnAddNode);
    }
    
    
    /**
     * 
     */
    public  void testJson() {
	
    }
    
}
