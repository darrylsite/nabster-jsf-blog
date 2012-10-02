package com.darrylsite.supinblog.model;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
               @NamedQuery(name = "Comment.findAll", query = "select o from Comment o"),
               @NamedQuery(name = "Comment.findByPost", query = "select o from Comment o where o.posts.id = :id"),
               @NamedQuery(name = "Comment.findByUser", query = "select o from Comment o where o.user = :user and o.creationDate between :date1 and :date2"),
               @NamedQuery(name = "Comment.findByDate", query = "select o from Comment o where o.creationDate between :date1 and :date2")
              }
              )
@Table(name = "COMMENTS")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
   
    @ManyToOne(cascade=CascadeType.REFRESH)
    private Post posts;
    @ManyToOne
    private User user;
    @Column(length=10000)
    private String content;
    
  @Temporal(TemporalType.DATE)
  private Date creationDate;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPosts() {
        return posts;
    }

    public void setPosts(Post posts) {
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
