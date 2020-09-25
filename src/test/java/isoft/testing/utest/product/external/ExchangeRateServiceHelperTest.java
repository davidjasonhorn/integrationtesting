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
package isoft.testing.utest.product.external;

import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.service.InventoryTransactionTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
/**
 *
 * @author hornd
 */
@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceHelperTest {

    @Mock
    private ExchangeRateService exchangeRateServiceMock;
    
    @InjectMocks
    private ExchangeRateHelper exchangeRateHelper;
    
    @Test
    public void calculateSalesPrice_mock_EUR_MKD () { 
        //arrange
        BigDecimal unitPrice = new BigDecimal(".15");
        Integer quantity = 50;
        BigDecimal exhangeRate = new BigDecimal("61.55");
        BigDecimal expected = new BigDecimal("461.63");
        
        when(exchangeRateServiceMock.getCurrentExchangeRateForCurrency("EUR", "MKD")).thenReturn(exhangeRate);
        
        Product p = new Product("id", "description", quantity, unitPrice);
        InventoryTransactionTO invTo = new InventoryTransactionTO(LocalDateTime.now(), quantity, p.getProductId());
        
        //act
        BigDecimal actual = exchangeRateHelper.calculateSalePriceForCurrency(invTo, "MKD",  p);
        
        //assert
        Assertions.assertEquals(expected, actual);
    }     
    
    /**
     * 
     */
    @Test
    public void calculateSalesPrice_EUR_to_MKD () { 
        //arrange
        BigDecimal unitPrice = new BigDecimal("11.25");
        Integer quantity = 10;
        BigDecimal exchangeRate = new BigDecimal("61.55");
        
        //Bad Practice - Test contains implementation details which it should not
        BigDecimal expectedPrice = new BigDecimal("6924.38");
    
        //act
        BigDecimal actual = exchangeRateHelper.calculateSalesPrice(unitPrice, exchangeRate, quantity );
        
        //assert
        Assertions.assertEquals(expectedPrice, actual);
    }
    

    @Test
    public void calculateSalesPrice_with_small_exchange_rate () { 
        //arrange
        BigDecimal unitPrice = new BigDecimal(".15");
        Integer quantity = 2;
        BigDecimal exchangeRate = new BigDecimal("0.20");
        BigDecimal expectedPrice = new BigDecimal("0.06");

        //act
        BigDecimal actual = exchangeRateHelper.calculateSalesPrice(unitPrice, exchangeRate, quantity );
        
        //assert - Actual and expected are both 0 which is not possible.
        Assertions.assertEquals(expectedPrice, actual);
    }  
    

}
