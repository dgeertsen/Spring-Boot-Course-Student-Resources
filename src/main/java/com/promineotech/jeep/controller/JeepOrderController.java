package com.promineotech.jeep.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/orders")
@OpenAPIDefinition(info = @Info(title = "Jeep Order Service"),
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})

public interface JeepOrderController {


  // API Documentation for the four possible outcomes
  //@formatter:off
  @Operation(
  summary="Create an order for a jeep",
  description="Returns the created Jeep",
  responses= {
      @ApiResponse(
          responseCode="201", 
          description="Created Jeep is returned", 
          content= @Content(mediaType="application/json",
          schema=@Schema(implementation=Order.class))),
      @ApiResponse(
          responseCode="400", 
          description="Request parameters are invalid", 
          content= @Content(mediaType="application/json")),
      @ApiResponse(
          responseCode="404", 
          description="A Jeep was not found", 
          content= @Content(mediaType="application/json")),
      @ApiResponse(
          responseCode="500", 
          description="Unplanned error occured", 
          content= @Content(mediaType="application/json"))
  },
    //Paramter Annotations
    parameters={
      @Parameter(
          name="orderRequest", 
          required=true,
          description="The Order as JSON")
      
    }
  //@formatter:on
  )



  @PostMapping // HTTP POST Request to this method
  @ResponseStatus(code = HttpStatus.CREATED) // sets the HTTP response status code to 201 (CREATED)
  // The @RequestBody annotation specifies that the OrderRequest object will be obtained from the
  // request body of the HTTP POST method that is used to create the order
  Order createOrder(@Valid @RequestBody OrderRequest orderRequest);


}
