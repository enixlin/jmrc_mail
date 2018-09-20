<<<<<<< HEAD
/**
 * 
 */
package enixlin.jmrc.mail.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.http.impl.client.CloseableHttpClient;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import enixlin.jmrc.mail.entity.User;

/**
 * oa系统用户管理类<br>
 * 在新的oa系统中，用户管理分成了两种类型：组织（org）和雇员（emp）<br>
 * 这个类的用途是将上面两种类型的用户抽象成同一种类型就是User<br>
 * 
 * @author linzhenhuan
 *
 */
public class UserService {

    private NetService netService;

    /**
     * 
     */
    public UserService(NetService netService) {
	this.netService = netService;
	// TODO Auto-generated constructor stub
    }

    /**
     * 通过组织编号查询下属的组织或个人用户
     * 
     * @param OrgId
     *            组织编号
     * @return 下属的组织或个人用户列表
     */
    public ArrayList<User> getUserList(String OrgId) {
	ArrayList<User> userList = new ArrayList<>();
	String url = "http://96.8.8.88/COA/email/com.snxoa.base.common.selectParticipants.biz.ext";
	String jsonString = this.netService.HttpGet(url + "?id=" + OrgId);
	// 将jsonString转换成为User对象列表
	return this.makeUserList(jsonString);
    }

    
    
    /**
     * 用户登录验证<br>
     * 由于系统使用session形式进行用户登录状态记录，所以必须进行一次用户登录操作，使httpclient获得和保存服务器返回的session
     * 
     * @param name
     *            用户名称
     * @param password
     *            用户密码
     * @return 成功就返回true 失败就返回false
     */
    public boolean Login(String name, String password) {
	String url = "http://96.8.8.88/portal/sso.login?username=" + name + "&flag=true&password=" + password
		+ "&loginPage=win10_login/login.jsp&service=http://96.8.8.88:80/portal/login/index.jsp&usernameByEye&passwordByEye";

	String result = this.netService.HttpGet(url);
	
	return result != null ? true: null;
    }
    
    /**
     * 更新用户树
     * 
     * @param userId
     */
    public void updateUserList(String userId,DefaultMutableTreeNode root) {
	// TODO Auto-generated method stub
	ArrayList<User> userList = getUserList(userId);
	// DefaultMutableTreeNode node = userService.makeUserTree(userList);
	DefaultMutableTreeNode new_root = addNodeToTree(root, userList);
	System.out.println(new_root);

    }

    
    
    /**
     * 将用户类型列表转换成树型结构
     * 
     * @param userList
     *            用户类型列表
     * @return 返回树型结构的根节点
     */
    public DefaultMutableTreeNode makeUserTree(ArrayList<User> userList) {
	DefaultMutableTreeNode root = new DefaultMutableTreeNode(userList.get(0));
	for (User ListNode : userList) {
	    Enumeration em = root.postorderEnumeration();
	    DefaultMutableTreeNode TreeNode = null;
	    while ((TreeNode = (DefaultMutableTreeNode) em.nextElement()) != null) {
		User user = (User) TreeNode.getUserObject();
		if (ListNode.getParentId() != null && ListNode.getParentId().equals(user.getId())) {
		    TreeNode.add(new DefaultMutableTreeNode(ListNode));
		}
	    }
	}

	return root;
    }

    
    /**
     * 将新节点添加到原来的树当中
     * @param totalTree
     * @param node
     * @return
     */
    public DefaultMutableTreeNode addNodeToTree(DefaultMutableTreeNode totalTree,ArrayList<User> userList) {
	
	for (User ListNode : userList) {
	    Enumeration em = totalTree.postorderEnumeration();
	    DefaultMutableTreeNode TreeNode = null;
	    while ((TreeNode = (DefaultMutableTreeNode) em.nextElement()) != null) {
		User user = (User) TreeNode.getUserObject();
		if (ListNode.getParentId() != null && ListNode.getParentId().equals(user.getId())) {
		    TreeNode.add(new DefaultMutableTreeNode(ListNode));
		}
	    }
	}

	return totalTree;
	
    }
    
    
    
    /**
     * 将json字符串转换成用户对象列表
     * 
     * @param jsonString
     * @return 用户对象列表
     */
    public ArrayList<User> makeUserList(String jsonString) {
//	String s=jsonString.replace("\"", "");
//	System.out.println(s);
	ArrayList<User> userList = new ArrayList<>();
	JsonParser jp = new JsonParser();
	JsonObject jo = (JsonObject) jp.parse(jsonString);
	JsonArray participants = jo.getAsJsonArray("participants");
	for (int i = 0; i < participants.size(); i++) {
	    JsonObject joi = participants.get(i).getAsJsonObject();
	    User u = new User();
	    u.setExpanded(joi.get("expanded").toString());
	    u.setId(joi.get("id").toString());
	    u.setImg(joi.get("img").toString());
	    u.setIsLeaf(joi.get("isLeaf").toString());
	    u.setName(joi.get("name").toString());
	    u.setParentId(joi.get("parentId").toString());
	    u.setTypeCode(joi.get("typeCode").toString());
	    if (joi.get("empcode") != null) {
		u.setEmpcode(joi.get("empcode").toString());
	    } else {
		u.setEmpcode("");
	    }
	    if (joi.get("mobileno") != null) {
		u.setMobileno(joi.get("mobileno").toString());
	    } else {
		u.setMobileno("");
	    }
	    if (joi.get("htel") != null) {
		u.setOtel(joi.get("htel").toString());
	    }
	    if (joi.get("otel") != null) {
		u.setOtel(joi.get("otel").toString());
	    }
	    userList.add(u);
	}
	return userList;
    }
}
=======
/**
 * 
 */
