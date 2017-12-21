package com.example.demo.controller;

import com.example.demo.config.UrlConfig;
import com.example.demo.dto.*;
import com.example.demo.enums.MethodEnum;
import com.example.demo.enums.ResultEnum;
import com.example.demo.service.FileMongoService;
import com.example.demo.service.TencentID_corService;
import com.example.demo.tencentdemo.util.IoUtil;
import com.example.demo.utils.ObjectToJSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Map;

/**
 * Created by yhn on 2017/12/7.
 */
@RestController
@Slf4j
public class FacemergeController {
    @Autowired
    private TencentID_corService tencentID_corService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private WxMpService wxMpService; //已配置完成
    @Autowired
    private FileMongoService fileMongoService;

    @GetMapping("/face")
    public ModelAndView test(@RequestParam("url") String url,
                                Map<String,Object> map) {
        map.put("projectUrl",urlConfig.getUrl());
        map.put("url",url);   //本地图片的一张url
        return new ModelAndView("page/facemerge",map);
    }
    @GetMapping("/wechatface")
    public ModelAndView wechatface(@RequestParam("url") String url,
                             Map<String,Object> map)throws Exception {
        log.info("url={}",url);
        map.put("projectUrl",urlConfig.getUrl());
        map.put("url",URLEncoder.encode(url,"UTF-8"));   //本地图片的一张url
        return new ModelAndView("page/facemerge2",map);
    }

    @GetMapping("/getFaceMerge")
    public void getFaceMerge(@RequestParam("url") String url
                                ,@RequestParam("model") String model,
                                HttpServletResponse response)throws Exception{
        String json = tencentID_corService.getFaceMerge(url,model,MethodEnum.url2byte.getMessage());
        JSONParser parser=new JSONParser();
        org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
        FaceMerge persion_id =new FaceMerge(data);
        if(persion_id.getRet()==0){
            try{
                String xmlImg = persion_id.getImage();
                response.setContentType("image/*"); // 设置返回的文件类型
                OutputStream toClient = response.getOutputStream();
                IoUtil.GenerateImage(xmlImg,toClient);
            }catch(Exception ex) {
                ex.printStackTrace();
            }

        }else{
            log.error("显示返回码：ret={}",persion_id.getRet());
        }
    }
    @GetMapping("/getFaceMerge2")
    public void getFaceMerge2(@RequestParam("url") String url
            ,@RequestParam("model") String model,
                             HttpServletResponse response)throws Exception{
        log.info("url是:"+url);
        String json = tencentID_corService.getFaceMerge(URLDecoder.decode(url,"UTF-8"),model,MethodEnum.local2byte.getMessage());
        JSONParser parser=new JSONParser();
        org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
        FaceMerge persion_id =new FaceMerge(data);
        if(persion_id.getRet()==0){
            try{
                String xmlImg = persion_id.getImage();
                response.setContentType("image/*"); // 设置返回的文件类型
                OutputStream toClient = response.getOutputStream();
                IoUtil.GenerateImage(xmlImg,toClient);
            }catch(Exception ex) {
                ex.printStackTrace();
            }

        }else{
            log.error("显示返回码：ret={}",persion_id.getRet());
        }
    }



    @PostMapping("/uploadImage")   //上传临时素材
    public String  uploadImage(@RequestParam("media_id") String media_id,@RequestParam("action")String action)throws Exception{
        File file = wxMpService.getMaterialService().mediaDownload(media_id);//本地路径
        log.info("file={}",file.toString());
        try {
            if(action.equals(String.valueOf(ResultEnum.PERSON_ID.getCode()))) {  //身份证识别
                String json = tencentID_corService.getIdInformation(file.toString(),MethodEnum.local2byte.getMessage());
                JSONParser parser=new JSONParser();
                org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
                Persion_ID persion_id =new  Persion_ID(data);
                String str = ObjectToJSON.getJSON(persion_id);
                log.info("给前端信息：str={}",str);
                return str;
            }
            if(action.equals(String.valueOf(ResultEnum.PHOTO_SPEAK.getCode()))) {  //看图说话
                String json = tencentID_corService.getPhoto_Speak(file.toString(),MethodEnum.local2byte.getMessage());
                JSONParser parser=new JSONParser();
                org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
                PhotoSpeak persion_id =new  PhotoSpeak(data);
                String str = ObjectToJSON.getJSON(persion_id);
                log.info("给前端信息：str={}",str);
                return str;
            }
            if(action.equals(String.valueOf(ResultEnum.IMAGE_LABEL.getCode()))) {  //标签识别
                String json = tencentID_corService.getLabelPhoto(file.toString(),MethodEnum.local2byte.getMessage());
                JSONParser parser=new JSONParser();
                org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
                LabelPhoto persion_id =new  LabelPhoto(data);
                String str = ObjectToJSON.getJSON(persion_id);
                log.info("给前端信息：str={}",str);
                return str;
            }
            if(action.equals(String.valueOf(ResultEnum.FACE_MERGE.getCode()))) {  //人脸识别
                FaceMerge faceMerge = new FaceMerge();
                    faceMerge.setRet(new Long(0));
                    faceMerge.setMsg("success");
                    faceMerge.setImage(URLEncoder.encode(file.toString(),"UTF-8"));
                return ObjectToJSON.getJSON(faceMerge);
            }
            if(action.equals(String.valueOf(ResultEnum.PHOTOFORTRANSLATE.getCode()))) {  //图片翻译
                String json = tencentID_corService.getPhotoForTranslate(file.toString(),"doc","en","zh",MethodEnum.local2byte.getMessage());
                JSONParser parser=new JSONParser();
                org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
                PhotoForTranslate photoForTranslate = new PhotoForTranslate(data);
                String str = ObjectToJSON.getJSON(photoForTranslate);
                log.info("给前端信息：str={}",str);
                return str;
            }
            log.warn("无任何内容");
            return null;
        }catch (Exception e){
            e.printStackTrace();
            log.error("异常");
        }
        return null;
    }
}
