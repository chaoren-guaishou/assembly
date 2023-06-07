package com.assembly.utils.transaction;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * <p>
 * 事务管理器 - 代理方法
 *          - 方法增强
 * </p>
 *
 * @author DPJ
 * @since 2023-03-16
 */
public class TransManagerUtil {

    /**
     * 事务管理器代理方法
     *
     * @param consumer 代理函数
     * @param t 入参
     * @param <T> 泛型T
     */
    public static <T> void proxy(Consumer<T> consumer, T t) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                consumer.accept(t);
            }
        });
    }

    /**
     * 事务管理器代理方法
     *
     * @param consumer 代理函数
     * @param t1 入参1
     * @param t2 入参2
     * @param <T1> 泛型T1
     * @param <T2> 泛型T2
     */
    public static <T1, T2> void proxy(BiConsumer<T1, T2> consumer, T1 t1, T2 t2) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                consumer.accept(t1, t2);
            }
        });
    }

    /**
     * 事务管理器代理方法
     *
     * @param consumer 代理函数
     */
    public static void proxy(NoArgsConsumer consumer) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                consumer.apply();
            }
        });
    }

}
