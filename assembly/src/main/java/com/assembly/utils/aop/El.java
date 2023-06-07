package com.assembly.utils.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
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
     * 初始化解析器
     *
     * @param joinPoint 切点
     * @return 解析反射字段
     */
    public static StandardEvaluationContext analysisInit(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object [] args = joinPoint.getArgs();

        // 获取被拦截方法参数名列表
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNameArr = discoverer.getParameterNames(method);

        // 如果参数为空
        if (paramNameArr == null) {
            throw new RuntimeException("未解析到参数！");
        }

        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNameArr.length; i++) {
            context.setVariable(paramNameArr[i], args[i]);
        }

        return context;
    }

    /**
     * 解析字段
     *
     * @param context 解析器
     * @param param 参数
     * @param tClass tClass
     * @return 解析结果
     * @param <T> 泛型
     */
    public static <T> T analysis(StandardEvaluationContext context, String param, Class<T> tClass) {
        return new SpelExpressionParser().parseExpression(param).getValue(context, tClass);
    }

}
