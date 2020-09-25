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

/**
 * The validation logic for custom validations
 *
 * @author hornd
 */
public class ValidationLogicHelper {

    /**
     * Verifies that the product ID does not already exist in the database.
     *
     * @param repository
     * @param productId
     * @return
     */
    public static boolean isUniqueProductId(ProductRepository repository, String productId) {

        Product product = repository.findByProductId(productId);

        return product == null;
    }

    /**
     * Performs various validations on the {@link InventoryTransactionTO}. 
     * The {@link InventoryTransactionTO#productId} should exist in the
     * database, otherwise return invalid. 
     * The {@link InventoryTransactionTO#quantity} should be less than or equal to
     * the {@link Product#remainingQuantity}. 
     * The {@link InventoryTransactionTO#quantity} should not be null or less than or equal to zero.
     *
     * @param repository
     * @param inventoryTransaction
     * @return
     */
    public static boolean isValidInventoryTransaction(ProductRepository repository, InventoryTransactionTO inventoryTransaction) {

        boolean isValid = true;

        Product product = repository.findByProductId(inventoryTransaction.getProductId());
        
        //if the product is found
        if (product != null) {
            //verify that the transaction amount is valid

            Integer transactionQuantity = inventoryTransaction.getQuantity();
            if (transactionQuantity != null && transactionQuantity > 0) {
                Integer remaining = product.getRemainingQuantity();

                //a similar way to express this
                if ((remaining - transactionQuantity) < 0) {
                    isValid = false;
                }

            } else { 
                isValid = false;
            }
        } else { 
            isValid = false;
        }

        return isValid;

    }

}
