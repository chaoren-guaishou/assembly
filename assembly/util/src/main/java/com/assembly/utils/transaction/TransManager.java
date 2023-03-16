package com.assembly.utils.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 事务管理：添加该注解之后,方法会在主事务提交之后,执行当前方法
 * </p>
 *
 * @author DPJ
 * @since 2023-01-31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TransManager {

    /**
     * 目标方法名称(用作日志打印,描述时要言简意赅)
     *
     * @return 目标方法名称
     */
    String logDesc() default "";
}
