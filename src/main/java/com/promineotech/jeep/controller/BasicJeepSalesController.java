package com.promineotech.jeep.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.jeep.entity.Jeep;

//Indicates this is a Spring REST controller that will 
//handle incoming HTTP requests and return responses.
@RestController 
public class BasicJeepSalesController implements JeepSalesController {

  @Override
  //Returns a list of jeeps based on model and trim.
  public List<Jeep> fetchJeeps(String model, String trim) {
    return null;
  }

}
