package ru.voskhod.springdemo.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.voskhod.springdemo.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Customer> getCustomers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.joinTransaction();
        return entityManager.createQuery("SELECT c FROM Customer c ORDER BY lastName", Customer.class).getResultList();
    }

    @Override
    public void saveCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Session session = entityManager.unwrap(Session.class);

        // save or update the customer
        session.saveOrUpdate(customer);

    }

    @Override
    public Customer getCustomer(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.joinTransaction();
        return entityManager.find(Customer.class, id);
    }

    @Override
    public void deleteCustomer(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.joinTransaction();

        entityManager
                .createQuery("DELETE FROM Customer where id=:customerId")
                .setParameter("customerId", id)
                .executeUpdate();
    }

    @Override
    public List<Customer> findCustomersByName(String searchName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.joinTransaction();

        return entityManager
                .createQuery(
                        "SELECT c FROM Customer c " +
                                "WHERE c.firstName LIKE CONCAT('%', :searchName, '%') " +
                                "OR c.lastName LIKE CONCAT('%', :searchName, '%')",
                        Customer.class).setParameter("searchName", searchName)
                .getResultList();
    }

}
