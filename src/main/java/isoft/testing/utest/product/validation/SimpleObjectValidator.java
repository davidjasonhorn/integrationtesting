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

import isoft.testing.utest.product.domain.ProductRepository;
import isoft.testing.utest.product.service.InventoryTransactionTO;
import isoft.testing.utest.product.service.ProductTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hornd
 */
public class SimpleObjectValidator implements ValidateInventory, ValidateProduct {

    private ProductRepository productRepository;

    @Override
    public void validateInventoryTransaction(InventoryTransactionTO invt) throws ValidationError {
        List<String> violations = new ArrayList<>();

        if (invt == null) {
            violations.add("InventoryTransactionTO is null");
        } else {
            if (invt.getQuantity() == null) {
                violations.add("InventoryTransactionTO.quantity is null");
            }

            if (invt.getProductId() == null) {
                violations.add("InventoryTransactionTO.productId is null");
            }

            if (violations.isEmpty()) {
                boolean isTransValid = ValidationLogicHelper.isValidInventoryTransaction(productRepository, invt);
                if (!isTransValid) {
                    violations.add("InventoryTransactionTO is not valid");
                }
            }
        }

        if (!violations.isEmpty()) {
            throw new ValidationError(violations);
        }

    }

    @Override
    public void validateProduct(ProductTO product) throws ValidationError {
        List<String> violations = new ArrayList<>();

        if (product == null) {
            violations.add("ProductTO is null");
        } else {
            if (product.getProductId() == null) {
                violations.add("InventoryTransactionTO.productId is null");
            }

            if (product.getInitialQuantity()== null) {
                violations.add("InventoryTransactionTO.initialQuantity is null");
            }

            if (product.getUnitPrice() == null) {
                violations.add("InventoryTransactionTO.unitPrice is null");
            }

            if (!violations.isEmpty()) {
                boolean isTransValid = ValidationLogicHelper.isUniqueProductId(productRepository, product.getProductId());
                if (!isTransValid) {
                    violations.add("ProductT.productId is not unique");
                }
            }

        }
        if (!violations.isEmpty()) {
            throw new ValidationError(violations);
        }
    }

}
