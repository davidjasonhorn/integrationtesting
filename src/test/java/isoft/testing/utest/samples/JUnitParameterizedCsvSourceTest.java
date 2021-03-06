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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author hornd
 */
@Tag("example-test")
public class JUnitParameterizedCsvSourceTest {

    @ParameterizedTest()
    @CsvSource({"string,1", "string,2", "'string 3',3"})
    public void stringArgumentIsNotNull(String argument1, Integer argument2) {
        System.out.println(argument1 + ":" + argument2);
        Assertions.assertTrue(argument1 != null);
        Assertions.assertTrue(argument2 > 0);
    }

}
