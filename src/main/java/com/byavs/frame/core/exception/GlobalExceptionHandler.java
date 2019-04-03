package com.byavs.frame.core.exception;

import com.byavs.frame.core.constants.LogCatalog;
import com.byavs.frame.core.entity.Response;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qibin.long on 2017/4/14.
 * 系统异常处理，从上到下匹配异常类型
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    private String formatException(HttpServletRequest request, String marker) {
        StringBuilder sb = new StringBuilder();
        sb.append(marker);
        sb.append(LINE_SEPARATOR);
        sb.append(String.format("       url: [%s]", request.getRequestURL())).append(LINE_SEPARATOR);
        sb.append(String.format(" server-ip: [%s:%s]", request.getLocalAddr(), request.getLocalPort())).append(LINE_SEPARATOR);
        sb.append(String.format("request-ip: [%s]", request.getRemoteHost())).append(LINE_SEPARATOR);
        return sb.toString();
    }

    @ExceptionHandler(value = ApplicationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response handle(HttpServletRequest request, ApplicationException ex) {
        logger.warn(formatException(request, LogCatalog.APP.getValue()), ex);
        return Response.failure(String.valueOf(ex.getCode()), ex.getMessage(), ex.getData());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response handle(HttpServletRequest request, MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            i++;
            sb.append(error.getDefaultMessage());
            if (i != ex.getBindingResult().getFieldErrorCount())
                sb.append("\n");
        }
        logger.warn(formatException(request, LogCatalog.SPRING_DATA_BIND.getValue()), ex);
        return Response.failure(sb.toString());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    public Response handle(HttpServletRequest request, MaxUploadSizeExceededException ex) {
        logger.warn(formatException(request, LogCatalog.UPLOAD.getValue()), ex);
        String msg = String.format("上传文件超过:%sKB", ex.getMaxUploadSize() / 1024);
        return Response.failure(msg);
    }

    @ExceptionHandler(value = MyBatisSystemException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response handle(HttpServletRequest request, MyBatisSystemException ex) {
        Throwable exCause = ex.getCause();
        if (exCause != null && exCause instanceof PersistenceException) {
            Throwable peCause = exCause.getCause();
            if (peCause != null && peCause instanceof ApplicationException) {
                return handle(request, (ApplicationException) peCause);
            }
        }
        return handleException(request, exCause);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleException(HttpServletRequest request, Throwable ex) {
        logger.error(formatException(request, LogCatalog.SYS.getValue()), ex);
        return Response.failure("很抱歉,系统发生错误,请联系客服人员!");
    }
}
