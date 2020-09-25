package isoft.testing.utest.product.domain;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author hornd
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProductRepositoryImpl implements ProductRepository{

    @Inject
    @ProductEmQualifier
    private EntityManager em;    
    
    @Override
    public Product findByProductId(String productId) {
        Query query = em.createQuery("SELECT pd FROM Product where (:busId1 is null or pd.productId = :productId)", Product.class); 
        query.setParameter("productId", productId); 
                
       return (Product) query.getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void save(Product product) {
        em.persist(product);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> findAll() {
        Query query = em.createQuery("SELECT pd FROM Product", Product.class); 
                
       return  query.getResultList();    
    }
    
}
