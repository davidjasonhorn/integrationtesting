package isoft.testing.utest.product.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EmProducer {

    @PersistenceContext(unitName = "inventory-pu")
    private EntityManager em;

    @Produces
    @ProductEmQualifier
    public EntityManager getEntityManager() {
        return this.em;
    }
}
