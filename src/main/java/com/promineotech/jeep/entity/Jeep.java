package com.promineotech.jeep.entity;

import java.math.BigDecimal;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Jeep implements Comparable<Jeep> {
  private Long modelPK;
  private JeepModel modelId;
  private String trimLevel;
  private int numDoors;
  private int wheelSize;
  private BigDecimal basePrice;
  
  @JsonIgnore
  public Long getModelPk() {
    return modelPK;
  }

  @Override
  public int compareTo(Jeep that) {
    // TODO Auto-generated method stub
    // @formatter:off 
    return Comparator.comparing(Jeep::getModelId)
        .thenComparing(Jeep::getTrimLevel)
        .thenComparing(Jeep::getNumDoors)
        .compare(this, that);
    // @formatter:on 
  }
}

