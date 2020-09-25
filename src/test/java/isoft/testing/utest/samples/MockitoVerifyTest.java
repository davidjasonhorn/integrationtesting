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
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Mockito provides ways to inspect what has happened 
 * to the mock objects that are managed by Mockito.
 *
 * @author hornd
 */
@Tag("example-test")
public class MockitoVerifyTest {

    @Test
    public void verifyMethodCalled() {
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.findByProductId("id")).thenReturn(null);

        Product p = repository.findByProductId("id");

        Assertions.assertNull(p, "Product should be null");
        verify(repository, Mockito.times(1)).findByProductId("id");

    }

    @Test
    public void verifyMethodCalledTwoTimes() {
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.findByProductId("id")).thenReturn(null);

        Product p = repository.findByProductId("id");
        Product p2 = repository.findByProductId("id");

        Assertions.assertNull(p, "Product should be null");
        Assertions.assertNull(p2, "Product should be null");
        verify(repository, Mockito.times(2)).findByProductId("id");

    }

    @Test
    public void verifyMethodCalledAtLeastTwoTimes() {
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.findByProductId("id")).thenReturn(null);

        Product p = repository.findByProductId("id");
        Product p2 = repository.findByProductId("id");
        Product p3 = repository.findByProductId("id");

        Assertions.assertNull(p, "Product should be null");
        Assertions.assertNull(p2, "Product should be null");

        verify(repository, Mockito.atLeast(2)).findByProductId("id");

    }

    @Test
    public void verifyMethodCalledAtMostOnce() {
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.findByProductId("id")).thenReturn(null);

        Product p = repository.findByProductId("id");


        Assertions.assertNull(p, "Product should be null");

        verify(repository, Mockito.atMostOnce()).findByProductId("id");

    }
    
    @Test
    public void verifyMethodCalledNever() {
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.findByProductId("id")).thenReturn(null);

        Product p = repository.findByProductId("id");

        Assertions.assertNull(p, "Product should be null");

        verify(repository, Mockito.never()).findByProductId("id2");

    }    

    @Test
    public void verifyOnlyloadProductByProductIdMethodCalled() {
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.findByProductId("id")).thenReturn(null);

        Product p = repository.findByProductId("id");

        Assertions.assertNull(p, "Product should be null");

        verify(repository, Mockito.only()).findByProductId("id");

    }    
    

}
