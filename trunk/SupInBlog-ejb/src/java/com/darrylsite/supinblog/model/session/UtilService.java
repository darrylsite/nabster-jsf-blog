
package com.darrylsite.supinblog.model.session;

import com.darrylsite.supinblog.model.session.local.UtilServiceLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nabster
 */
@Stateless
public class UtilService implements UtilServiceLocal
{
    @PersistenceContext(unitName = "SupInBlog-ejbPU")
    private EntityManager em;

    @Override
    public Object refresh(Object object)
    {
      object = em.merge(object);
      em.flush();
      em.refresh(object);
      return object;
    }
    
    
 
}
