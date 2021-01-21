package com.yqfsoft.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yqfsoft.util.commonresult.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {
    @SuppressWarnings("deprecation")
    public static void out(HttpServletResponse response, CommonResult r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
