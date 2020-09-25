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
package isoft.testing.utest.product.validation;

import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.domain.ProductRepository;
import isoft.testing.utest.product.service.ProductTO;
import java.math.BigDecimal;
import java.util.Set;
import javax.validation.Configuration;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.internal.engine.ConstraintValidatorFactoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hornd
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UniqueProductValidatorTest implements ConstraintValidatorFactory {

    private Validator validator;

    @Mock
    private ProductRepository repository;

    //Inject Mocks does not work with JSR303 validators. 
    //The JSR303 mechanism handles dependency injection
//    @InjectMocks
//    UniqueProductValidator uniqueProductValidator;
    
    @BeforeEach
    public void setup() {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        config.constraintValidatorFactory(this);
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void product_is_not_unique() {
        //arrange
        ProductTO t = new ProductTO("id", "desc", 100, 0, 100, new BigDecimal("0.17"));
        Product p = new Product("id", "descr", 100, new BigDecimal("0.17"));

        Mockito.when(repository.findByProductId("id")).thenReturn(p);

        //act
        //run the validation
        Set<ConstraintViolation<ProductTO>> violations = validator.validate(t);
        
        //assert
        //check the validation error        
        Assertions.assertTrue(isConstraintViolated("{product.unique.constraint}",violations), "The product is not unique");
    }

    @Test
    public void product_is_unique() {
        //arrange
        ProductTO t = new ProductTO("id", "desc", 100, 0, 100, new BigDecimal("0.17"));
        Product p = null;

        Mockito.when(repository.findByProductId("id")).thenReturn(null);

        //act
        //run the validation
        Set<ConstraintViolation<ProductTO>> violations = validator.validate(t);

        //check the validation error
        Assertions.assertFalse(isConstraintViolated("{product.unique.constraint}", violations), "The product is unique");
    }
    
    private boolean isConstraintViolated(String message, Set<ConstraintViolation<ProductTO>> violations) { 
        boolean violationFound = false;
        
        for (ConstraintViolation<?> m : violations) {
            if ( m.getMessage().equals("{product.unique.constraint}")) { 
                violationFound = true;
            }
        }

        return violationFound;
    }
    
    /**
     * Workaround for JSR Dependency Injection.
     * 
     * @param <T>
     * @param type
     * @return 
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> type) {
        if (type == UniqueProductValidator.class) {
            return (T) new UniqueProductValidator(repository);
        }
        return new ConstraintValidatorFactoryImpl().getInstance(type);
    }

}
