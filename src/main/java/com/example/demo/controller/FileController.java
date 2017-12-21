package com.example.demo.controller;

import com.example.demo.VO.WxJsVO;
import com.example.demo.config.UrlConfig;
import com.example.demo.dto.FaceMerge;
import com.example.demo.dto.LabelPhoto;
import com.example.demo.dto.Persion_ID;
import com.example.demo.dto.PhotoSpeak;
import com.example.demo.entity.File;
import com.example.demo.enums.MethodEnum;
import com.example.demo.enums.ResultEnum;
import com.example.demo.form.MultipartFileForm;
import com.example.demo.service.FileMongoService;
import com.example.demo.service.TencentID_corService;
import com.example.demo.utils.ObjectToJSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.api.WxMpService;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by yhn on 2017/11/8.
 */
@RestController
@Slf4j
public class FileController {
    @Autowired
    private WxMpService wxMpService; //已配置完成
    @Autowired
    private FileMongoService fileMongoService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private TencentID_corService tencentID_corService;
    @PostMapping("/testuploadimg2")
    public String success(@Valid MultipartFileForm file, BindingResult bindingResult
                        ,@RequestParam("action")String action) {
        System.out.println(action);
        if(bindingResult.hasErrors()){
            Persion_ID persion_id = new Persion_ID();   //默认return 空信息
            persion_id.setRet(new Long(500));
            persion_id.setMsg(bindingResult.getFieldError().getDefaultMessage());
            log.error("上传文件出错：error={}",bindingResult.getFieldError().getDefaultMessage());
            return ObjectToJSON.getJSON(persion_id);
        }
        File mongoFile = fileMongoService.save(file.getFile());
        log.info("回调monFile={}", mongoFile);
        String url = urlConfig.getUrl()+"/file/"+mongoFile.getId();
        log.info("url={}",url);
        try {
           if(action.equals(String.valueOf(ResultEnum.PERSON_ID.getCode()))) {  //身份证识别
             String json = tencentID_corService.getIdInformation(url,MethodEnum.url2byte.getMessage());
               JSONParser parser=new JSONParser();
               org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
               Persion_ID persion_id =new  Persion_ID(data);
               String str = ObjectToJSON.getJSON(persion_id);
               log.info("给前端信息：str={}",str);
               return str;
           }
            if(action.equals(String.valueOf(ResultEnum.PHOTO_SPEAK.getCode()))) {  //看图说话
                String json = tencentID_corService.getPhoto_Speak(url,MethodEnum.url2byte.getMessage());
                JSONParser parser=new JSONParser();
                org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
                PhotoSpeak persion_id =new  PhotoSpeak(data);
                String str = ObjectToJSON.getJSON(persion_id);
                log.info("给前端信息：str={}",str);
                return str;
            }
            if(action.equals(String.valueOf(ResultEnum.IMAGE_LABEL.getCode()))) {  //看图说话
                String json = tencentID_corService.getLabelPhoto(url, MethodEnum.url2byte.getMessage());
                JSONParser parser=new JSONParser();
                org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(json);
                LabelPhoto persion_id =new  LabelPhoto(data);
                String str = ObjectToJSON.getJSON(persion_id);
                log.info("给前端信息：str={}",str);
                return str;
            }
            if(action.equals(String.valueOf(ResultEnum.FACE_MERGE.getCode()))) {  //人脸融合
                FaceMerge faceMerge = new FaceMerge();
                if(mongoFile.getSize()>new Long("512000")){   //文件大小大于500KB
                    faceMerge.setRet(new Long(001));
                    faceMerge.setMsg("fail");
                    faceMerge.setImage(url);
                    log.warn("文件超出大小：size={}",mongoFile.getSize());
                }
                else {
                    faceMerge.setRet(new Long(0));
                    faceMerge.setMsg("success");
                    faceMerge.setImage(url);
                }
                return ObjectToJSON.getJSON(faceMerge);
            }
            log.warn("无任何内容");
           return null;
        }catch (Exception e){
            e.printStackTrace();
            log.error("异常");
        }
        return null;
    }
    @GetMapping("/test")
    public ModelAndView test(Map<String,Object> map) throws Exception{
        map.put("projectUrl",urlConfig.getUrl());
        return new ModelAndView("page/upload");
    }
    @GetMapping("test2")  //微信端
    public ModelAndView test2(Map<String,Object> map) throws Exception{
        //        //js jdk
        String testUrlgetAccessToken = urlConfig.getUrl()+"/test2";
        log.info("【查看是否获取到accessToken】,accessToken={}",wxMpService.getAccessToken());  //获得accessToken;
        String ticket =  wxMpService.getJsapiTicket();
        log.info("【查看是否获取到ticket】,ticket={}",ticket);  //获得accessToken;
        WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(testUrlgetAccessToken);
        log.info("【获得参数为:】appid={},nonceStr={},signature={},timestamp={},url={}",wxJsapiSignature.getAppId(),wxJsapiSignature.getNonceStr(),wxJsapiSignature.getSignature(),wxJsapiSignature.getTimestamp(),wxJsapiSignature.getUrl());
        WxJsVO wxJsVO = new WxJsVO();
        BeanUtils.copyProperties(wxJsapiSignature,wxJsVO);
        wxJsVO.setTimestamp(String.valueOf(wxJsapiSignature.getTimestamp()));
        log.info("【copy参数为:】appid={},nonceStr={},signature={},timestamp={},url={}",wxJsVO.getAppId(),wxJsVO.getNonceStr(),wxJsVO.getSignature(),wxJsVO.getTimestamp(),wxJsVO.getUrl());
        map.put("wxJsVO",wxJsVO);
        map.put("projectUrl",urlConfig.getUrl());
        return new ModelAndView("page/upload2");
    }
    @GetMapping("/file/{id}")
    public byte[] serveFile(@PathVariable String id) {    //回显图片
        byte[] fileContent = fileMongoService.getFileById(id);
        if (fileContent != null) {
            return fileContent;
        } else {
            log.error("mongoDB查询文件不存在！ id={}", id);
            return null;
        }
    }
}
