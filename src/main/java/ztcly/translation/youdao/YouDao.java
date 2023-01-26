package ztcly.translation.youdao;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;
import ztcly.translation.config.TSConfig;
import ztcly.translation.TranslationMod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class YouDao {
    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";
    public static Logger logger = TranslationMod.logger;
    public static Map<String, String> getConfig(){
        Map<String,String> params = new HashMap<String,String>();
        params.put("app_key", TSConfig.APP_KEY);
        params.put("app_secret",TSConfig.APP_SECRET);
        logger.info("APPKey:"+ TSConfig.APP_KEY+"   APPSecret:"+TSConfig.APP_SECRET);
        return params;
    }
    public static Output onlineTranslation(String q, String from, String to) {//langs:"zh-CHS","en"
        logger.info("OnlineTrslStart");
        Map<String, String> params = new HashMap<String, String>();
        logger.info("StartGetConfig");
        Map<String, String> appinfo = YouDao.getConfig();
        logger.info("Config:"+appinfo);
        String APP_KEY = appinfo.get("app_key");
        String APP_SECRET = appinfo.get("app_secret");
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", from);
        params.put("to", to);
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
        //params.put("vocabId","您的用户词表ID");
        /** 处理结果 */
        logger.info("params:"+ params);
        Output output = null;
        try {
            String json = requestForHttp(YOUDAO_URL, params);
            output = JSON.parseObject(json, Output.class);

        } catch (Exception e) {
            logger.error("Exception!"+e.toString());
        }
        return output;
    }
    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    public static String requestForHttp(String url,Map<String,String> params) throws IOException {
        logger.info("Start HTTP");
        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key,value));
        }
        logger.info("StartHttpExecute");
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList,"UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        try{
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            //logger.info("Content-Type:" + contentType[0].getValue());
            /** 响应不是音频流，直接显示结果 */
            HttpEntity httpEntity = httpResponse.getEntity();
            String json = EntityUtils.toString(httpEntity,"UTF-8");
            EntityUtils.consume(httpEntity);
            //logger.info(json);
            logger.info("json:"+json);
            return json;
        }finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                logger.error("IOException"+e.toString());
                throw e;
            }
        }

    }
}
