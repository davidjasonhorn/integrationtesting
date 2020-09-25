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
package isoft.testing.utest.product.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hornd
 */
public class ProductTest {
    
    @Test
    public void addInventoryTransaction_one_transaction() { 
        //Arrange
        Product p = new Product(1l, "id1","my product", 100, 100, new BigDecimal("0.15"));
        InventoryTransaction iv = new InventoryTransaction(LocalDateTime.now(), 10);
        
        //Act
        p.addInventoryTransaction(iv);
        
        //Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, p.getInventoryTransactions().size()), 
                () -> Assertions.assertEquals(new Integer(10), p.getTotalQuantityUsed()), 
                () -> Assertions.assertEquals(new Integer(100), p.getInitialQuantity()), 
                () -> Assertions.assertEquals(new Integer(90), p.getRemainingQuantity()),  
                () -> Assertions.assertEquals(p, p.getInventoryTransactions().get(0).getProduct())
        );
    }

    @Test
    public void addInventoryTransaction_multiple_transactions() { 
        //Arrange
        Product p = new Product(1l, "id1","my product", 100, 100, new BigDecimal("0.15"));
        InventoryTransaction iv1 = new InventoryTransaction(LocalDateTime.now(), 10);
        InventoryTransaction iv2 = new InventoryTransaction(LocalDateTime.now(), 10);        
        InventoryTransaction iv3 = new InventoryTransaction(LocalDateTime.now(), 10);
        
        //Act
        p.addInventoryTransaction(iv1);
        p.addInventoryTransaction(iv2);
        p.addInventoryTransaction(iv3);        
        //Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(3, p.getInventoryTransactions().size()), 
                () -> Assertions.assertEquals(new Integer(30), p.getTotalQuantityUsed()), 
                () -> Assertions.assertEquals(new Integer(100), p.getInitialQuantity()), 
                () -> Assertions.assertEquals(new Integer(70), p.getRemainingQuantity())  
        );
        
        p.getInventoryTransactions().forEach((t) -> {
            assertAll("validate the list elements",
                    () -> assertEquals(10, t.getQuantity()),
                    () -> assertEquals(p, t.getProduct())
            );
        });
    }   
    
    @Test
    public void addInventoryTransaction_null_transaction() { 
        //Arrange
        Product p = new Product(1l, "id1","my product", 100, 100, new BigDecimal("0.15"));
        
        //Act & Assert
        Assertions.assertThrows(RuntimeException.class,
                () -> p.addInventoryTransaction(null))
        ;
    }        


    @Test
    public void addInventoryTransaction_no_quanity() { 
        //Arrange
        Product p = new Product(1l, "id1","my product", 100, 100, new BigDecimal("0.15"));
        InventoryTransaction iv1 = new InventoryTransaction(LocalDateTime.now(), null);
        
        //Act & Assert
        Assertions.assertThrows(RuntimeException.class,
                () -> p.addInventoryTransaction(iv1))
        ;
    } 
    
    @Test
    public void addInventoryTransaction_transactions_exceed_initialAmount() { 
        //Arrange
        Product p = new Product(1l, "id1","my product", 100, 100, new BigDecimal("0.15"));
        InventoryTransaction iv1 = new InventoryTransaction(LocalDateTime.now(), 25);
        InventoryTransaction iv2 = new InventoryTransaction(LocalDateTime.now(), 25);        
        InventoryTransaction iv3 = new InventoryTransaction(LocalDateTime.now(), 100);
        
        //Act
        p.addInventoryTransaction(iv1);
        p.addInventoryTransaction(iv2);

        //Act & Assert
        //Act & Assert
        Assertions.assertThrows(RuntimeException.class,
                () -> p.addInventoryTransaction(iv3))
        ;
    }   
    
}
