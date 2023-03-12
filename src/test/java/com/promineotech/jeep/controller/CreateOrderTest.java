package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import com.promineotech.jeep.controller.support.BaseTest;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;

//This is a SpringBoot intergration test.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
// Load SQL Scripts that will be executed
@Sql(
   scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
       "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
   config = @SqlConfig(encoding = "utf-8"))




class CreateOrderTest extends BaseTest {
  
  @Autowired 
  private JdbcTemplate jdbcTemplate;
  
  
  @LocalServerPort
  private int serverPort;
  /**
   * 
   */
  @Test
  void testCreateOrderReturnsSuccess201() {
    // Given: an order a JSON
    String body = createOrderBody();
    
    //Counts the num of rows in repsective tables
    int numRowsOrders = JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders");
    int numRowsOptions = JdbcTestUtils.countRowsInTable(jdbcTemplate, "order_options");
    
    //HTTP Request Body will containt JSON Data
    HttpHeaders headers=new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<String> bodyEntity = new HttpEntity<>(body,headers); 
    
    //Indicates the location of server endpoint
    String uri =
        String.format("http://localhost:%d/orders", serverPort);
    // When: an order is sent
    ResponseEntity<Order> response = getRestTemplate().exchange(uri, HttpMethod.POST, bodyEntity, Order.class);
    
    // Then: a 201 Status is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    
    // And: the returned order is correct
    assertThat(response.getBody()).isNotNull();
    
    Order order =response.getBody();
    assertThat(order.getCustomer().getCustomerId()).isEqualTo("MORISON_LINA");
    assertThat(order.getModel().getModelId()).isEqualTo(JeepModel.WRANGLER);
    assertThat(order.getModel().getTrimLevel()).isEqualTo("Sport Altitude");
    assertThat(order.getModel().getNumDoors()).isEqualTo(4);
    assertThat(order.getColor().getColorId()).isEqualTo("EXT_NACHO");
    assertThat(order.getEngine().getEngineId()).isEqualTo("2_0_TURBO");
    assertThat(order.getTire().getTireId()).isEqualTo("35_TOYO");
    assertThat(order.getOptions()).hasSize(6);
    
    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders"))
      .isEqualTo(numRowsOrders+1);
    
    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "order_options"))
      .isEqualTo(numRowsOptions+6);
    
    
    
  }
  
    //Defines a method named which returns a 
    //string representation of a JSON object that specifies the details of a Jeep order.
    protected String createOrderBody() {

    //@ formatter:off 
      return "{\n"
          + "  \"customer\":\"MORISON_LINA\",\n"
          + "  \"model\":\"WRANGLER\",\n"
          + "  \"trim\":\"Sport Altitude\",\n"
          + "  \"doors\":4,\n"
          + "  \"color\":\"EXT_NACHO\",\n"
          + "  \"engine\":\"2_0_TURBO\",\n"
          + "  \"tire\":\"35_TOYO\",\n"
          + "  \"options\":[\n"
          + "    \"DOOR_QUAD_4\",\n"
          + "    \"EXT_AEV_LIFT\",\n"
          + "    \"EXT_WARN_WINCH\",\n"
          + "    \"EXT_WARN_BUMPER_FRONT\",\n"
          + "    \"EXT_WARN_BUMPER_REAR\",\n"
          + "    \"EXT_ARB_COMPRESSOR\"\n"
          + "  ]\n"
          + "}";    
    //@ formatter:on 
    }
  }

