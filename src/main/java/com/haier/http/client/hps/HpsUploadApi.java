package com.haier.http.client.hps;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.haier.http.client.utils.CharsetHandler;
import com.haier.http.client.utils.HttpUtil;
import com.jhmis.common.utils.Constants;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static com.haier.http.client.utils.HttpUtil.closeHttpGet;

/**
 * //调取hps上传文件
 * @param
 * @return
 */
public class HpsUploadApi {

    //调取hps上传文件相关密钥
    public static final String API_GATEWAY_AUTH_URL = Constants.HPS_URL + "/api/noaop/file/upload";

    public static void main(String[] args) {
        try {
            hpsUploadApi(new File("F:\\logo1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<String,Object> hpsUploadApi (File file) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Map<String,Object> map = new HashMap<>();
        //调用成功与否标识
        Boolean flag = false;
        //结果返回
        String result = "";
        //提示信息
        String msg = "";
        //初始化httpget参数
        HttpPost httppost=null;
        //httpClient回调数据
        String content="";
        //验证信息
        //鹰眼接口路劲
        String url = HpsUploadApi.API_GATEWAY_AUTH_URL;
        try {
            try {
                httppost = new HttpPost(new URI(url));
                JSONObject param2= new JSONObject();
                param2.put("businessType", "VERIFY_BILL");
                param2.put("fileType"," FAIL_FILE");
                param2.put("description"," Webservice");
                //File file = new File("F:\\logo1.png");
                FileBody fileBody = new FileBody(file);
                StringBody businessType = new StringBody("VERIFY_BILL", ContentType.create("text/plain", Consts.UTF_8));
                StringBody fileType = new StringBody("FAIL_FILE", ContentType.create("text/plain", Consts.UTF_8));
                StringBody description = new StringBody("Webservice", ContentType.create("text/plain", Consts.UTF_8));
                HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                        .addPart("file", fileBody)//相当于<input type="file" name="file"/>
                        .addPart("businessType", businessType)
                        .addPart("fileType", fileType)//相当于<input type="text" name="fileType" value=name>
                        .addPart("description", description)
                        .build();
                httppost.setEntity(reqEntity);
                CloseableHttpResponse response = httpClient.execute(httppost);
                System.out.println(response.getStatusLine());
                //获取响应对象
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //打印响应长度
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    //打印响应内容
                    //System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
                    //String resStr = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
                    JSONObject jodata =JSONObject.fromObject(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
                    String errmsg = (String)jodata.get("errmsg");
                    if("OK".equals(errmsg)){
                        JSONObject data = (JSONObject)jodata.get("data");
                        String id = (String)data.get("id");
                        map.put("flag",true);
                        map.put("id",id);
                        map.put("msg","接口调用成功");
                    }else{
                        map.put("flag",false);
                        map.put("msg","接口调用失败");
                    }
                }
            } catch (Exception e) {
                map.put("flag",false);
                map.put("msg","接口调用失败，原因=" + e.getMessage());
                e.printStackTrace();
                return map;
            }
        }finally{
            httpClient.close();
        }
        return map;
    }
}
