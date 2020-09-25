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

import isoft.testing.utest.product.service.InventoryTransactionTO;
import isoft.testing.utest.product.service.ProductTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author hornd
 */
@Tag("example-test")
public class JUnitParameterizedValueSourceTest {
    
    @ParameterizedTest()
    @ValueSource(strings = {"EUR", "CHF"})
    public void stringArgumentIsNotNull(String argument) { 
        Assertions.assertTrue(argument != null);
    }
    
    @ParameterizedTest()
    @ValueSource(classes = {ProductTO.class, InventoryTransactionTO.class})
    public void classArgumentIsNotNull(Class argument) { 
        Assertions.assertTrue(argument != null);
    }

    @ParameterizedTest()
    @ValueSource(ints = {5, 10})
    public void intArgumentIsNotZero(Integer argument) { 
        Assertions.assertTrue(argument != 0);
    }  
    
}
