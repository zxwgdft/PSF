package com.paladin.supervise.configuration;

import com.paladin.framework.exception.CommonResponseEntityExceptionHandler;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author TontoZhou
 * @since 2019/12/11
 */
@ControllerAdvice
public class SuperviseExceptionHandler extends CommonResponseEntityExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> feignExceptionHandler(FeignException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        if (ex instanceof FeignException.FeignClientException) {
            return handleExceptionInternal(ex, ex.content(), headers, HttpStatus.BAD_REQUEST, request);
        } else {
            return handleExceptionInternal(ex, ex.content(), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }
}
