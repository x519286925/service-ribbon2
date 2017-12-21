package com.example.demo.dto;

import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;

/**
 * Created by yhn on 2017/12/16.
 */
@Data
public class PhotoForTranslate {
    private Long ret;
    private String msg;
    private List<PhotoForTranslateData> image_recordsList;
    public PhotoForTranslate(JSONObject object)throws Exception{
        ret = (Long)object.get("ret");
        msg = (String)object.get("msg");
        String str = object.get("data").toString();
        System.out.println(str);
        JSONParser parser=new JSONParser();
        org.json.simple.JSONObject tojsonData =(org.json.simple.JSONObject) parser.parse(str);
        image_recordsList = (List<PhotoForTranslateData>) tojsonData.get("image_records");
    }
}
