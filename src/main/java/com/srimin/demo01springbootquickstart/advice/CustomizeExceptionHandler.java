package com.srimin.demo01springbootquickstart.advice;

import com.srimin.demo01springbootquickstart.dto.ResultDTO;
import com.srimin.demo01springbootquickstart.exception.CustomizeErrorCode;
import com.srimin.demo01springbootquickstart.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截我们编写代码中的异常
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handleControllerException(CustomizeException e, Model model,
                                            HttpServletRequest request) {
        if("application/json".equals(request.getContentType())){
            //返回json
            if(e instanceof CustomizeException){
                return ResultDTO.errorOf(e);
            }else {
                return ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

        }else{
            //返回页面
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }

    }

}
