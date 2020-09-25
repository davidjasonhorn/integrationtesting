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

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * A spy object is managed by Mockito that 
 * supports real behavior of the underlying object. 
 * 
 * @author hornd
 */
@Tag("example-test")
@ExtendWith(MockitoExtension.class)
public class MockitoSpyTest {
    
    @Spy
    private List<String> spyList = new ArrayList<>();

    @Mock
    private List<String> mockList = new ArrayList<>();
    
    
    @Test
    public void spyTest() { 
        spyList.add("a"); //actually adds it to the spy
        spyList.add("b"); //actually adds it to the spy
        spyList.add("c"); //actually adds it to the spy
        doReturn("d").when(spyList).get(2); //mock behavior
        
        mockList.add("a"); //does nothing
        mockList.add("b"); //does nothing
        when(mockList.get(2)).thenReturn("c"); //mock behavior
        
        Assertions.assertEquals(0, mockList.size());
        Assertions.assertEquals(3, spyList.size());
        Assertions.assertEquals("c", mockList.get(2));
        Assertions.assertEquals("d", spyList.get(2));
        
    }
}
