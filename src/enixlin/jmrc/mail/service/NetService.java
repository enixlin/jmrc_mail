
/**
 * 
 */
package enixlin.jmrc.mail.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 网络连接服务实现类
 * 
 * @author enixlin
 * 
 *
 */
public class NetService {

	private BasicCookieStore store=new BasicCookieStore();
    private CloseableHttpClient httpClient;
    private HttpGet  httpGet;
    private HttpPost httpPost;
    public CloseableHttpResponse response;
    public HttpEntity entity;

    public CloseableHttpClient getHttpClient() {
	return httpClient;
    }
    
    public ArrayList<Cookie> getCookieList() {
    	
    	return (ArrayList<Cookie>) store.getCookies();
    }


    /**
     * 网络连接服务实现类NetService构造函数
     */
    public NetService() {
	super();
	// TODO Auto-generated constructor stub
	this.httpClient = this.createHttpClient();
    }

    /**
     * 创建一个http连接客户端，整个应用程序都会使用户个客户端进行http请求<br>
     * 主要是用户登录验证和用户查询等操作
     * 
     * @return
     */
    public CloseableHttpClient createHttpClient() {
	this.httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
    	return this.httpClient;
//	RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();  
//	CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();  
//    return client;
    }

    /**
     * 使用 get方法提交HTTP请求
     * 
     * @param requestUrl请求的地址
     * @return 结果字符串
     */
    public String HttpGet(String requestUrl) {
	String result = null;
	HttpGet httpGet=null;
	URIBuilder builder;
	try {
		
		 httpGet = new HttpGet(requestUrl);
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
	try {
	     response = this.httpClient.execute(httpGet);
	     entity = response.getEntity();
	    result = EntityUtils.toString(entity);
	  

	} catch (ClientProtocolException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return result;

    }

}
