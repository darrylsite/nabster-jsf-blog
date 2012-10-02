package com.darrylsite.supinblog.model.session.local;

import com.darrylsite.supinblog.model.Post;
import com.darrylsite.supinblog.model.User;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface PostServiceLocal {
  public Object queryByRange(String jpqlStmt, int firstResult, int maxResults);
  public Post persistPost(Post post);
  public Post mergePost(Post post);
  public void removePost(Post post);
  public List<Post> getPostFindAll();
  public List<Post> getPostFindByLastUpDated(Date begin, Date end, User user);
  public List<Post> getPostFindByLastUpDated();
  public List<Post> getPostFindByCreationDate(Date begin, Date end, User user) ;
  public List<Post> getPostFindByCreationDate(Date begin, Date end);
    public com.darrylsite.supinblog.model.Post getPostById(long id);
}
