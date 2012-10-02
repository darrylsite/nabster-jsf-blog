/**
 * 
 */
package com.darrylsite.supinblog.model.session;

import com.darrylsite.supinblog.model.session.local.UserServiceLocal;
import com.darrylsite.supinblog.model.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import javax.persistence.Query;

@Stateless(name = "UserService", mappedName = "Application1-Model-UserService")
public class UserServiceBean implements UserServiceLocal {

    @PersistenceUnit(name = "SupInBlog-ejbPU")
    private EntityManagerFactory emf;
    private EntityManager em;

    public UserServiceBean() {
    }

    @Override
    public Object queryByRange(String jpqlStmt, int firstResult,
            int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    @Override
    public User persistuser(User user)
    {
        em.persist(user);
        em.flush();
        return user;
    }

    @Override
    public User mergeuser(User user)
    {
        return em.merge(user);
    }

    @Override
    public void removeuser(User user)
    {
        user = em.find(User.class, user.getId());
        em.remove(user);
    }

    /** <code>select o from user o</code> */
    @Override
    public List<User> userFindAll()
    {
        return em.createNamedQuery("user.findAll").getResultList();
    }

    @PostConstruct
    public void init()
    {
        em = emf.createEntityManager();
    }

    @PreDestroy
    public void destroy()
    {
        em.close();
    }

    @Override
    public User getUserByPseudo(String pseudo)
    {
        Query query = em.createNamedQuery("user.findByPseudo");
        query.setParameter("pseudo", pseudo);
        List<User> users = query.getResultList();
        if (users == null || users.isEmpty())
        {
            return null;
        }
        return users.get(0);
    }
}
