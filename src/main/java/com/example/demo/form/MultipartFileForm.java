package com.example.demo.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
/**
 * Created by yhn on 2017/12/6.
 */
@Data
public class MultipartFileForm {
    private MultipartFile file;
}
