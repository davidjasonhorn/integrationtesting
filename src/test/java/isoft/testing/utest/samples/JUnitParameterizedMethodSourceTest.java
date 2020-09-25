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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author hornd
 */
@Tag("example-test")
public class JUnitParameterizedMethodSourceTest {

    static Stream<ProductTO> productProvider() {
        ProductTO p1 = new ProductTO("1", "Pencils", 100,0,100, BigDecimal.ONE);
        ProductTO p2 = new ProductTO("2", "NOtebooks", 50,0,50, BigDecimal.TEN);
        return Stream.of(p1,p2);
    }

    @ParameterizedTest()
    @MethodSource("productProvider")
    public void stringArgumentIsNotNull(ProductTO argument) {
        System.out.println(argument);
        Assertions.assertTrue(argument != null);
    }

}
