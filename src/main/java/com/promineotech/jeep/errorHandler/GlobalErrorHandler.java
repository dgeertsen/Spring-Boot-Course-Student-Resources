package com.promineotech.jeep.errorHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

  private enum LogStatus{
    STACK_TRACE,MESSSAGE_ONLY
  }
  /**
   * @info Catches handleConstraintViolationException 
   * and returns the exception, an exception, a messsage and BAD_REQUEST
   * 
   * @param e
   * @param webRequest
   * @return 
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
      WebRequest webRequest) {
    return createExceptionMessage(e, HttpStatus.BAD_REQUEST, webRequest,LogStatus.MESSSAGE_ONLY);
  }
  
/**
   * @info Catches handleConstraintViolationException 
   * and returns the exception, an exception, a messsage and BAD_REQUEST
   * 
   * @param e
   * @param webRequest
   * @return
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleConstraintViolationException(ConstraintViolationException e,
      WebRequest webRequest) {
    return createExceptionMessage(e, HttpStatus.BAD_REQUEST, webRequest,LogStatus.MESSSAGE_ONLY);

  }

  /**
   * @info Catches handleNoSuchElementException 
   * and returns the exception, an exception, a messsage and NOT_FOUND
   * 
   * @param e
   * @param webRequest
   * @return
   */
  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public Map<String, Object> handleNoSuchElementException(NoSuchElementException e,
      WebRequest webRequest) {

    return createExceptionMessage(e, HttpStatus.NOT_FOUND, webRequest,LogStatus.MESSSAGE_ONLY);

  }

  /**
   * @info Catches Exception 
   * and returns the exception, an exception, a messsage and INTERNAL_SERVER_ERROR
   *
   * @param e
   * @param webRequest
   * @return
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleException(Exception e, WebRequest webRequest) {
    return createExceptionMessage(e, HttpStatus.INTERNAL_SERVER_ERROR, webRequest,LogStatus.STACK_TRACE);
  }


  /**
   * @info Creates exception message for above methods
   * 
   * @param e
   * @param status
   * @param webRequest
   * @return
   */
  private Map<String, Object> createExceptionMessage(Exception e, HttpStatus status,
      WebRequest webRequest,LogStatus logStatus) {
    Map<String, Object> error = new HashMap<>();
    String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);


    if (webRequest instanceof ServletWebRequest) {
      error.put("uri", ((ServletWebRequest) webRequest).getRequest().getRequestURI());
    }

    error.put("message", e.toString());
    error.put("status code", status.value());
    error.put("timestamp", timestamp);
    error.put("reason", status.getReasonPhrase());

    if(logStatus== LogStatus.MESSSAGE_ONLY)
      log.error("Error: {}",e.toString());
    else
      log.error("Error:",e);
    
    return error;
  }


}
