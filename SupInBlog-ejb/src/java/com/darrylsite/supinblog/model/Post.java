package com.darrylsite.supinblog.model;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
  @NamedQuery(name = "Post.findAll", query = "select o from Post o order by o.id desc"),
  @NamedQuery(name = "Post.findLastUpdated", query = "select o from Post o order by o.lastModification desc"),
   @NamedQuery(name = "Post.findById", query = "select o from Post o where o.id = :id"),
  @NamedQuery(name = "Post.CreationDate", query = "select p from Post p where p.creationDate between :date1 and :date2"),
  @NamedQuery(name = "Post.findByUpdatedDate", query = "select p from Post p where p.lastModification between :date1 and :date2"),
  @NamedQuery(name = "Post.CreationDate.user", query = "select p from Post p where p.creationDate between :date1 and :date2 " +
                                                       "and p.users= :author"),
  @NamedQuery(name = "Post.findByUpdatedDate.user", query = "select p from Post p where p.lastModification between :date1 and :date2 " +
                                                          " and p.users= :author")
})
@Table(name = "POSTS")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String title;
    @Column(length=10000)
    private String content;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;
    @ManyToMany(mappedBy = "posts", cascade=CascadeType.REFRESH)
    private List<Category> categories;
    @OneToMany(mappedBy = "posts", cascade=CascadeType.ALL)
    private List<Comment> comments;
    @ManyToOne(cascade=CascadeType.REFRESH)
    private User users;

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category c)
    {
        if(categories ==null)
          categories = new ArrayList<Category>();
     categories.add(c);
    }

    public void removeCategory(Category c)
    {
     categories.remove(c);
    }

    public void  removeAllCategory()
    {
     categories.clear();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public void addComment(Comment c)
    {
      if(this.comments==null)
           comments = new ArrayList<Comment>();
      comments.add(c);
    }

    public void removeComment(Comment c)
    {
      comments.remove(c);
    }

    public String getCommentsNumber()
    {
     if(comments==null)
         return "0";
    return ""+comments.size();
    }
}
