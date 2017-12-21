package com.example.demo.VO;

import lombok.Data;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yhn on 2017/11/26.
 */
@Data
public class PageVO{
    private List<?> content;
    private Long totalPages;
    private Long size;
    private Long page;
    public PageVO(){
    }
//    public PageVO(JSONObject object){
//        content = (List<?>)object.get("content");
//        totalPages = (Long)object.get("totalPages");
//        size = (Long)object.get("size");
//    }
}
