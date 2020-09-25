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

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hornd
 */
@Tag("example-test")
@Disabled
public class JUnitTimedTests {

    @Test
    public void testAssertTimeoutFails() {
        //Test is run in same thread
        //will wait until the Execution as finished to 
        //evaluate if the test passed or failed
        Assertions.assertTimeout(Duration.ofMillis(2000),
                () -> Thread.sleep(100),
                "Should have executed in 2000 ms");
    }

    @Test
    public void testAssertTimeoutPreEmptiveliyFails() {
        //Test is run in separate thread
        //Will run for the time of the Duration
        //And evluate if the test has passed or failed.
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(2000),
                () -> Thread.sleep(30000000),
                "Should have executed in 2000 ms");
    }

}
