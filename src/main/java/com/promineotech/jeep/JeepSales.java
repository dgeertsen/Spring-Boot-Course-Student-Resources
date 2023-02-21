package com.promineotech.jeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //// This annotation indicates that this class is a SpringBoot app

public class JeepSales {

  public static void main(String[] args) {
    //This method starts the application.
    //The two arguments are the class that contains the  
    //main method, and the second is any command-line arguments.
    SpringApplication.run(JeepSales.class, args);

  }

}
