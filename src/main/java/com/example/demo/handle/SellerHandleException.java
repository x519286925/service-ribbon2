package com.example.demo.handle;

import com.example.demo.VO.ResultVO;
import com.example.demo.dto.Persion_ID;
import com.example.demo.utils.ObjectToJSON;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yhn on 2017/9/6.
 */
@ControllerAdvice
public class SellerHandleException {
    @ExceptionHandler(value = FileUploadBase.SizeLimitExceededException.class)
    public String SizeLimitExceededException(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(500);  //参数错误，统一抛出
        resultVO.setMsg("错误操作");
        return ObjectToJSON.getJSON(resultVO);
    }
}
