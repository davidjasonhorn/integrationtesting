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

import java.math.BigDecimal;

/**
 *
 * @author hornd
 */
public interface ProductService {
    
    /** Add a product to inventory
     * 
     * @param product 
     */
    public void addProduct(ProductTO product); 
    
    /**
     * Perform a transaction to inventory
     * 
     * @param inventoryTransaction 
     */
    public void performTransaction(InventoryTransactionTO inventoryTransaction);
    
    /**
     * For a transaction, determine the sale price based on a currency
     * 
     * @param inventoryTransaction
     * @param currencyCode
     * @return 
     */
    public BigDecimal getSalePrice(InventoryTransactionTO inventoryTransaction, String currencyCode);
    
    /**
     * Load a product and it's inventory transactions by a product Id.
     * 
     * @param productId
     * @return 
     */
    public ProductTO loadProductByProductId(String productId);
    
    /**
     * Loads Transactions for a specific product.
     * 
     * @param productId
     * @return 
     */
    public InventoryTransactionListTO loadTransactionsForProductId(String productId);
    
    /**
     * Loads all products
     * @return 
     */
    public ProductListTO loadAllProducts();
}
