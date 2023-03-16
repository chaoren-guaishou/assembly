package com.assembly.utils.transaction;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * <p>
 * 事务管理执行
 * </p>
 *
 * @author DPJ
 * @since 2023-01-31
 */
@Configuration
@Aspect
public class TransManagerAspect {

    Logger log = LoggerFactory.getLogger(TransManagerAspect.class);

    @Around("@annotation(transManager)")
    public Object process(ProceedingJoinPoint joinPoint, TransManager transManager) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @SneakyThrows
            @Override
            public void afterCommit() {
                joinPoint.proceed();
                log.info("事务管理执行成功, 目标方法:[{}]......", transManager.logDesc());
            }
        });
        return null;
    }

}
