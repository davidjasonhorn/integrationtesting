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
import isoft.testing.utest.product.service.InventoryTransactionTO;
import isoft.testing.utest.product.domain.ProductRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author hornd
 */
public class InventoryTransactionValidator implements ConstraintValidator<InventoryTransactionConstraint, InventoryTransactionTO> {
    
    private ProductRepository repository;

    public InventoryTransactionValidator(ProductRepository repository) {
        this.repository = repository;
    }
    
    
    @Override
    public boolean isValid(InventoryTransactionTO inventoryTransaction, ConstraintValidatorContext cvc) {

        boolean isValid = true; 
        
        Product product = repository.findByProductId(inventoryTransaction.getProductId()); 
        
        if (product != null) { 
            if (isValid) { 
                //verify that the transaction amount is valid

                Integer transactionQuantity = inventoryTransaction.getQuantity();

                Integer remaining = product.getRemainingQuantity(); 
                Integer initial = product.getInitialQuantity();
                Integer used = remaining - initial;

                if (used + transactionQuantity > initial) { 
                    isValid = false;
                }
                //a similar way to express this
                if ( remaining - transactionQuantity < 0) { 
                    isValid = false;
                }
            }            
        }

        return isValid;
            
    }

    @Override
    public void initialize(InventoryTransactionConstraint constraintAnnotation) {
    }
}
