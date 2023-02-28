package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

// This is a SpringBoot intergration test.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
// Load SQL Scripts that will be executed
@Sql(scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
    "classpath:flyway/migrations/V1.1__Jeep_Data.sql"}, config = @SqlConfig(encoding = "utf-8"))

class FetchJeepTest {

  // Inject an instance of the TestRestTemplate class into this class.
  @Autowired
  private TestRestTemplate restTemplate;
  // Inject the local server port number into this class.
  @LocalServerPort
  private int serverPort;

  /*
   * //Build Expected Test
   * 
   * @Autowired private JdbcTemplate jdbcTemplate; //Test how many rows are in h2 db
   * 
   * @Test void testDb() { int numRows=JdbcTestUtils.countRowsInTable(jdbcTemplate, "customers");
   * System.out.println("num="+numRows); }
   * 
   * 
   * @Disabled
   */
  @Test
  void testThatJeepsAreReturendWhenAValidJeepModelAndTrimAreSupplied() {
    // Given: a valid model, trim and URI
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Sport";
    String uri =
        String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);

    // When: a connections is made to the URI
    ResponseEntity<List<Jeep>> response =
        restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    // Then: a success (OK - 200) status code is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    // And: the actual list returned is the same as the expected list. NOT WORKING DUE TO DAO LAYER
    List<Jeep> actual = response.getBody();
    List<Jeep> expected = buildExpected();
    actual.forEach(jeep -> jeep.setModelPK(null));
    assertThat(actual).isEqualTo(expected);

  }
  @Test
  void testThatAnErrorMessageIsReturnedWhenInvalidTrimIsUsed() {
    // Given: a valid model, trim and URI
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Invalid value";
    String uri =
        String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);

    // When: a connections is made to the URI
    ResponseEntity<Map<String, Object>> response =
        restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    // Then: a not found (404) status is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    // And: an error message is returned
    Map<String, Object> error = response.getBody();
    
    // @formatter:off
    assertThat(error)
    .containsKey("message")
    .containsEntry("status code",HttpStatus.NOT_FOUND.value())
    .containsEntry("uri", "/jeeps")
    .containsKey("timestamp")
    .containsEntry("reason",HttpStatus.NOT_FOUND.getReasonPhrase());
    // @formatter:on

  }

  
  protected List<Jeep> buildExpected() {
    List<Jeep> list = new LinkedList<>();
    // @formatter:off
    list.add(Jeep.builder()
        .modelId(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(4)
        .wheelSize(17)
        .basePrice(new BigDecimal("31975.00"))
        .build());
    
    list.add(Jeep.builder()
        .modelId(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(2)
        .wheelSize(17)
        .basePrice(new BigDecimal("28475.00"))
        .build());
    
    
    // @formatter:on
    
    Collections.sort(list);
    return list;

  }
}


