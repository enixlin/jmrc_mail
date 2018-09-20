/**
 * 
 */
package enixlin.jmrc.mail.util;

import java.util.Comparator;

/**
 *定义按数字排序算法
 * @author black
 * @since 2011-10-20
 */
public class OrderNumberComparator implements Comparator{

    public int compare(Object o1, Object o2) {
        int i = (Integer) o1 - (Integer) o2;
            return i;
    }

}