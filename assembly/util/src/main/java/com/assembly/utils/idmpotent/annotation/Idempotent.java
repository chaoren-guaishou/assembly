package com.assembly.utils.idmpotent.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 幂等校验
 * >基于在一定时间内 请求路径+请求参数相同,暂且定义为防止接口重刷
 * </p>
 *
 * @author DPJ
 * @since 2022-09-14
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    int expired() default 5000;
}
