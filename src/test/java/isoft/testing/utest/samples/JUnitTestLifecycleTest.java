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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hornd
 */
@Tag("example-test")
public class JUnitTestLifecycleTest {
    
    @BeforeAll
    public static void beforeAll () { 
       System.out.println("before all tests are run ...");
    }
    
    @BeforeEach
    public void beforeEach() { 
       System.out.println("before each test is run ...");
    }
    
    @Test
    public void test1() { 
       System.out.println("test1 has run ...");
    }
    
    @Test
    public void test2() { 
       System.out.println("test2 has run ...");
    }

    @AfterEach   
    public void afterEach() { 
       System.out.println("after each test is run ...");
    }
    
    @AfterAll
    public static void afterAll () { 
       System.out.println("after all tests are run ...");
    }
}

