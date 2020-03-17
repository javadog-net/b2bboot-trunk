package com.jhmis.common.aspect;

import com.alibaba.fastjson.JSON;
import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.cache.LoggerCache;
import com.jhmis.common.json.AjaxJson;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.aspect
 * @Author: hdx
 * @CreateTime: 2019-08-28 15:09
 * @Description: SysLogAspect 切面实现日志记录
 */
@Aspect
@Component
public class SysLogAspect {

    @Pointcut("@annotation(com.jhmis.common.annotation.SysLog)")
    public void log() {
        System.out.println("123344");
    }

    /**
     * 加入注解自动记录方法日志
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("log()&& @annotation(annotation)")
    public Object logAround(ProceedingJoinPoint joinPoint, SysLog annotation) throws Throwable {
        // 获取执行方法的类的名称（包名加类名）
        String className = joinPoint.getTarget().getClass().getName();
        // 获取实例和方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 从缓存中获取日志实例
        Logger log = LoggerCache.getLoggerByClassName(className);
        // 记录日志
        log.info("======================开始==========================");
        log.info("*_*_*_* 接口描述desc\t===》\t" + annotation.desc());
        log.info("*_*_*_* 包路径\t===》\t"+className);
        log.info("*_*_*_* 方法名\t===》\t"+ method.getName());
        Object[] args = joinPoint.getArgs();
        String[] paramNames = getFieldsName(className, method.getName());
        log.info("*_*_*_*" + " 参数名\t===》\t" + JSON.toJSONString(paramNames));
        //会有实体对象的情况单独处理
        if(!StringUtils.isEmpty(args)){
            if(args.length>=1){
                //则这是对象
                log.info("*_*_*_*" + " 参数值\t===》\t" + (null== args[0]? "" : args[0].toString()));
            }else{
                //普通参数
                log.info("*_*_*_*" + " 参数值\t===》\t" + JSON.toJSONString(args));
            }
        }

        // 执行方法获取返回值
        Object proceed = joinPoint.proceed();
        // 记录日志
        if(proceed.getClass()== AjaxJson.class){
            log.info("*_*_*_*" + " 返回\t===》\t" + proceed );
        }else{
            log.info("*_*_*_*" + " 返回\t===》\t" + JSON.toJSONString(proceed));
        }
        log.info("======================结束==========================");
        // 返回
        return proceed;
    }

    /**
     * 使用javassist来获取方法参数名称
     * @param class_name    类名
     * @param method_name   方法名
     * @return
     * @throws Exception
     */
    private String[] getFieldsName(String class_name, String method_name) throws Exception {
        Class<?> clazz = Class.forName(class_name);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if(attr == null){
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i=0;i<paramsArgsName.length;i++){
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;

    }
}
