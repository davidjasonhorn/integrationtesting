/*
 * Copyright 2020 hornd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package isoft.testing.utest.product.conversion;

import isoft.testing.utest.product.domain.InventoryTransaction;
import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.service.InventoryTransactionListTO;
import isoft.testing.utest.product.service.InventoryTransactionTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hornd
 */
public class InventoryTransactionConverterTest {

    private String productId = "id";
    private String productDescription = "description";
    private Integer quantity = 10;
    private BigDecimal unitPrice = new BigDecimal("0.15");
    private LocalDateTime currentTime = LocalDateTime.now();

    private InventoryTransactionConverter conv = new InventoryTransactionConverter();

    @Test
    public void toDomain_without_product_valid() {
        //Arrange
        InventoryTransactionTO transferObject = new InventoryTransactionTO(currentTime, quantity, productId);

        //Act
        InventoryTransaction domainObject = conv.toDomain(transferObject);

        //Assert
        assertAll(
                () -> assertEquals(quantity, domainObject.getQuantity()),
                () -> assertEquals(currentTime, domainObject.getTransactionDate()),
                () -> assertTrue(domainObject.getProduct() == null),
                () -> assertTrue(domainObject.getDatabaseId() == null)
        );
    }

    @Test
    public void toTransferObjectList_with_product_valid() {
        //Arrange
        Product p = new Product(productId, productDescription, quantity, unitPrice);
        InventoryTransaction iv1 = new InventoryTransaction(currentTime, 5);
        InventoryTransaction iv2 = new InventoryTransaction(currentTime, 5);
        p.addInventoryTransaction(iv1);
        p.addInventoryTransaction(iv2);

        //Act
        InventoryTransactionListTO transferObjList = conv.toTransferObject(p.getInventoryTransactions());

        assertAll("validate returned list",
                () -> assertNotNull(transferObjList.getInventoryTransactions()),
                () -> assertEquals(2, transferObjList.getInventoryTransactions().size())
        );

        //Assert
        transferObjList.getInventoryTransactions().forEach((t) -> {
            assertAll("validate the list elements",
                    () -> assertEquals(5, t.getQuantity()),
                    () -> assertEquals(currentTime, t.getTransactionDate()),
                    () -> assertEquals( productId, t.getProductId())
            );
        });
    }
    
    @Test
    public void toTransferObjectList_with_null_list() {
        //Arrange
 
        //Act
        InventoryTransactionListTO transferObjList = conv.toTransferObject(null);

        assertAll("validate returned list",
                () -> assertNotNull(transferObjList),
                () -> assertEquals(0, transferObjList.getInventoryTransactions().size())
        );

    }

    @Test
    public void toTransferObjectList_with_empty_list() {
        //Arrange
        List<InventoryTransaction> emptyList = new ArrayList<>();
        //Act
        InventoryTransactionListTO transferObjList = conv.toTransferObject(emptyList);

        assertAll("validate returned list",
                () -> assertNotNull(transferObjList),
                () -> assertEquals(0, transferObjList.getInventoryTransactions().size())
        );

    }       
       
}
