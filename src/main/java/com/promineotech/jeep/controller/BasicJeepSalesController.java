package com.promineotech.jeep.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.jeep.entity.Jeep;
import lombok.extern.slf4j.Slf4j;

//Indicates this is a Spring REST controller that will 
//handle incoming HTTP requests and return responses.
@RestController
//Logger
@Slf4j
public class BasicJeepSalesController implements JeepSalesController {

  @Override
  //Returns a list of jeeps based on model and trim.
  public List<Jeep> fetchJeeps(String model, String trim) {
    log.debug("model={}, trim={}",model,trim);
    return null;
  }

}
