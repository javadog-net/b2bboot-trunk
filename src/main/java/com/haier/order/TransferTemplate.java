package com.haier.order;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.lang.reflect.Method;

/**
 * @author wangzh
 * @time 2018-11-01 15:25
 * @Description 远程调用模板
 */
public class TransferTemplate {

    private String converterGson="gson";
    private String converterScalars="scalars";

    /**
     * 执行JSOn调用模板
     * @param url 远程url
     * @param clazz 调用 class
     * @param methodName 方法名称
     * @param args 参数列表
     * @return 返回值
     * @throws Exception
     */
    public Object executeGsonTemplate(String url,Class clazz,String methodName,Object[] args,Class[] argsType) throws Exception{
        return executeTemplate(url,clazz,methodName,args,argsType,converterGson);
    }
    /**
     * 执行Scalars调用模板
     * @param url 远程url
     * @param clazz 调用 class
     * @param methodName 方法名称
     * @param args 参数列表
     * @return 返回值
     * @throws Exception
     */
    public Object executeScalarsTemplate(String url,Class clazz,String methodName,Object[] args,Class[] argsType) throws Exception{
        return executeTemplate(url,clazz,methodName,args, argsType, converterScalars);
    }
    /**
     * 执行调用模板
     * @param url 远程url
     * @param clazz 调用 class
     * @param methodName 方法名称
     * @param args 参数列表
     * @param argsType
     * @param converterType 转换器类型
     * @return 返回值
     * @throws Exception
     */
    protected Object executeTemplate(String url, Class clazz, String methodName, Object[] args, Class[] argsType, String converterType) throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit=getRetrofit(url,converterType);
        // 2、生成调用实例
        Object service = retrofit.create(clazz);
        // 3、组织参数
        Method method=getExecMethod(service,methodName,argsType);
        // 4、执行调用
        Call<Object> call = (Call<Object>) method.invoke(service,args);
        // 5、获取返回值
        return call.execute().body();
    }
    /**
     * 获取自行方法
     * @param service 调用的service
     * @param methodName 方法名称
     * @param args 方法参数
     * @return
     * @throws NoSuchMethodException
     */
    private Method getExecMethod(Object service, String methodName, Class[] args) throws NoSuchMethodException {
//        Class[] calzzs=new Class[args.length];
//        for(int i=0;i<args.length;i++){
//            calzzs[i]=args[i].getClass();
//        }
        return service.getClass().getMethod(methodName,args);
    }


    /**
     * 获取调用 Retrofit
     * @param url 调用url
     * @param converterType 转换器类型
     * @return
     */
    private Retrofit getRetrofit(String url, String converterType) {
        if(converterType.equals(converterScalars)){
            return new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
