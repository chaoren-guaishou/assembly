package com.assembly.utils.trans;

import cn.hutool.core.text.StrPool;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 字符串工具类
 * </p>
 *
 * @author DPJ
 * @since 2023-03-15
 */
public class StringUtil {

    /**
     * 字符串分割转List
     *
     * @param str 字符串
     * @param regex 分割的正则表达式
     * @return list
     */
    public static List<String> splitToList(String str, String regex) {
        return Arrays.asList(str.split(regex));
    }

    /**
     * 字符串分割转List(按英文逗号)
     *
     * @param str 字符串
     * @return list
     */
    public static List<String> splitCommaToList(String str) {
        return splitToList(str, StrPool.COMMA);
    }

}
