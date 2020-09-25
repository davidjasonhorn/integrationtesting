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
import javax.inject.Inject;

/**
 *
 * @author hornd
 */
public class ExchangeRateHelper {

    private static final String APP_DEFAULT_CURRENCY = "EUR";
    
    @Inject
    private ExchangeRateService exchangeRateService;

    /**
     * 
     * @param inventoryTransaction
     * @param currencyCode
     * @param product
     * @return 
     */
    public BigDecimal calculateSalePriceForCurrency(InventoryTransactionTO inventoryTransaction, String currencyCode, Product product) {
        BigDecimal rate;
        if (currencyCode.equals(APP_DEFAULT_CURRENCY)) {
             rate = BigDecimal.ONE;
        } else {
            rate = exchangeRateService.getCurrentExchangeRateForCurrency(APP_DEFAULT_CURRENCY, currencyCode);
        }

        return calculateSalesPrice(product.getUnitPrice(),rate, inventoryTransaction.getQuantity() );

    }
    
    /**
     * 
     * @param unitPrice
     * @param exchangeRate
     * @param quantity
     * @return 
     */
    public BigDecimal calculateSalesPrice(BigDecimal unitPrice, BigDecimal exchangeRate, Integer quantity) { 
        BigDecimal requestedQuantity = new BigDecimal(quantity);
        
        BigDecimal salesPrice = unitPrice.multiply(requestedQuantity).multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
    
        return salesPrice;
    }
}
