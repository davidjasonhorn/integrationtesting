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
package isoft.testing.utest.samples;

import isoft.testing.utest.product.service.ProductTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;

/**
 *
 * @author hornd
 */
@Tag("example-test")
public class JUnitGroupedAssertionTest {
    private ProductTO product;
    
    @BeforeEach
    public void setUp() { 
        product = new ProductTO("productId", 
                "Pencil no.2 with Eraser", 
                100, 
                0,
                100,
                new BigDecimal("0.15"));
    }
    
    @Test
    public void testProductFieldsValid() { 
        Assertions.assertAll( "AssertAll test",
                () -> assertNotNull(product), 
                () -> assertNotNull(product.getProductId()), 
                () -> assertNotNull(product.getInitialQuantity()), 
                () -> assertNotNull(product.getProductDescription()), 
                () -> assertNotNull(product.getRemainingQuantity()), 
                () -> assertNotNull(product.getTotalUsedQuantity()), 
                () -> assertTrue(product.getTotalUsedQuantity() == 0), 
                () -> assertTrue(product.getRemainingQuantity()== 100)                 
                );
    }
    
}
