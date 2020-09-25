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
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 *
 * @author hornd
 */
@Tag("example-test")
public class JUnitParameterizedArgumentSourceTest {

    @ParameterizedTest()
    @ArgumentsSource(CustomsArgumentProvider.class)
    public void stringArgumentIsNotNull(ProductTO product) {
        System.out.println(product);
        Assertions.assertTrue(product != null);

    }
    
    static class CustomsArgumentProvider implements ArgumentsProvider { 

        
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext ec) throws Exception {

        ProductTO p1 = new ProductTO("1","Pencil", 10, 0, 10, new BigDecimal("0.15") );
        ProductTO p2 = new ProductTO("2","Erasers", 100,0,100, new BigDecimal("0.28") );
        return Stream.of(Arguments.of(p1), Arguments.of(p2));
        }
        
    }

}
