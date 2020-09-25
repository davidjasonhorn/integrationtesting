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

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

/**
 *
 * @author hornd
 */
@Tag("example-test")
public class JUnitDependecyInjectionTest {

    @BeforeEach
    void init(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        System.out.printf("@BeforeEach %s %n", displayName);
    }

    @Test
    @DisplayName("Test Info")
    @Tag("my-tag")
    public void testDependencyInject(TestInfo testinfo) {
        System.out.println(testinfo.getDisplayName());
        System.out.println(testinfo.getTestMethod());
        System.out.println(testinfo.getTestClass());
        System.out.println(testinfo.getTags());
    }
    
    @RepeatedTest(2)
    public void testDependencyInject(RepetitionInfo repetitionInfo) {
        System.out.println("Current Repetition :" + repetitionInfo.getCurrentRepetition());
        System.out.println("Total Repetitions: " + repetitionInfo.getTotalRepetitions());

    } 
    
    @Test
    public void testDependencyInject(TestReporter testReporter) {
        testReporter.publishEntry("key", "value");
        Map<String, String> values = new HashMap<>();
        values.put("key2", "value2");
        values.put("key3", "value3");
        testReporter.publishEntry(values);
        
    }    
}
