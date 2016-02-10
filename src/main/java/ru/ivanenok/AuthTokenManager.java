package ru.ivanenok;

import ru.ivanenok.domain.AuthToken;
import ru.ivanenok.domain.Customer;
import ru.ivanenok.util.TimeService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by ivanenok on 2/10/16.
 */
@Stateless
public class AuthTokenManager {
    @PersistenceContext
    EntityManager em;


    @Transactional
    public AuthToken authenticate(String email, String password) throws CustomerNotFound {
        AuthToken authToken;
        TypedQuery<Customer> query = em.createQuery("select customer from Customer customer where customer.email = :email and customer.password = :password", Customer.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Customer> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            Customer customer = resultList.get(0);
            LocalDateTime now = TimeService.now();
            authToken = new AuthToken(now, now.plusDays(1), UUID.randomUUID().toString());
            customer.getTokens().add(authToken);
            em.merge(customer);
        } else {
            throw new CustomerNotFound();
        }
        return authToken;
    }

}
