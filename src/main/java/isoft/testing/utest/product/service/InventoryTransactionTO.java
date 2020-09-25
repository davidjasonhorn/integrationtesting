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
package isoft.testing.utest.product.service;

import isoft.testing.utest.product.domain.InventoryTransaction;
import isoft.testing.utest.product.validation.InventoryTransactionConstraint;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Transfer object representing a {@link InventoryTransaction}.
 * 
 * @author hornd
 */
@InventoryTransactionConstraint
public class InventoryTransactionTO {
    
    private final LocalDateTime transactionDate;

    @NotNull
    private final Integer quantity; 

    @NotNull
    private final String productId;

    public InventoryTransactionTO(LocalDateTime transactionDate, Integer quantity, String productId) {
        this.transactionDate = (transactionDate == null ? LocalDateTime.now() : transactionDate);
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.transactionDate);
        hash = 97 * hash + Objects.hashCode(this.quantity);
        hash = 97 * hash + Objects.hashCode(this.productId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InventoryTransactionTO other = (InventoryTransactionTO) obj;
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.transactionDate, other.transactionDate)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        return true;
    }
    
}
