package com.darrylsite.supinblog.model.session.local;

import com.darrylsite.supinblog.model.User;
import java.util.List;

import javax.ejb.Local;

@Local
public interface UserServiceLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    User persistuser(User user);

    User mergeuser(User user);

    void removeuser(User user);

    List<User> userFindAll();

    public com.darrylsite.supinblog.model.User getUserByPseudo(java.lang.String pseudo);
}
