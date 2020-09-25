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
package isoft.testing.utest.product.validation;

import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.domain.ProductRepository;
import isoft.testing.utest.product.service.InventoryTransactionTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hornd
 */
@ExtendWith(MockitoExtension.class) // run initMocks - so setsup all @Mock classes automatically
public class ValidationLogicHelperTest {

    private String foundProductId = "found";
    private String description = "description";
    private Integer initalQuanity = 100;
    private Integer remainingQuantity = 100;
    private BigDecimal unitPrice = new BigDecimal("0.13");
    private Product toReturn = new Product(1l, foundProductId, description, initalQuanity, remainingQuantity, unitPrice);
    
    private String notFoundProductId = "notfound";
    
    //create a mock to use in the tests
    @Mock
    private ProductRepository productRepositoryMock;

    @Test
    public void isUniqueProductId_product_id_not_unique() {
        //arrrange
        when(productRepositoryMock.findByProductId(foundProductId)).thenReturn(toReturn);

        //act 
        boolean isValid = ValidationLogicHelper.isUniqueProductId(productRepositoryMock, foundProductId);

        //assert
        assertFalse(isValid, "Product Id is found");
    }
    
    
    @Test
    public void isUniqueProductId_product_id_is_unique() {
        //arrrange
        when(productRepositoryMock.findByProductId(notFoundProductId)).thenReturn(null);

        //act 
        boolean isValid = ValidationLogicHelper.isUniqueProductId(productRepositoryMock, notFoundProductId);

        //assert
        assertTrue(isValid, "Product Id not found");
    }

    @Test
    public void isValidInventoryTransaction_transaction_no_product_found() {
        //arrrange
        when(productRepositoryMock.findByProductId(notFoundProductId)).thenReturn(null);
        InventoryTransactionTO inv = new InventoryTransactionTO(LocalDateTime.now(), remainingQuantity, notFoundProductId);
        
        //act 
        boolean isValid = ValidationLogicHelper.isValidInventoryTransaction(productRepositoryMock, inv);

        //assert
        assertFalse(isValid, "Product Id not found");
    } 

    @Test
    public void isValidInventoryTransaction_transaction_amount_is_null() {
        //arrrange
        when(productRepositoryMock.findByProductId(foundProductId)).thenReturn(toReturn);
        InventoryTransactionTO inv = new InventoryTransactionTO(LocalDateTime.now(), null, foundProductId);
        
        //act 
        boolean isValid = ValidationLogicHelper.isValidInventoryTransaction(productRepositoryMock, inv);

        //assert
        assertFalse(isValid, "Transaction amount should not be null");
    }
    
    
    @Test
    public void isValidInventoryTransaction_transaction_amount_is_negative() {
        //arrrange
        when(productRepositoryMock.findByProductId(foundProductId)).thenReturn(toReturn);
        InventoryTransactionTO inv = new InventoryTransactionTO(LocalDateTime.now(), -1, foundProductId);
        
        //act 
        boolean isValid = ValidationLogicHelper.isValidInventoryTransaction(productRepositoryMock, inv);

        //assert
        assertFalse(isValid, "Transaction amount cannot be less than 0");
    } 
    
    @Test
    public void isValidInventoryTransaction_transaction_amount_is_exceeds_remainingQuantity() {
        //arrrange
        when(productRepositoryMock.findByProductId(foundProductId)).thenReturn(toReturn);
        InventoryTransactionTO inv = new InventoryTransactionTO(LocalDateTime.now(), remainingQuantity+1, foundProductId);
        
        //act 
        boolean isValid = ValidationLogicHelper.isValidInventoryTransaction(productRepositoryMock, inv);

        //assert
        assertFalse(isValid, "Transaction amount should not exceed remaining qunity");
    }  
    
    @Test
    public void isValidInventoryTransaction_transaction_amount_balance_0() {
        //arrrange
        when(productRepositoryMock.findByProductId(foundProductId)).thenReturn(toReturn);
        InventoryTransactionTO inv = new InventoryTransactionTO(LocalDateTime.now(), remainingQuantity, foundProductId);
        
        //act 
        boolean isValid = ValidationLogicHelper.isValidInventoryTransaction(productRepositoryMock, inv);

        //assert
        assertTrue(isValid, "Transaction amount is valid");
    }      
    
}
