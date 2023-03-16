package com.assembly.utils.idmpotent.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharUtil;
import com.assembly.utils.idmpotent.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 接口重刷 处理
 * </p>
 *
 * @author DPJ
 * @since 2022-09-14
 */
@Aspect
@Component
public class IdempotentAspect {

    private final Logger log = LoggerFactory.getLogger(IdempotentAspect.class);

    /**
     * 缓存
     */
    @Resource
    private RedisTemplate<String, String> redisCache;

    /**
     * 幂等校验KEY前缀
     */
    public static final String KEY = "idempotent:Idem|";

    @Before("@annotation(idempotent)")
    public void handle(JoinPoint joinPoint, Idempotent idempotent) {
        // 获取参数值
        Object[] args = joinPoint.getArgs();
        // 构造缓存的key
        // 此处使用的是方法路径 + 参数的hashcode 作为key，在个别情况下有可能会重复
        StringBuilder key = new StringBuilder();
        for (Object arg : args) {
            key.append(Convert.toStr(arg));
        }

        // 方法路径
        String methodPath = joinPoint.getSignature().getDeclaringTypeName() + CharUtil.DOT + joinPoint.getSignature().getName();
        log.info("幂等校验路径[{}]", methodPath);

        // redis key
        String redisKey = KEY + methodPath + key.toString().hashCode();

        // 若key已存在，说明在expired()的时间范围内已经请求过。当前请求为重复请求；
        if (Boolean.TRUE.equals(redisCache.hasKey(redisKey))) {
            throw new RuntimeException("当前请求为重复请求！");
        }

        // 若key不存在，则把当前key设置到redis中，并设置过期时间。
        redisCache.opsForValue().set(redisKey, redisKey, idempotent.expired(), TimeUnit.MILLISECONDS);
    }

}
