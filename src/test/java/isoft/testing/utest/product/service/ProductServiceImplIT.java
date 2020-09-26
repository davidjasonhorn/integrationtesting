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
package isoft.testing.utest.product.service;

import isoft.testing.utest.product.conversion.InventoryTransactionConverter;
import isoft.testing.utest.product.conversion.ProductConverter;
import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.domain.ProductRepositoryImpl;
import isoft.testing.utest.product.external.ExchangeRateHelper;
import isoft.testing.utest.product.external.ExchangeRateService;
import isoft.testing.utest.product.validation.SimpleObjectValidator;
import isoft.testing.utest.product.validation.ValidationError;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hornd
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplIT {
    //MOCKS
    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery query;

    @Mock
    private ExchangeRateService exchangeRateService;

    //REGULAR OBJECTS
    @Spy
    @InjectMocks
    private ProductRepositoryImpl repository;

    @Spy
    private ProductConverter productConverter = new ProductConverter();

    @Spy
    private SimpleObjectValidator simpleObjectValidator = new SimpleObjectValidator();

    @Spy
    @InjectMocks
    private ExchangeRateHelper exchangeRateHelper = new ExchangeRateHelper();

    @Spy
    private InventoryTransactionConverter inventoryTransactionConverter = new InventoryTransactionConverter();

    private ProductServiceImpl productService = new ProductServiceImpl();

    @BeforeEach
    public void setUp() {
        simpleObjectValidator.setProductRepository(repository);
        productService.setExchangeRateHelper(exchangeRateHelper);
        productService.setInventoryTransactionConverter(inventoryTransactionConverter);
        productService.setProductConverter(productConverter);
        productService.setValidator(simpleObjectValidator);
        productService.setProductRepository(repository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addProduct_valid_scenario() {
        //Arrange
        ProductTO product = new ProductTO("id", "description", 100, 0, 100, BigDecimal.ONE);
        Product p = new Product("id", "description", 100, BigDecimal.ONE);

        Mockito.doNothing().when(entityManager).persist(p);
        Mockito.when(
                entityManager.createQuery("SELECT pd FROM Product where (:busId1 is null or pd.productId = :productId)",
                        Product.class)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(null);

        //Act
        productService.addProduct(product);

        //Assert
        Mockito.verify(productConverter, Mockito.times(1)).toDomain(product);
        Mockito.verify(simpleObjectValidator, Mockito.times(1)).validateProduct(product);
        Mockito.verify(repository, Mockito.timeout(1)).save(p);
        Mockito.verify(entityManager, Mockito.timeout(1)).persist(p);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void performTransaction_valid_scenario() {
        //Arrange
        InventoryTransactionTO trans = new InventoryTransactionTO(LocalDateTime.now(), 100, "id");
        Product p = new Product("id", "description", 100, BigDecimal.ONE);

        Mockito.doNothing().when(entityManager).persist(p);
        Mockito.when(
                entityManager.createQuery("SELECT pd FROM Product where (:busId1 is null or pd.productId = :productId)",
                        Product.class)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(p);

        //Act
        productService.performTransaction(trans);

        //Assert
        Mockito.verify(inventoryTransactionConverter, Mockito.times(1)).toDomain(trans);
        Mockito.verify(simpleObjectValidator, Mockito.times(1)).validateInventoryTransaction(trans);
        Mockito.verify(repository, Mockito.timeout(1)).save(p);
        Mockito.verify(entityManager, Mockito.timeout(1)).persist(p);
    }
    
    
    
    @Test
    public void getSalesPrice_valid_scenario() {
        //Arrange
        InventoryTransactionTO trans = new InventoryTransactionTO(LocalDateTime.now(), 100, "id");
        Product p = new Product("id", "description", 100, BigDecimal.ONE);

        //The amount = quantity * unit price * exchange rate (100 * 1 * 1.16)
        BigDecimal expectedAmount = new BigDecimal("116.00");

        Mockito.when(
                entityManager.createQuery("SELECT pd FROM Product where (:busId1 is null or pd.productId = :productId)",
                        Product.class)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(p);
        
        Mockito.when(exchangeRateService.getCurrentExchangeRateForCurrency("EUR", "USD")).thenReturn(new BigDecimal("1.16"));

        //Act
        BigDecimal actualAmount = productService.getSalePrice(trans, "USD");

        //Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        
        Mockito.verify(exchangeRateService, Mockito.times(1)).getCurrentExchangeRateForCurrency("EUR", "USD");
        Mockito.verify(exchangeRateHelper, Mockito.times(1)).calculateSalePriceForCurrency(trans, "USD", p);
        Mockito.verify(simpleObjectValidator, Mockito.times(1)).validateInventoryTransaction(trans);
    }    

    @Test
    public void perform_transaction_invalid_null_quantity() {
        //Arrange
        InventoryTransactionTO trans = new InventoryTransactionTO(LocalDateTime.now(), null, "id");

        //Act & Assert
        Assertions.assertThrows(ValidationError.class,
                () -> productService.performTransaction(trans), "InitialQuantity cannot be null");
    }

    @Test
    public void addProduct_invalid_null_quantity() {
        //Arrange
        ProductTO product = new ProductTO("id", "desciption", null, 0, 100, BigDecimal.ONE);

        //Act & Assert
        Assertions.assertThrows(ValidationError.class,
                () -> productService.addProduct(product), "InitialQuantity cannot be null");

    }

}
