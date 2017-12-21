package com.example.demo.dto;

import lombok.Data;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * Created by yhn on 2017/12/6.
 */
@Data
public class PersonDataDTO implements Serializable{
    private String name;     //名字0
    private String sex;      //性别1
    private String nation;  //民族2
    private String birth;  //生日3
    private String address;  //住址4
    private String id;  //身份证识别5
//    private String frontimage;  //二进制数据过大。不需要采用
//    private String authority;    //权威-- 背面才有用。
//    private String valid_date;  //有效日期   -- 背面才有用。
//    private String backimage;
    public PersonDataDTO(JSONObject object){
        name = (String)object.get("name");
        sex = (String)object.get("sex");
        nation = (String)object.get("nation");
        birth = (String)object.get("birth");
        address = (String)object.get("address");
        id = (String)object.get("id");
    }
}
