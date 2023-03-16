package com.assembly.utils.collections;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * List 工具类
 * </p>
 *
 * @author DPJ
 * @since 2023-03-07
 */
public class StreamUtil {

    /**
     * 抽取List指定字段作为新的List
     *
     * @param list list
     * @param mapper 指定字段
     * @return 新list
     * @param <R> 泛型R
     * @param <T> 泛型T
     */
    public static <R, T> List<R> mapToList(List<T> list, Function<? super T, ? extends R> mapper) {
        // 如果为空
        if (CollUtil.isEmpty(list)) {
            return ListUtil.empty();
        }

        return list
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * 抽取List指定字段作为新的Set
     *
     * @param list list
     * @param mapper 指定字段
     * @return 新list
     * @param <R> 泛型R
     * @param <T> 泛型T
     */
    public static <R, T> Set<R> mapToSet(List<T> list, Function<? super T, ? extends R> mapper) {
        // 如果为空
        if (CollUtil.isEmpty(list)) {
            return new HashSet<>();
        }

        return list
                .stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    /**
     * 过滤
     *
     * @param list list
     * @param predicate 指定字段
     * @return 新list
     * @param <T> 泛型T
     */
    public static <T> List<T> filterToList(List<T> list, Predicate<? super T> predicate) {
        return list
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * 转Map
     *
     * @param pairs Pair
     * @return Map
     * @param <K> 泛型K
     * @param <V> 泛型V
     */
    public static <K, V> Map<K, V> toMap(List<Pair<K, V>> pairs) {
        // 如果为空
        if (CollUtil.isEmpty(pairs)) {
            return MapUtil.newHashMap();
        }

        return pairs
                .stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    }

    /**
     * 转Map
     *
     * @param list list
     * @param keyMapper key
     * @return map
     * @param <K> 泛型K
     * @param <T> 泛型T
     */
    public static <K, T> Map<K, T> toMap(List<T> list, Function<? super T, ? extends K> keyMapper) {
        // 如果为空
        if (CollUtil.isEmpty(list)) {
            return MapUtil.newHashMap();
        }

        return list.stream()
                .collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    /**
     * 转Map
     *
     * @param list list
     * @param keyMapper key
     * @return map
     * @param <K> 泛型K
     * @param <U> 泛型T
     * @param <T> 泛型T
     */
    public static <K, U, T> Map<K, U> toMap(List<T> list, Function<? super T, ? extends K> keyMapper,
                                            Function<? super T, ? extends U> valueMapper) {
        // 如果为空
        if (CollUtil.isEmpty(list)) {
            return MapUtil.newHashMap();
        }

        return list.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * 分组
     *
     * @param list list
     * @param classifier 指定分组K
     * @return 分组结果
     * @param <K> 泛型K
     * @param <T> 泛型T
     */
    public static <K, T> Map<K, List<T>> groupingBy(List<T> list, Function<? super T, ? extends K> classifier) {
        // 如果为空
        if (CollUtil.isEmpty(list)) {
            return MapUtil.newHashMap();
        }

        return list
                .stream()
                .collect(Collectors.groupingBy(classifier));
    }

    /**
     * 过滤统计
     *
     * @param list list
     * @param predicate predicate
     * @return long
     * @param <T> 泛型T
     */
    public static <T> long filterCount(List<T> list, Predicate<? super T> predicate) {
        return list
                .stream()
                .filter(predicate)
                .count();
    }

}
