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

import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.domain.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Inline Mock creation
 * @author hornd
 */
@Tag("example-test")
public class MockitoMockInlineTest {
    
    @Test
    public void testInLineMock() { 
        ProductRepository repository =  mock(ProductRepository.class); 
        when(repository.findByProductId("id")).thenReturn(null);
        
        Product p = repository.findByProductId("id");
        
        Assertions.assertNull(p, "Product should be null");
    }
    
    
}
