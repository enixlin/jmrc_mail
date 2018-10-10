
/**
 * 
 */
package enixlin.jmrc.mail.service;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

    private CloseableHttpClient httpClient;
    private HttpGet  httpGet;
    private HttpPost httpPost;

    public CloseableHttpClient getHttpClient() {
	return httpClient;
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
	this.httpClient = HttpClients.createDefault();
	return this.httpClient;
    }

    /**
     * 使用 get方法提交HTTP请求
     * 
     * @param requestUrl请求的地址
     * @return 结果字符串
     */
    public String HttpGet(String requestUrl) {
	String result = null;

	HttpGet httpGet = new HttpGet(requestUrl);
	try {
	    CloseableHttpResponse response = this.httpClient.execute(httpGet);
	    HttpEntity entity = response.getEntity();
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
