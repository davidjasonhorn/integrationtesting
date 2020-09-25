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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * An Entity Class for Inventory Transactions
 * @author hornd
 */
@Entity
@Table(name = "INVENTORY_TRANSACTION")
public class InventoryTransaction implements Serializable {
    
    @Id
    @SequenceGenerator(name = "INV_SEQ", sequenceName = "INV_SEQ")
    @GeneratedValue(generator = "INV_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "INV_ID")   
    private Long databaseId; 
    
    @Column(name = "INV_TRANS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar transactionDate;

    @Column(name = "INV_TRANS_QUANTITY", nullable = false)    
    private Integer quantity; 
    
    @ManyToOne(optional=false)
    @JoinColumn(name = "databaseId")
    private Product product; 

    public InventoryTransaction(LocalDateTime transactionDate, Integer quantity) {
        this.transactionDate = GregorianCalendar.from(transactionDate.atZone(ZoneId.systemDefault()));;
        this.quantity = quantity;
    }

    public InventoryTransaction() {
    }
    
    public Long getDatabaseId() {
        return databaseId;
    }

    protected void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    
    public Integer getQuantity() {
        return quantity;
    }

    protected void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    
    public LocalDateTime getTransactionDate() {
        return LocalDateTime.ofInstant(transactionDate.toInstant(), transactionDate.getTimeZone().toZoneId());
    }

    protected void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = GregorianCalendar.from(transactionDate.atZone(ZoneId.systemDefault()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.databaseId);
        hash = 11 * hash + Objects.hashCode(this.transactionDate);
        hash = 11 * hash + Objects.hashCode(this.quantity);
        hash = 11 * hash + Objects.hashCode(this.product);
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
        final InventoryTransaction other = (InventoryTransaction) obj;
        if (!Objects.equals(this.databaseId, other.databaseId)) {
            return false;
        }
        if (!Objects.equals(this.transactionDate, other.transactionDate)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InventoryTransaction{" + "databaseId=" + databaseId + ", transactionDate=" + transactionDate + ", quantity=" + quantity + ", product=" + product + '}';
    }
    
}
