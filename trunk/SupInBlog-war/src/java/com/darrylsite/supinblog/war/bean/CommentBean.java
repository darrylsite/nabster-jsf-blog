package com.darrylsite.supinblog.war.bean;

import com.darrylsite.supinblog.war.utils.Utils;
import com.darrylsite.supinblog.model.Comment;
import com.darrylsite.supinblog.model.session.local.CommentServiceLocal;
import com.darrylsite.supinblog.model.Post;
import com.darrylsite.supinblog.model.session.local.PostServiceLocal;
import com.darrylsite.supinblog.model.User;
import com.darrylsite.supinblog.model.session.local.UserServiceLocal;
import com.darrylsite.supinblog.war.utils.GmailSender;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author nabster
 */
@ManagedBean
@SessionScoped
public class CommentBean
{

    @EJB
    private CommentServiceLocal commentService;
    @EJB
    private UserServiceLocal userService;
    @EJB
    private PostServiceLocal postService;
    private String content;
    private String userId;
    private String id;
    public String postId;

    /** Creates a new instance of CommentBean */
    public CommentBean() {
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<Comment> getAllComment() {
        PostBean postBean = Utils.findBean("postBean", PostBean.class);
        if (postBean == null || postBean.getId() == null)
        {
            return null;
        }

        if (postBean.getId().equals(""))
        {
            return new ArrayList<Comment>();
        }
        Post post;
        try
        {
            post = this.postService.getPostById(Long.parseLong(postBean.getId()));
        } catch (NumberFormatException ex)
        {
            return new ArrayList<Comment>();
        }
        return this.commentService.getCommentByPost(post.getId());
    }

    public String deleteComment() {
        Comment cmt = new Comment();
        cmt.setId(Long.parseLong(id));

        commentService.removeComment(cmt);
        Utils.refresh();
        return "";
    }

    public String addComment() {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreationDate(Calendar.getInstance().getTime());

        PostBean postBean = Utils.findBean("postBean", PostBean.class);
        Post post = postService.getPostById(Long.parseLong(postBean.getId()));

        UserBean userBean = Utils.findBean("userBean", UserBean.class);
        User user = this.userService.getUserByPseudo(userBean.getPseudo());
        comment.setUser(user);

        comment.setPosts(post);
        commentService.persistComment(comment);

        Utils.refresh();
        String eml = "Anonymous@SupinBlog.dar";
        if (post.getUsers() != null)
        {
            eml = post.getUsers().getEmail();
        }

        sendMail2(post.getUsers().getEmail(), "New Comment from " + eml, post.getTitle() + " ::: <br/>" + comment.getContent());

        return "";
    }

    private void sendMail2(String email, String subject, String body)
    {
        GmailSender.send("nabster@darrylsite.com", email, body, subject);

//        try
//        {
//            String[] recept = new String[]
//            {
//                email
//            };
//            SendMailUsingAuthentication mailer = new SendMailUsingAuthentication();
//            mailer.postMail(recept, subject, body, "nabster@darrylsite.com");
//        } catch (MessagingException ex)
//        {
//            Logger.getLogger(CommentBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

 }
