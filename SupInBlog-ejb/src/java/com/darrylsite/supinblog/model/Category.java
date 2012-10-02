package com.darrylsite.supinblog.model;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "Category.findAll", query = "select o from Category o")
})
@Table(name = "CATEGORIES")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    @Column(length=1000)
    private String description;
    @ManyToMany   (cascade=CascadeType.ALL)
    private List<Post> posts;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post p)
    {
        if(posts==null)
           posts = new ArrayList<Post>();
        this.posts.add(p);
    }

    public void removePost(Post p)
    {
     posts.remove(p);
    }
}
