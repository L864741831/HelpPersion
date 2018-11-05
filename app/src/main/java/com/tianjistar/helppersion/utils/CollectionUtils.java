package com.tianjistar.helppersion.utils;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * CollectionUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-7-22
 */
public class CollectionUtils {

    /**
     * default join separator
     **/
    public static final CharSequence DEFAULT_JOIN_SEPARATOR = ",";

    private CollectionUtils() {
        throw new AssertionError();
    }

    /**
     * is null or its size is 0
     * <p/>
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     *
     * @param <V>
     * @param c
     * @return if collection is null or its size is 0, return true, else return false.
     */
    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.size() == 0);
    }

    /**
     * join collection to string, separator is {@link #DEFAULT_JOIN_SEPARATOR}
     * <p/>
     * <pre>
     * join(null)      =   "";
     * join({})        =   "";
     * join({a,b})     =   "a,b";
     * </pre>
     *
     * @param collection
     * @return join collection to string, separator is {@link #DEFAULT_JOIN_SEPARATOR}. if collection is empty, return
     * ""
     */
    public static String join(Iterable collection) {
        return collection == null ? "" : TextUtils.join(DEFAULT_JOIN_SEPARATOR, collection);
    }

    /**
     * 判断集合是否有元素
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return c != null && !c.isEmpty();
    }

    /**
     * 获取集合的size（防止空指针异常）
     *
     * @param c
     * @return
     */
    public static int getSize(Collection<?> c) {
        return c == null ? 0 : c.size();
    }


    /**
     * 用字符串拼接集合
     *
     * @param list
     * @param delimiter
     * @return
     */
    public static String join(List<?> list, String delimiter) {
        return join(list, null, delimiter);
    }

    /**
     * 用字符串拼接集合
     *
     * @param list
     * @param delimiter
     * @return
     */
    public static String join(List<?> list, String fieldName, String delimiter) {
        if (isEmpty(list)) {
            return "";
        }
        // 默认为,号分割
        if (null == delimiter || "".equalsIgnoreCase(delimiter)) {
            delimiter = ",";
        }

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                buffer.append(delimiter);
            }
            Object item = list.get(i);
            if (item instanceof String) {
                buffer.append(item);
            } else {
                try {
                    // 反射获取某个字段的值
                    Field field = item.getClass().getField(fieldName);
                    buffer.append(field.get(item));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }

}
