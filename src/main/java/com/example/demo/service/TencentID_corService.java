package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhn on 2017/12/6.
 */
public interface TencentID_corService {
    //通用接口，不想要复制代码，这是我一项原则,记得重构
    String getIdInformation(String url,String method) throws Exception;   //身份证识别
    String getPhoto_Speak(String url,String method)throws Exception; //看图说话
    String getLabelPhoto(String url,String method)throws Exception;  //图像标签识别；
    String getFaceMerge(String url,String model,String method) throws Exception;  //人脸融合
    String getPhotoForTranslate(String url,String scene,String source,String target,String method) throws Exception;  //图片翻译
}
