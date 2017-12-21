package com.example.demo.service.serviceImpl;

import com.example.demo.enums.MethodEnum;
import com.example.demo.service.TencentID_corService;
import com.example.demo.tencentdemo.sign.TencentAISign;
import com.example.demo.tencentdemo.sign.TencentAISignSort;
import com.example.demo.tencentdemo.util.Base64Util;
import com.example.demo.tencentdemo.util.HttpsUtil4Tencent;
import com.example.demo.tencentdemo.util.TencentAPI;
import com.example.demo.tencentdemo.util.UrlMethodUtil;
import com.example.demo.utils.KeyUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhn on 2017/12/6.
 */
@Service
@Scope("prototype")
public class TencentID_corServiceImpl implements TencentID_corService {
    @Override
    public String getPhoto_Speak(String url,String method) throws Exception {
        //时间戳
        String time_stamp = System.currentTimeMillis()/1000+"";
        byte[] imageData;
        if(method.equals(MethodEnum.url2byte.getMessage())) {
            imageData = UrlMethodUtil.url2byte(url);
        }
        else{
            imageData = UrlMethodUtil.local2byte(url);
        }
        //图片的base64编码数据
        String img64 = Base64Util.encode(imageData);
        //随机字符串
        String nonce_str = TencentAISign.getRandomString(10);
        Map<String,String> person_Id_body = new HashMap<>();
        person_Id_body.put("app_id", String.valueOf(TencentAPI.APP_ID_AI));
        person_Id_body.put("time_stamp",time_stamp);
        person_Id_body.put("nonce_str", nonce_str);
        person_Id_body.put("image", img64);
        person_Id_body.put("session_id", KeyUtil.genUniqueKey());
        String sign = TencentAISignSort.getSignature(person_Id_body);
        person_Id_body.put("sign", sign);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HttpResponse responseBD = HttpsUtil4Tencent.doPostTencentAI(TencentAPI.PHOTO_SPEAK, headers, person_Id_body);
        String json = EntityUtils.toString(responseBD.getEntity());
        return json;
    }

    @Override
    public String getFaceMerge(String url,String model,String method) throws Exception {
        //时间戳
        String time_stamp = System.currentTimeMillis()/1000+"";
        byte[] imageData;
        if(method.equals(MethodEnum.url2byte.getMessage())) {
            imageData = UrlMethodUtil.url2byte(url);
        }
        else{
            imageData = UrlMethodUtil.local2byte(url);
        }
        //图片的base64编码数据
        String img64 = Base64Util.encode(imageData);
        //随机字符串
        String nonce_str = TencentAISign.getRandomString(10);
        Map<String,String> person_Id_body = new HashMap<>();
        person_Id_body.put("app_id", String.valueOf(TencentAPI.APP_ID_AI));
        person_Id_body.put("time_stamp",time_stamp);
        person_Id_body.put("nonce_str", nonce_str);
        person_Id_body.put("image", img64);
        person_Id_body.put("model",model);
        String sign = TencentAISignSort.getSignature(person_Id_body);
        person_Id_body.put("sign", sign);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HttpResponse responseBD = HttpsUtil4Tencent.doPostTencentAI(TencentAPI.FACE_MERGE, headers, person_Id_body);
        String json = EntityUtils.toString(responseBD.getEntity());
        System.out.println("人脸识别"+json);
        return json;
    }

    @Override
    public String getPhotoForTranslate(String url, String scene, String source, String target,String method) throws Exception {
        //时间戳
        String time_stamp = System.currentTimeMillis()/1000+"";
        //图片的二进制数组数据
        byte[] imageData;
        if(method.equals(MethodEnum.url2byte.getMessage())) {
            imageData = UrlMethodUtil.url2byte(url);
        }
        else{
            imageData = UrlMethodUtil.local2byte(url);
        }
        //图片的base64编码数据
        String img64 = Base64Util.encode(imageData);
        //随机字符串
        String nonce_str = TencentAISign.getRandomString(10);
        Map<String,String> person_Id_body = new HashMap<>();
        person_Id_body.put("app_id", String.valueOf(TencentAPI.APP_ID_AI));
        person_Id_body.put("time_stamp",time_stamp);
        person_Id_body.put("nonce_str", nonce_str);
        person_Id_body.put("image", img64);
        person_Id_body.put("session_id", KeyUtil.genUniqueKey());
        person_Id_body.put("scene", scene);
        person_Id_body.put("source", source);
        person_Id_body.put("target", target);
        String sign = TencentAISignSort.getSignature(person_Id_body);
        person_Id_body.put("sign", sign);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HttpResponse responseBD = HttpsUtil4Tencent.doPostTencentAI(TencentAPI.PHOTOFORTRANSLATE, headers, person_Id_body);
        String json = EntityUtils.toString(responseBD.getEntity());
        return json;
    }

    @Override
    public String getLabelPhoto(String url,String method) throws Exception {
        //时间戳
        String time_stamp = System.currentTimeMillis()/1000+"";
        //图片的二进制数组数据
        byte[] imageData;
        if(method.equals(MethodEnum.url2byte.getMessage())) {
            imageData = UrlMethodUtil.url2byte(url);
        }
        else{
            imageData = UrlMethodUtil.local2byte(url);
        }
        //图片的base64编码数据
        String img64 = Base64Util.encode(imageData);
        //随机字符串
        String nonce_str = TencentAISign.getRandomString(10);
        Map<String,String> person_Id_body = new HashMap<>();
        person_Id_body.put("app_id", String.valueOf(TencentAPI.APP_ID_AI));
        person_Id_body.put("time_stamp",time_stamp);
        person_Id_body.put("nonce_str", nonce_str);
        person_Id_body.put("image", img64);
        String sign = TencentAISignSort.getSignature(person_Id_body);
        person_Id_body.put("sign", sign);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HttpResponse responseBD = HttpsUtil4Tencent.doPostTencentAI(TencentAPI.IMAGE_LABEL, headers, person_Id_body);
        String json = EntityUtils.toString(responseBD.getEntity());
        return json;
    }

    public String getIdInformation(String url,String method) throws Exception {
        //时间戳
        String time_stamp = System.currentTimeMillis()/1000+"";
        //图片的二进制数组数据
        byte[] imageData;
        if(method.equals(MethodEnum.url2byte.getMessage())) {
            imageData = UrlMethodUtil.url2byte(url);
        }
        else{
            imageData = UrlMethodUtil.local2byte(url);
        }
        //图片的base64编码数据
        String img64 = Base64Util.encode(imageData);
        //随机字符串
        String nonce_str = TencentAISign.getRandomString(10);
        Map<String,String> person_Id_body = new HashMap<>();
        person_Id_body.put("app_id", String.valueOf(TencentAPI.APP_ID_AI));
        person_Id_body.put("time_stamp",time_stamp);
        person_Id_body.put("nonce_str", nonce_str);
        person_Id_body.put("image", img64);
        person_Id_body.put("card_type", "0");
        String sign = TencentAISignSort.getSignature(person_Id_body);
        person_Id_body.put("sign", sign);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HttpResponse responseBD = HttpsUtil4Tencent.doPostTencentAI(TencentAPI.PERSON_ID, headers, person_Id_body);
        String json = EntityUtils.toString(responseBD.getEntity());
        return json;
    }
}
