package com.example.demo.dto;

import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by yhn on 2017/12/7.
 */
@Data
public class PhotoSpeak extends TencentRespon {
    private String text;
    public PhotoSpeak(){}
    public PhotoSpeak(JSONObject object)throws Exception {
        ret = (Long) object.get("ret");
        msg = (String) object.get("msg");
        String str = object.get("data").toString();
        JSONParser parser=new JSONParser();
        org.json.simple.JSONObject tojsonData =(org.json.simple.JSONObject) parser.parse(str);
        text = (String)tojsonData.get("text");
    }
}
