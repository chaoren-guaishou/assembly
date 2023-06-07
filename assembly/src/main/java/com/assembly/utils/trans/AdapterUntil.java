package com.assembly.utils.trans;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.util.List;

/**
 * <p>
 * 类型适配器 会校验空
 * </p>
 *
 * @author DPJ
 * @since 2022-12-20
 */
public class AdapterUntil {


    /**
     * JSON字符串转List
     *
     * @param jsonStr JSON字符串
     * @return List<T>
     * @param <T> 类型
     */
    public static <T> List<T> jsonStrToList(String jsonStr, Class<T> tClass) {
        return CharSequenceUtil.isNotBlank(jsonStr)
                ? JSONUtil.toList(jsonStr, tClass)
                : ListUtil.empty();
    }

    /**
     * Integer 适配器:主要是为了转换为null的数据
     *
     * @param number 入参
     * @return Integer
     */
    public static Integer integerAdapter(Integer number) {
        return ObjectUtil.isNull(number) ? 0 : number;
    }

    /**
     * Long 适配器:主要是为了转换为null的数据
     *
     * @param number 入参
     * @return Long
     */
    public static Long longAdapter(Long number) {
        return ObjectUtil.isNull(number) ? 0L : number;
    }

}
