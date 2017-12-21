package com.example.demo.service;

import com.example.demo.config.FeignMultipartSupportConfig;
import com.example.demo.entity.File;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yhn on 2017/12/5.
 */
@FeignClient(value = "partime-meow-nan",configuration = FeignMultipartSupportConfig.class)
public interface FileMongoService {
    @PostMapping(value = "/File_save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    File save(@RequestPart(value = "file") MultipartFile file);
    @PostMapping("/File_getFileById")
    byte[] getFileById(@RequestParam("id") String id);
}
