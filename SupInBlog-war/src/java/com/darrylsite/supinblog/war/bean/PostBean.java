
package com.darrylsite.supinblog.war.bean;

import com.darrylsite.supinblog.model.session.local.UserServiceLocal;
import com.darrylsite.supinblog.war.utils.Utils;
import com.darrylsite.supinblog.model.Category;
import com.darrylsite.supinblog.model.session.local.CategoryServiceLocal;
import com.darrylsite.supinblog.model.Post;
import com.darrylsite.supinblog.model.session.local.PostServiceLocal;
import com.darrylsite.supinblog.model.User;
import com.darrylsite.supinblog.model.session.local.UtilServiceLocal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 * @author nabster
 */
@ManagedBean
@SessionScoped
public class PostBean
{
    @EJB
    private UserServiceLocal userService;
    @EJB
    private UtilServiceLocal utilService;
    @EJB
    private PostServiceLocal postService;
    @EJB
    private CategoryServiceLocal categoryService;
    private String id;
    private String title;
    private String content;
    private Date creationDate;
    private Date upDate;
    private String modif;
    private String userId;
    private List<String> categories;
    private List<Category> categoryList;
    private User author;
    private String commentNumber;

    @Inject
    private UserBean userBean;

    /** Creates a new instance of PostBean */
    public PostBean()
    {
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getUpDate()
    {
        return upDate;
    }

    public void setUpDate(Date upDate)
    {
        this.upDate = upDate;
    }

    public String getModif()
    {
        return modif;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getCommentNumber()
    {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber)
    {
        this.commentNumber = commentNumber;
    }

    public List<Category> getCategoryList()
    {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList)
    {
        this.categoryList = categoryList;
    }  

    public void setModif(String modif)
    {
        this.modif = modif;
    }

    public String createPost()
    {
       Post post = new Post();
       post.setTitle(title);
       post.setContent(content);
       post.setCreationDate(Calendar.getInstance().getTime());
       post.setLastModification(Calendar.getInstance().getTime());
       userBean = Utils.findBean("userBean", UserBean.class);
       User usr = new User();
       usr.setId(userBean.getId());
       usr = this.userService.getUserByPseudo(userBean.getPseudo());
       post.setUsers(usr);
       post = this.postService.persistPost(post);
       this.id = ""+post.getId();
       utilService.refresh(usr);

       for(String s : categories)
       {
          Category c = this.categoryService.getCategoryById(Long.parseLong(s));
          if(c==null)
              continue;
         c.addPost(post);
         this.categoryService.mergeCategory(c);
          utilService.refresh(c);
       }

       post = (Post)utilService.refresh(post);
       refreshPostData(post);
       
       return "postDetail";
    }

    private void  refreshPostData(Post post)
    {
      title = post.getTitle();
      content = post.getContent();
      creationDate = post.getCreationDate();
      upDate = post.getLastModification();
      userId = ""+post.getUsers().getId();
      author = post.getUsers();
      categoryList = post.getCategories();
    }

    public void deletePost()
    {
     Post post= new Post();
     post.setId(Long.parseLong(id));
     this.postService.removePost(post);
     Utils.refresh();
    }

    public String modifyPost()
    {
       Post post = this.postService.getPostById(Long.parseLong(id));
       post.removeAllCategory();
       for(String s : categories)
       {
          Category c = this.categoryService.getCategoryById(Long.parseLong(s));
          if(c==null)
              continue;
          post.addCategory(c);
       }

       post.setTitle(title);
       post.setContent(content);
       post.setLastModification(Calendar.getInstance().getTime());
       post = this.postService.mergePost(post);

       return "postDetail";
    }

    public String gotoManagePost()
    {
      Post post = this.postService.getPostById(Long.parseLong(id));
      title = post.getTitle();
      content = post.getContent();
      creationDate = post.getCreationDate();
      upDate = post.getLastModification();
      userId = ""+post.getUsers().getId();
      author = post.getUsers();
      categoryList = post.getCategories();
      return "managePost";
    }

    public String gotoPostDetail()
    {
      Post post = this.postService.getPostById(Long.parseLong(id));
      title = post.getTitle();
      content = post.getContent();
      creationDate = post.getCreationDate();
      upDate = post.getLastModification();
      userId = ""+post.getUsers().getId();
      author = post.getUsers();
      categoryList = post.getCategories();
      commentNumber = null;
      return "postDetail";
    }

    public List<Category> getCategories()
    {
      return this.categoryService.getCategoryFindAll();
    }

    public void setCategories(List<String> categories)
    {
        this.categories = categories;
    }

    public List<Post> getPostList()
    {
        List<Post> liste = this.postService.getPostFindAll();
        List<Post> lst = new ArrayList<Post>();
        for(Post p : liste)
        {
            this.utilService.refresh(p);
            lst.add(p);
        }

     return lst;
    }

     public List<Post> getUpdatedPostList()
    {
        List<Post> liste = this.postService.getPostFindByLastUpDated();
        List<Post> lst = new ArrayList<Post>();
        int i=0;
        for(Post p : liste)
        {
            i++;
            lst.add(p);
            if(i>4)
                break;
        }

     return lst;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }



}
