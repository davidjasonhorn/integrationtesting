package isoft.testing.utest.samples.newstuff;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 *
 * @author hornd
 */
public class RestAssuredTest {

    @Test
    void testEchoService() {
        String key = "foo";
        String value = "bar";
        given().when().get("http://locahost:7001/echo.jsontest.com/" + key + "/" + value)
                .then().assertThat().statusCode(200).body(key, 
                equalTo(value));
    }
    
    
 
}
