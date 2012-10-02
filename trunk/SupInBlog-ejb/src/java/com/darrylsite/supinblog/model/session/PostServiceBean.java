package com.darrylsite.supinblog.model.session;

import com.darrylsite.supinblog.model.session.local.PostServiceLocal;
import com.darrylsite.supinblog.model.Category;
import com.darrylsite.supinblog.model.Post;
import com.darrylsite.supinblog.model.User;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

@Stateless(name="PostService", mappedName = "Application1-Model-PostService")
public class PostServiceBean implements PostServiceLocal {
    @PersistenceUnit(name = "SupInBlog-ejbPU")
    private EntityManagerFactory emf;
    private EntityManager em;


    public PostServiceBean() {
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
    public Post persistPost(Post post)
    {
        em.persist(post);
        em.flush();
        em.refresh(post);
        return post;
    }

    @Override
    public Post mergePost(Post post) {
        post = em.merge(post);
        em.flush();
        em.refresh(post);
        return post;
    }

    @Override
    public void removePost(Post post) {
        post = em.find(Post.class, post.getId());
        List<Category> cats = post.getCategories();
        for(Category c : cats)
            c.removePost(post);
        em.flush();
        em.remove(post);
        em.flush();
    }

    /** <code>select o from Post o</code> */
    @Override
    public List<Post> getPostFindAll() {
        return em.createNamedQuery("Post.findAll").getResultList();
    }

    @Override
    public Post getPostById(long id)
    {
        Post p = em.find(Post.class, id);
        if(p==null)
            return null;
        em.refresh(p);
        return em.find(Post.class, id);
    }
    
    @Override
  public List<Post> getPostFindByCreationDate(Date begin, Date end) 
  {
      Query query = em.createNamedQuery("Post.CreationDate");
      query.setParameter("date1", begin);
      query.setParameter("date2", end);
      return query.getResultList();
  }
  
    @Override
  public List<Post> getPostFindByCreationDate(Date begin, Date end, User user) 
  {
      Query query = em.createNamedQuery("Post.CreationDate.user");
      query.setParameter("date1", begin);
      query.setParameter("date2", end);
      query.setParameter("author", user);
      return query.getResultList();
  }
   
    @Override
  public List<Post> getPostFindByLastUpDated() {
    Query query = em.createNamedQuery("Post.findLastUpdated");
    return query.getResultList();
  } 
  
    @Override
  public List<Post> getPostFindByLastUpDated(Date begin, Date end, User user) {
    Query query = em.createNamedQuery("Post.findByUpdatedDate.user");
    query.setParameter("date1", begin);
    query.setParameter("date2", end);
    query.setParameter("author", user);
      return query.getResultList();
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
