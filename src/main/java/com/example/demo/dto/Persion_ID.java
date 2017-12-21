package com.example.demo.dto;
import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.Serializable;
import java.util.List;
/**
 * Created by yhn on 2017/12/6.
 */
@Data
public class Persion_ID extends TencentRespon implements Serializable {
    private PersonDataDTO data; //个人信息集合
    public Persion_ID(){}
    public Persion_ID(JSONObject object)throws Exception{
        ret = (Long)object.get("ret");
        msg = (String)object.get("msg");
        String str = object.get("data").toString();
        JSONParser parser=new JSONParser();
        org.json.simple.JSONObject tojsonData =(org.json.simple.JSONObject) parser.parse(str);
        data = new PersonDataDTO(tojsonData);
//        JSONParser parser=new JSONParser();
//        org.json.simple.JSONObject data2 = (org.json.simple.JSONObject)parser.parse(data);
    }
}
