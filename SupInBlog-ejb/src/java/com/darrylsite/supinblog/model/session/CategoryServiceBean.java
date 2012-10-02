package com.darrylsite.supinblog.model.session;

import com.darrylsite.supinblog.model.session.local.CategoryServiceLocal;
import com.darrylsite.supinblog.model.Category;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

@Stateless(name="CategoryService",
    mappedName = "Application1-Model-CategoryService")
public class CategoryServiceBean implements CategoryServiceLocal 
{
    @PersistenceUnit(name = "SupInBlog-ejbPU")
    private EntityManagerFactory emf;
    private EntityManager em;

    public CategoryServiceBean() 
    {
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
    public Category persistCategory(Category category) {
        em.persist(category);
        em.flush();
        return category;
    }

    @Override
    public Category mergeCategory(Category category) {
        category = em.merge(category);
        em.flush();
        return category;
    }

    @Override
    public void removeCategory(Category category) {
        category = em.find(Category.class, category.getId());
        if(category ==null)
            return;
        em.remove(category);
        em.flush();
    }

    @Override
    public Category getCategoryById(long id)
    {
      return em.find(Category.class, id);
    }

    /** <code>select o from Category o</code> */
    @Override
    public List<Category> getCategoryFindAll() 
    {
        return em.createNamedQuery("Category.findAll").getResultList();
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    
}
