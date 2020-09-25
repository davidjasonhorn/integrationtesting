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

import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.service.ProductListTO;
import isoft.testing.utest.product.service.ProductTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hornd
 */
public class ProductConverter {

    public Product toDomain(ProductTO product) {
        
        Product p = new Product(product.getProductId(), 
                product.getProductDescription(), 
                product.getInitialQuanity(),
                product.getUnitPrice());
        return p;
    }

    public ProductTO toTransferObject(Product product) {
        
        ProductTO productTo = new ProductTO(product.getProductId(), 
                product.getProductDescription(), 
                product.getInitialQuantity(), 
                product.getTotalQuantityUsed(), 
                product.getRemainingQuantity(), 
                product.getUnitPrice());
        
        return productTo;

    }

    public ProductListTO toTransferObject(List<Product> products) {
        List<ProductTO> productTOs = new ArrayList<>();
        
        if (products != null) { 
            products.forEach((p) -> { 
                productTOs.add(toTransferObject(p));
            });
        }
        ProductListTO pList = new ProductListTO(productTOs);
        
        return pList;
    }
    
}
