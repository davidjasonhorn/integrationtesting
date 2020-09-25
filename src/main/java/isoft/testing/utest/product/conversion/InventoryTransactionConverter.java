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
import isoft.testing.utest.product.service.InventoryTransactionListTO;
import isoft.testing.utest.product.service.InventoryTransactionTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hornd
 */
public class InventoryTransactionConverter {

    /**
     * 
     * @param inventoryTransaction
     * @return 
     */
    public InventoryTransaction toDomain(InventoryTransactionTO inventoryTransaction) {
    InventoryTransaction inv = new InventoryTransaction(inventoryTransaction.getTransactionDate(),
        inventoryTransaction.getQuantity());
    
    return inv;
    }

    /**
     * 
     * @param inventoryTransactions
     * @return 
     */
    public InventoryTransactionListTO toTransferObject(List<InventoryTransaction> inventoryTransactions) {
        List<InventoryTransactionTO> inventoryTransactionTOs = new ArrayList<>();

        if (inventoryTransactions != null) {
            inventoryTransactions.forEach((i) -> { 
                inventoryTransactionTOs.add(toTransferObject(i));
            });
        } 
        InventoryTransactionListTO invList = new InventoryTransactionListTO(inventoryTransactionTOs);
        return invList;
    }
        
    private InventoryTransactionTO toTransferObject(InventoryTransaction inventoryTransaction) {
    InventoryTransactionTO inv = new InventoryTransactionTO(inventoryTransaction.getTransactionDate(),
        inventoryTransaction.getQuantity(), inventoryTransaction.getProduct().getProductId());
    
    return inv;
    }
    
}
