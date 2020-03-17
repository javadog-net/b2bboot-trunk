package com.jhmis.core.web;

import freemarker.core.Environment;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义标签基础方法
 */
public class BaseDirective implements TemplateDirectiveModel{


    private static final ConcurrentHashMap<String, Class> classBucketMap = new ConcurrentHashMap<>();
    private static Logger logger = LoggerFactory.getLogger(BaseDirective.class);
    private String clazzPath = null;
//

//    public BaseDirective(String...objs) {
////        for(Object obj:objs){
//////
//////        }
////        clazzPath = targetClass;
////        if (classBucketMap.get(clazzPath) == null) {
////            try {
////                Class clazz = Class.forName(clazzPath);
////                classBucketMap.put(clazzPath, clazz);
////            } catch (ClassNotFoundException e) {
////                logger.error("无法从[{}]获取对应的class", clazzPath, e);
////            }
////        }
//    }
    /**
     * 获取参数名，解决属性名大小写不规范问题
     * @param params
     * @param name
     * @return
     */
    public String getParamName(Map params,String name){
        if (params!=null && !params.isEmpty()) {
            Iterator<String> names = params.keySet().iterator();
            String currName;
            while (names.hasNext()) {
                currName=names.next();
                if (currName.equalsIgnoreCase(name)) {
                    return currName;
                }
            }
        }
        return name;
    }
    /**
     * 获得参数
     * @param params
     * @param name
     * @return
     */
    public String getParam(Map params,String name){
        String value="";
        name=getParamName(params, name);
        if (params.get(name)!=null && params.get(name).toString().length()>0) {
            value=params.get(name).toString();
        }
        return value;
    }
    /**
     * 获得参数并传递默认值
     * @param params
     * @param name
     * @param defaultValue
     * @return
     */
    public String getParam(Map params,String name,String defaultValue){
        String value=defaultValue;
        name=getParamName(params, name);
        if (params.get(name)!=null && params.get(name).toString().length()>0) {
            value=params.get(name).toString();
        }
        return value;
    }
    /**
     * 获得int参数并传递默认值
     * @param params
     * @param name
     * @param defaultValue
     * @return
     */
    public int getParamInt(Map params,String name,int defaultValue){
        int value=defaultValue;
        name=getParamName(params, name);
        if (params.get(name)!=null && params.get(name).toString().length()>0) {
            try {
                value=Integer.parseInt(params.get(name).toString());
            } catch (Exception e) {
            }
        }
        return value;
    }
    private String getMethod(Map params) {
        return this.getParam(params, "method");
    }
    private TemplateModel getModel(Object o) throws TemplateModelException {
        return this.getBuilder().wrap(o);
    }
    private DefaultObjectWrapper getBuilder() {
        return new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25).build();
    }
//    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
//        this.verifyParameters(params);
//        String funName = getMethod(params);
//
//        Class clazz = classBucketMap.get(clazzPath);
//        Method method = null;
//        try {
//            if (clazz != null && (method = clazz.getMethod("findLink",Map.class)) != null) {
//                // 核心处理，调用子类的具体方法，获取返回值
//                boolean t = method.isAccessible();
//                method.setAccessible(true);
//                Object res = method.invoke(this, params);
//                env.setVariable(funName, getModel(res));
//            }
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            logger.error("无法获取[{}]的方法，或者调用[{}]方法发生异常", clazzPath, method, e);
//        }
//        body.render(env.getOut());
    }
    /**
     * 把变量名第一个字设置为大写
     * @return
     */
    public String varMethodName(String varName){
        if (varName!=null && varName.trim().length()>0) {
            return varName.replaceFirst(varName.substring(0, 1),varName.substring(0, 1).toUpperCase());
        }
        return varName;
    }
//    public  void init(String...objs){
//        try {
//            for(String obj:objs){
//                if(obj!=null && obj.trim().length()>0){
//                    //获取变量的get方法
//                    //get方法没有参数，不能传null作为参数，必须指定参数类型，否者会有警告
//                    Method method=getClass().getMethod("get"+varMethodName(obj));
//                    //调用get方法，判断对象是否已初始化
//                    if (method.invoke(this)==null) {
//                        //获取get方法返回类型，即变量类型,然后动态创建对象
//                        initObj(method.getReturnType().toString(), obj);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * 初始化指定变量
     * @param className 要创建对象的类名
     * @param varName 变量名
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
//    public  void initObj(String className,String varName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
//        className=className.replaceFirst("class ", "").replaceFirst("interface ", "");
//        //获取名全名
//        Class<?> clazz=Class.forName(className);
//        //动态创建对象
//        Object obj=.getBean(clazz);
//        //获取变量的set方法
//        Method method=getClass().getMethod("set"+varMethodName(varName), clazz);
//        //调用set方法
//        method.invoke(this, obj);
//    }
}
