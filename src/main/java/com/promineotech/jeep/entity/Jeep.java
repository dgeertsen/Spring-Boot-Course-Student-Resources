package com.promineotech.jeep.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//Annotation that generates getters and setters for all fields, 
//as well as equals, hashCode, and toString.
@Data 
//Generates builder methods.
@Builder
//Builds default constructor.
@NoArgsConstructor
//Builds all args constuctor.
@AllArgsConstructor
public class Jeep {
  private Long modelPK;
  private JeepModel modelId;
  private String trimLevel;
  private int numDoors;
  private int wheelSize;
  private BigDecimal basePrice;
}

