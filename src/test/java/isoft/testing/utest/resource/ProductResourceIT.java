package isoft.testing.utest.resource;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hornd
 */
public class ProductResourceIT {

    private static String HOST_PORT = "http://localhost:8080";
    private static String PRODUCT_URI = "/product";
    private static String PRODUCT_ALL_URI = "/all";
    private static String PERFORM_TRANS_URI = "/trans";

    private String productId = "id4";
    private String description = "product description";
    private Integer initialQuantity = 100;
    private BigDecimal unitPrice = new BigDecimal("11.23");
    private Integer transctionQuantity = 10;

    @Test
    public void postProduct_test_valid() {
        //Arrange
        JSONObject jo = createProductJSON(productId, description, initialQuantity, unitPrice);
        RestAssured.baseURI = HOST_PORT;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(jo.toString());

        //Act
        Response response = request.post(PRODUCT_URI);

        //Assert
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(201, statusCode);
    }

    @Test
    public void postPerformTransaction_valid() {
        //Arrange
        JSONObject jo = createTransactionJSON(productId, transctionQuantity);
        RestAssured.baseURI = HOST_PORT;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(jo.toString());

        //Act
        Response response = request.post(PRODUCT_URI + PERFORM_TRANS_URI);

        //Assert
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(201, statusCode);
    }
    
    @Nested
    public class loadDataTestCases { 
        @Test
        public void getProductByID_valid() {
            RestAssured.baseURI = HOST_PORT;

            RequestSpecification request = RestAssured.given();
            Response response = request.get(PRODUCT_URI + "/" + productId);
            JSONObject js = new JSONObject(response.asString());

            int statusCode = response.getStatusCode();
            Assertions.assertEquals(200, statusCode);
            Assertions.assertEquals(productId, js.getString("productId"));
            Assertions.assertEquals(description, js.getString("productDescription"));
            Assertions.assertEquals(initialQuantity, js.getInt("initialQuanity"));
//            Assertions.assertEquals(initialQuantity, js.getInt("remainginQuantity"));
//            Assertions.assertEquals(0, js.getInt("totalUsedQuantity"));
            Assertions.assertEquals(unitPrice, js.getBigDecimal("unitPrice").setScale(2, RoundingMode.HALF_UP));
        }

        @Test
        public void getProductAll_valid() {
            RestAssured.baseURI = HOST_PORT;

            RequestSpecification request = RestAssured.given();
            Response response = request.get(PRODUCT_URI + PRODUCT_ALL_URI);
            JSONObject js = new JSONObject(response.asString());

            int statusCode = response.getStatusCode();

            Assertions.assertEquals(200, statusCode);
            Assertions.assertTrue(js.getJSONArray("products").length() >= 1);
        }

        @Test
        public void getTransactionsForProductId_valid() {
            RestAssured.baseURI = HOST_PORT;

            RequestSpecification request = RestAssured.given();
            Response response = request.get(PRODUCT_URI + "/" + productId + "/inv");
            JSONObject js = new JSONObject(response.asString());

            int statusCode = response.getStatusCode();

            Assertions.assertEquals(200, statusCode);
            Assertions.assertTrue(js.getJSONArray("inventoryTransactions").length() >= 1);
        }
    }


    private JSONObject createProductJSON(String productId, String description, Integer initialQuantity,
            BigDecimal unitPrice) {

        JSONObject js = new JSONObject();
        js.put("productId", productId);
        js.put("productDescription", description);
        js.put("initialQuanity", initialQuantity);
        js.put("totalUsedQuantity", 0);
        js.put("remainginQuantity", initialQuantity);
        js.put("unitPrice", unitPrice.toString());

        return js;
    }

    private JSONObject createTransactionJSON(String productId, Integer transctionQuantity) {
        JSONObject js = new JSONObject();
        js.put("productId", productId);
        js.put("quantity", transctionQuantity);
        return js;
    }

}
