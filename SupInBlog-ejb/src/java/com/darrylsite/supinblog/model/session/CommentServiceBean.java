package com.darrylsite.supinblog.model.session;

import com.darrylsite.supinblog.model.session.local.CommentServiceLocal;
import com.darrylsite.supinblog.model.Comment;
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

@Stateless(name="CommentService",
    mappedName = "Application1-Model-CommentService")
public class CommentServiceBean implements CommentServiceLocal
{
    @PersistenceUnit(name = "SupInBlog-ejbPU")
    private EntityManagerFactory emf;
    private EntityManager em;

    public CommentServiceBean() {
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
    public Comment persistComment(Comment comment) 
    {
        em.persist(comment);
        
        em.flush();
        comment.getPosts().getComments();
        return comment;
    }

    @Override
    public Comment mergeComment(Comment comment) 
    {
        return em.merge(comment);
    }

    @Override
    public void removeComment(Comment comment) 
    {
        comment = em.find(Comment.class, comment.getId());
        if(comment ==null)
            return;
        comment.getPosts().removeComment(comment);
        comment.setPosts(null);
        em.remove(comment);
        em.flush();
    }

    @Override
    public List<Comment> getCommentByPost(long id)
    {
        Query query = em.createNamedQuery("Comment.findByPost");
        query.setParameter("id", id);
        return query.getResultList();
    }

    /** <code>select o from Comment o</code> */
    @Override
    public List<Comment> getCommentFindAll() 
    {
        return em.createNamedQuery("Comment.findAll").getResultList();
    }
    
    @Override
  public List<Comment> getCommentFindByDate(Date date1, Date date2) 
  {
       Query query = em.createNamedQuery("Post.CreationDate.user");
       query.setParameter("date1", date1);
       query.setParameter("date2", date2);
       return query.getResultList();
  }
  
    @Override
  public List<Comment> getCommentFindByUser(Date date1, Date date2, User user) 
  {
    Query query = em.createNamedQuery("Comment.findByUser");
    query.setParameter("date1", date1);
    query.setParameter("date2", date2);
    query.setParameter("user", user);
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
