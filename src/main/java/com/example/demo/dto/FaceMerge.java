package com.example.demo.dto;

import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;

/**
 * Created by yhn on 2017/12/7.
 */
@Data
public class FaceMerge extends TencentRespon {
        private String image;
        public FaceMerge(){}
        public FaceMerge(JSONObject object)throws Exception{
            ret = (Long)object.get("ret");
            msg = (String)object.get("msg");
            String str = object.get("data").toString();
            JSONParser parser=new JSONParser();
            org.json.simple.JSONObject tojsonData =(org.json.simple.JSONObject) parser.parse(str);
            image = (String)tojsonData.get("image");
        }
}
