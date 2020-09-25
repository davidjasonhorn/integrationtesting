/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoft.testing.utest.product.service;

import isoft.testing.utest.product.conversion.InventoryTransactionConverter;
import isoft.testing.utest.product.conversion.ProductConverter;
import isoft.testing.utest.product.domain.InventoryTransaction;
import isoft.testing.utest.product.domain.Product;
import isoft.testing.utest.product.domain.ProductRepository;
import isoft.testing.utest.product.external.ExchangeRateHelper;
import isoft.testing.utest.product.validation.ObjectValidator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hornd
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    @Mock
    private InventoryTransactionConverter inventoryTransactionConverter;

    @Mock
    private ExchangeRateHelper exchangeRateHelper;

    @Mock
    private ObjectValidator objectValidator; 
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    @Test
    public void addProduct_valid_scenario() {
        //Arrange
        ProductTO product = new ProductTO("id", "description", 100, 0, 100, BigDecimal.ONE);
        Product p = new Product("id", "description", 100, BigDecimal.ONE);
        
        //validations pass
        Mockito.doNothing().when(objectValidator).validateProduct(product);
        Mockito.when(productConverter.toDomain(product)).thenReturn(p);
        Mockito.doNothing().when(productRepository).save(p);

        //Act
        productService.addProduct(product);

        //Assert
        Mockito.verify(productConverter, Mockito.times(1)).toDomain(product);
        Mockito.verify(objectValidator, Mockito.atMostOnce()).validateProduct(product);
        Mockito.verify(productRepository, Mockito.atMostOnce()).save(p);

    }    
    
    @Test
    public void getSalesPrice_valid_scenario () { 
        
        //Arrange 
        Product p = new Product("id", "description", 100, BigDecimal.ONE);
        InventoryTransactionTO iv = new InventoryTransactionTO(LocalDateTime.now(), 10, "id");
        BigDecimal bd = new BigDecimal("10.25");

        //validations pass
        Mockito.doNothing().when(objectValidator).validateInventoryTransaction(iv);
        Mockito.when(productRepository.findByProductId("id")).thenReturn(p);
        Mockito.when(exchangeRateHelper.calculateSalePriceForCurrency(iv, "EUR", p)).thenReturn(bd);
        
        //Act 
        BigDecimal salePrice = productService.getSalePrice(iv, "EUR");
        
        //Assert
        Assertions.assertEquals(bd, salePrice);
        Mockito.verify(objectValidator, Mockito.times(1)).validateInventoryTransaction(iv);
        Mockito.verify(productRepository, Mockito.times(1)).findByProductId("id");
        Mockito.verify(exchangeRateHelper, Mockito.times(1)).calculateSalePriceForCurrency(iv, "EUR", p);
    }
    
    @Test
    public void performTransaction_valid_scenario () { 
        
        //Arrange 
        InventoryTransactionTO iv = new InventoryTransactionTO(LocalDateTime.now(), 10, "id");
        Product p = new Product("id", "description", 100, BigDecimal.ONE);
        InventoryTransaction i = new InventoryTransaction(LocalDateTime.MIN, 10);

        //validations pass
        Mockito.doNothing().when(objectValidator).validateInventoryTransaction(iv);
        Mockito.when(productRepository.findByProductId("id")).thenReturn(p);
        Mockito.when(inventoryTransactionConverter.toDomain(iv)).thenReturn(i);
        
        //Act 
        productService.performTransaction(iv);
        
        //Assert
        Mockito.verify(objectValidator, Mockito.times(1)).validateInventoryTransaction(iv);
        Mockito.verify(productRepository, Mockito.times(1)).findByProductId("id");
        Mockito.verify(inventoryTransactionConverter, Mockito.times(1)).toDomain(iv);

    }
    
}
