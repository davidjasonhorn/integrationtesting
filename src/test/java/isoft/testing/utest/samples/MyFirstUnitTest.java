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

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hornd
 */
@Tag("example-test")
public class MyFirstUnitTest {

   @Test 
   public void test_round_up () { 
        //arrange - given 
        BigDecimal toTest = new BigDecimal("100.756431"); 
        BigDecimal expected = new BigDecimal("100.76"); 
        
        //act - when 
        toTest = toTest.setScale(2, RoundingMode.HALF_UP); 
        
        //assert - then
        Assertions.assertEquals(expected, toTest);
        
    }
}
