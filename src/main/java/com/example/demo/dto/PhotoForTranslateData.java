package com.example.demo.dto;

import lombok.Data;
import org.json.simple.JSONObject;

/**
 * Created by yhn on 2017/12/16.
 */
@Data
public class PhotoForTranslateData {
    private String source_text;
    private String target_text;
    private Long x;
    private Long y;
    private Long width;
    private Long height;
}
