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

import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.service.ProductListTO;
import isoft.testing.utest.product.service.ProductTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hornd
 */
public class ProductConverterTest {

    ProductConverter conv = new ProductConverter();

    @Test
    public void toDomain_valid() {
        //arrange 
        BigDecimal unitPrice = new BigDecimal("100.2352");
        ProductTO p = new ProductTO("id", "description", 100, 0, 100, unitPrice);

        //act
        Product prod = conv.toDomain(p);

        //assert 
        Assertions.assertAll("verify Product domain",
                () -> Assertions.assertEquals("id", prod.getProductId()),
                () -> Assertions.assertEquals("description", prod.getProductDescription()),
                () -> Assertions.assertEquals(new Integer(100), prod.getInitialQuantity()),
                () -> Assertions.assertEquals(new Integer(0), prod.getTotalQuantityUsed()),
                () -> Assertions.assertEquals(new Integer(100), prod.getRemainingQuantity()),
                () -> Assertions.assertEquals(new Integer(100), prod.getRemainingQuantity()),
                () -> Assertions.assertEquals(unitPrice, prod.getUnitPrice()),
                () -> Assertions.assertEquals(0, prod.getInventoryTransactions().size())
        );
    }
    
    @Test
    public void toTransferObject_valid() {
        //arrange 
        BigDecimal unitPrice = new BigDecimal("100.2352");
        Product p = new Product(1l,"id", "description", 100,100, unitPrice);

        //act
        ProductTO prod = conv.toTransferObject(p);

        //assert 
        Assertions.assertAll("verify Product domain",
                () -> Assertions.assertEquals("id", prod.getProductId()),
                () -> Assertions.assertEquals("description", prod.getProductDescription()),
                () -> Assertions.assertEquals(new Integer(100), prod.getInitialQuantity()),
                () -> Assertions.assertEquals(new Integer(0), prod.getTotalUsedQuantity()),
                () -> Assertions.assertEquals(new Integer(100), prod.getRemainingQuantity()),
                () -> Assertions.assertEquals(unitPrice, prod.getUnitPrice())
        );

    }

    @Test
    public void toTransferObject_null_list() {
        //arrange 
        List<Product> products = null;
        //act
        ProductListTO prod = conv.toTransferObject(products);

        //assert 
        Assertions.assertAll("verify Product TO List is empty",
                () -> Assertions.assertNotNull(prod),
                () -> Assertions.assertEquals(0, prod.getProducts().size())
        );

    } 
    
    
    @Test
    public void toTransferObject_empty_list() {
        //arrange 
        List<Product> products = new ArrayList<>();
        //act
        ProductListTO prod = conv.toTransferObject(products);

        //assert 
        Assertions.assertAll("verify Product TO List is empty",
                () -> Assertions.assertNotNull(prod),
                () -> Assertions.assertEquals(0, prod.getProducts().size())
        );

    }       
    
    @Test
    public void toTransferObject_valid_list() {
        //arrange 
        BigDecimal unitPrice = new BigDecimal("100.2352");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(1l, "id1", "descr1", 100, 100,unitPrice);
        Product p2 = new Product(2l, "id2", "descr2", 100, 100,unitPrice);
        products.add(p1);
        products.add(p2);
        
        //act
        ProductListTO prod = conv.toTransferObject(products);

        //Assert 
        Assertions.assertAll("Verify List",
                () -> Assertions.assertNotNull(prod),
                () -> Assertions.assertEquals(2, prod.getProducts().size())
                
        );
        
        //Assert
        prod.getProducts().forEach((t) -> {
            assertAll("validate the list elements",
                    () -> assertEquals(100, t.getInitialQuantity()),
                    () -> assertEquals(100, t.getRemainingQuantity()),
                    () -> assertEquals( unitPrice, t.getUnitPrice())
            );
        });

    }      
    
}
