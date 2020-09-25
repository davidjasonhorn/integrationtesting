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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hornd
 */
@ExtendWith(MockitoExtension.class)
public class MockitoArgumentCaptorTest {

    @Mock
    ProductRepository repository;

    @Captor
    ArgumentCaptor<String> captor;
    
    @Test
    public void testMock() {
        when(repository.findByProductId("id")).thenReturn(null);

        Product p = repository.findByProductId("id");

        verify(repository).findByProductId(captor.capture());
        Assertions.assertEquals(captor.getValue(), "id" );
        
        Assertions.assertNull(p, "Product should be null");
    }
}
