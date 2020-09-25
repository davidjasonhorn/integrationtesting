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
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import isoft.testing.utest.product.validation.UniqueProductConstraint;

/**
 *
 * @author hornd
 */
public class ProductTO {

    @NotNull
    @UniqueProductConstraint
    private String productId;

    @NotNull
    @Size(min = 10, max = 255)
    private String productDescription;

    @NotNull
    private Integer initialQuanity;

    private Integer totalUsedQuantity;

    private Integer remainginQuantity;
    
    @NotNull
    private BigDecimal unitPrice;

    /**
     * 
     * @param productId
     * @param productDescription
     * @param initialQuanity
     * @param totalUsedQuantity
     * @param remainginQuantity
     * @param unitPrice 
     */
    public ProductTO(String productId, String productDescription, Integer initialQuanity, Integer totalUsedQuantity, Integer remainginQuantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productDescription = productDescription;
        this.initialQuanity = initialQuanity;
        this.totalUsedQuantity = totalUsedQuantity;
        this.remainginQuantity = remainginQuantity;
        this.unitPrice = unitPrice;
    }

    

    public Integer getTotalUsedQuantity() {
        return totalUsedQuantity;
    }

    public Integer getInitialQuanity() {
        return initialQuanity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getRemainginQuantity() {
        return remainginQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.productId);
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
        final ProductTO other = (ProductTO) obj;
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productDescription=" + productDescription + ", initialQuanity=" + initialQuanity + ", totalUsedQuantity=" + totalUsedQuantity + ", remainginQuantity=" + remainginQuantity + ", unitPrice=" + unitPrice + '}';
    }

}
