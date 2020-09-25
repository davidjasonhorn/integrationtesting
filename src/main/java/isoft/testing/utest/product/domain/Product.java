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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * An Entity Class that is persisted to the database.
 * 
 * @author hornd
 */
@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "PRD_SEQ", sequenceName = "PRD_SEQ")
    @GeneratedValue(generator = "PRD_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "PRD_DB_ID")    
    private Long databaseId;
    
    @Column(name = "PRD_ID", nullable = false)
    private String productId; 
    
    @Column(name = "PRD_DESCR", nullable = false)
    private String productDescription; 
    
    @Column(name = "PRD_INIT_QUANTITY", nullable = false)
    private Integer initialQuantity; 
    
    @Column(name = "PRD_REM_QUANTITY", nullable = false)    
    private Integer remainingQuantity;
    
    @Column(name = "PRD_UNIT_PRICE", nullable = false)    
    private BigDecimal unitPrice;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product" ,fetch = FetchType.EAGER)
    private List<InventoryTransaction> inventoryTransactions = new ArrayList<>();

    /**Create a new Product not stored in the database.
     * 
     * @param productId
     * @param productDescription
     * @param initialQuantity 
     * @param unitPrice 
     */
    public Product(String productId, String productDescription, Integer initialQuantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productDescription = productDescription;
        this.initialQuantity = initialQuantity;
        this.remainingQuantity = initialQuantity;
        this.unitPrice = unitPrice;
    }
    
    /**
     * Create a Product with all properties.
     * 
     * @param databaseId
     * @param productId
     * @param productDescription
     * @param initialQuantity
     * @param remainingQuantity 
     * @param unitPrice
     */
    public Product(Long databaseId, String productId, String productDescription, Integer initialQuantity, Integer remainingQuantity, BigDecimal unitPrice) {
        this.databaseId = databaseId;
        this.productId = productId;
        this.productDescription = productDescription;
        this.initialQuantity = initialQuantity;
        this.remainingQuantity = remainingQuantity;
        this.unitPrice = unitPrice;
    }

    public Product() {
    }

    /**
     * Adds a valid inventory transaction
     *
     * @param inventoryTransaction
     */
    public void addInventoryTransaction(InventoryTransaction inventoryTransaction) {
        //Sanity Checks
        if (inventoryTransaction == null) {
            throw new RuntimeException("InventoryTransaction cannot be null");
        }

        if (inventoryTransaction.getQuantity()== null) {
            throw new RuntimeException("InventoryTransaction quantity cannot be null");
        }
        
        inventoryTransaction.setProduct(this);
        inventoryTransactions.add(inventoryTransaction);
        updateProductQuantity(inventoryTransaction.getQuantity());

    }
    
    /**
     * Updates the Product Quantities.
     * 
     * @param amountused
     */
    public void updateProductQuantity(Integer amountused) {
        //Sanity Checks
        Integer totalQuanityUsed = getTotalQuantityUsed();
        if (totalQuanityUsed + amountused > this.initialQuantity) {
            throw new RuntimeException("Total used quantity exceeds total in stock quantity");
        }

        this.remainingQuantity = this.remainingQuantity - amountused;
    }

    /**
     * Gets the total Quantity Used
     * @return 
     */
    public Integer getTotalQuantityUsed() { 
        return this.initialQuantity - this.remainingQuantity;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public Integer getInitialQuantity() {
        return initialQuantity;
    }

    public List<InventoryTransaction> getInventoryTransactions() {
        return inventoryTransactions;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    protected void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    protected void setInitialQuantity(Integer initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    protected void setInventoryTransactions(List<InventoryTransaction> inventoryTransactions) {
        this.inventoryTransactions = inventoryTransactions;
    }

    protected void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    protected void setProductId(String productId) {
        this.productId = productId;
    }

    protected void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    protected void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.productId);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "databaseId=" + databaseId + ", productId=" + productId + ", productDescription=" + productDescription + ", initialQuantity=" + initialQuantity + ", remainingQuantity=" + remainingQuantity + ", unitPrice=" + unitPrice + '}';
    }

    
}