package enixlin.jmrc.mail.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.http.impl.client.CloseableHttpClient;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import enixlin.jmrc.mail.entity.User;

/**
 * oa系统用户管理类<br>
 * 在新的oa系统中，用户管理分成了两种类型：组织（org）和雇员（emp）<br>
 * 这个类的用途是将上面两种类型的用户抽象成同一种类型就是User<br>
 * 
 * @author linzhenhuan
 *
 */
public class UserService {

    private NetService netService;

    /**
     * 
     */
    public UserService(NetService netService) {
	this.netService = netService;
	// TODO Auto-generated constructor stub
    }

    /**
     * 通过组织编号查询下属的组织或个人用户
     * 
     * @param OrgId
     *            组织编号
     * @return 下属的组织或个人用户列表
     */
    public ArrayList<User> getUserList(String OrgId) {
	ArrayList<User> userList = new ArrayList<>();
	String url = "http://96.8.8.88/COA/email/com.snxoa.base.common.selectParticipants.biz.ext";
	String jsonString = this.netService.HttpGet(url + "?id=" + OrgId);
	// 将jsonString转换成为User对象列表
	return this.makeUserList(jsonString);
    }

    
    
    /**
     * 用户登录验证<br>
     * 由于系统使用session形式进行用户登录状态记录，所以必须进行一次用户登录操作，使httpclient获得和保存服务器返回的session
     * 
     * @param name
     *            用户名称
     * @param password
     *            用户密码
     * @return 成功就返回true 失败就返回false
     */
    public boolean Login(String name, String password) {
	String url = "http://96.8.8.88/portal/sso.login?username=" + name + "&flag=true&password=" + password
		+ "&loginPage=win10_login/login.jsp&service=http://96.8.8.88:80/portal/login/index.jsp&usernameByEye&passwordByEye";

	String result = this.netService.HttpGet(url);
	
	return result != null ? true: null;
    }
    
    /**
     * 更新用户树
     * 
     * @param userId
     */
    public void updateUserList(String userId,DefaultMutableTreeNode root) {
	// TODO Auto-generated method stub
	ArrayList<User> userList = getUserList(userId);
	// DefaultMutableTreeNode node = userService.makeUserTree(userList);
	DefaultMutableTreeNode new_root = addNodeToTree(root, userList);
	System.out.println(new_root);

    }

    
    
    /**
     * 将用户类型列表转换成树型结构
     * 
     * @param userList
     *            用户类型列表
     * @return 返回树型结构的根节点
     */
    public DefaultMutableTreeNode makeUserTree(ArrayList<User> userList) {
	DefaultMutableTreeNode root = new DefaultMutableTreeNode(userList.get(0));
	for (User ListNode : userList) {
	    Enumeration em = root.postorderEnumeration();
	    DefaultMutableTreeNode TreeNode = null;
	    while ((TreeNode = (DefaultMutableTreeNode) em.nextElement()) != null) {
		User user = (User) TreeNode.getUserObject();
		if (ListNode.getParentId() != null && ListNode.getParentId().equals(user.getId())) {
		    TreeNode.add(new DefaultMutableTreeNode(ListNode));
		}
	    }
	}

	return root;
    }

    
    /**
     * 将新节点添加到原来的树当中
     * @param totalTree
     * @param node
     * @return
     */
    public DefaultMutableTreeNode addNodeToTree(DefaultMutableTreeNode totalTree,ArrayList<User> userList) {
	
	for (User ListNode : userList) {
	    Enumeration em = totalTree.postorderEnumeration();
	    DefaultMutableTreeNode TreeNode = null;
	    while ((TreeNode = (DefaultMutableTreeNode) em.nextElement()) != null) {
		User user = (User) TreeNode.getUserObject();
		if (ListNode.getParentId() != null && ListNode.getParentId().equals(user.getId())) {
		    TreeNode.add(new DefaultMutableTreeNode(ListNode));
		}
	    }
	}

	return totalTree;
	
    }
    
    
    
    /**
     * 将json字符串转换成用户对象列表
     * 
     * @param jsonString
     * @return 用户对象列表
     */
    public ArrayList<User> makeUserList(String jsonString) {
//	String s=jsonString.replace("\"", "");
//	System.out.println(s);
	ArrayList<User> userList = new ArrayList<>();
	JsonParser jp = new JsonParser();
	JsonObject jo = (JsonObject) jp.parse(jsonString);
	JsonArray participants = jo.getAsJsonArray("participants");
	for (int i = 0; i < participants.size(); i++) {
	    JsonObject joi = participants.get(i).getAsJsonObject();
	    User u = new User();
	    u.setExpanded(joi.get("expanded").toString());
	    u.setId(joi.get("id").toString());
	    u.setImg(joi.get("img").toString());
	    u.setIsLeaf(joi.get("isLeaf").toString());
	    u.setName(joi.get("name").toString());
	    u.setParentId(joi.get("parentId").toString());
	    u.setTypeCode(joi.get("typeCode").toString());
	    if (joi.get("empcode") != null) {
		u.setEmpcode(joi.get("empcode").toString());
	    } else {
		u.setEmpcode("");
	    }
	    if (joi.get("mobileno") != null) {
		u.setMobileno(joi.get("mobileno").toString());
	    } else {
		u.setMobileno("");
	    }
	    if (joi.get("htel") != null) {
		u.setOtel(joi.get("htel").toString());
	    }
	    if (joi.get("otel") != null) {
		u.setOtel(joi.get("otel").toString());
	    }
	    userList.add(u);
	}
	return userList;
    }
}
>>>>>>> a05da3cbea95e88edb804f5d712c700cf95594e3
