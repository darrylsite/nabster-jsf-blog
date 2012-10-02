package com.darrylsite.supinblog.model.session.local;

import com.darrylsite.supinblog.model.Comment;
import com.darrylsite.supinblog.model.User;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface CommentServiceLocal {
  Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

  Comment persistComment(Comment comment);

  Comment mergeComment(Comment comment);

  void removeComment(Comment comment);

  List<Comment> getCommentFindAll();
  public List<Comment> getCommentFindByUser(Date date1, Date date2, User user) ;
  public List<Comment> getCommentFindByDate(Date date1, Date date2);

    public java.util.List<com.darrylsite.supinblog.model.Comment> getCommentByPost(long id);
}
