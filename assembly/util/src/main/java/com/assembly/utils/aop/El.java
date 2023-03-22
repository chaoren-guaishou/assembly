package com.assembly.utils.aop;

import cn.hutool.core.text.CharSequenceUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * <p>
 * 解析EL表达式
 * </p>
 *
 * @author DPJ
 * @since 2023-03-22
 */
public class El {

    /**
     * 解析业务ID
     *
     * @param bizId 业务ID
     * @param joinPoint 切入点
     * @return 业务ID
     */
    private String analysisBizId(String bizId, JoinPoint joinPoint) {
        // 拦截方法,参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object [] args = joinPoint.getArgs();

        // 获取被拦截方法参数名列表
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNameArr = discoverer.getParameterNames(method);

        // 如果参数为空
        if (paramNameArr == null) {
           // TODO 抛异常
            return CharSequenceUtil.EMPTY;
        }

        // EL表达式 解析
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNameArr.length; i++) {
            context.setVariable(paramNameArr[i], args[i]);
        }
        return parser.parseExpression(bizId).getValue(context, String.class);
    }

}
