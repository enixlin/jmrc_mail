<<<<<<< HEAD
/**
 * 
 */
package enixlin.jmrc.mail.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 自己封装的一个json应用工具，底层是应用了google的gson库
 * @author enixlin
 *
 */
public class JsonUtil {

    public JsonObject toJsonObject(String jsonString) {
	return (JsonObject) new JsonParser().parse(jsonString);
    }
}
=======
/**
 * 
 */
package enixlin.jmrc.mail.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 自己封装的一个json应用工具，底层是应用了google的gson库
 * @author enixlin
 *
 */
public class JsonUtil {

    public JsonObject toJsonObject(String jsonString) {
	return (JsonObject) new JsonParser().parse(jsonString);
    }
}
>>>>>>> a05da3cbea95e88edb804f5d712c700cf95594e3