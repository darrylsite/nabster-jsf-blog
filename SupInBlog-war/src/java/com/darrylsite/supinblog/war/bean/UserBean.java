package com.darrylsite.supinblog.war.bean;

import com.darrylsite.supinblog.model.User;
import com.darrylsite.supinblog.model.session.local.UserServiceLocal;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;

/**
 *
 * @author nabster
 */
@ManagedBean
@SessionScoped
public class UserBean {

    @EJB
    private UserServiceLocal userService;
    HtmlInputText pseudoHtml;
    HtmlInputSecret passWordHtml;
    HtmlOutputLabel labelError;
    private String pseudo;
    private String password;
    private String password2;
    private String email;
    private Date dateOfBirth;
    private boolean longin = false;
    private long id;

    public HtmlInputSecret getPassWordHtml() {
        return passWordHtml;
    }

    public void setPassWordHtml(HtmlInputSecret passWordHtml) {
        this.passWordHtml = passWordHtml;
    }

    public HtmlInputText getPseudoHtml() {
        return pseudoHtml;
    }

    public void setPseudoHtml(HtmlInputText pseudoHtml) {
        this.pseudoHtml = pseudoHtml;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HtmlOutputLabel getLabelError() {
        return labelError;
    }

    public void setLabelError(HtmlOutputLabel labelError) {
        this.labelError = labelError;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLongin() {
        return longin;
    }

    public void setLongin(boolean longin) {
        this.longin = longin;
    }

    public UserServiceLocal getUserService() {
        return userService;
    }

    public void setUserService(UserServiceLocal userService) {
        this.userService = userService;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    /** Creates a new instance of UserBean */
    public UserBean() {
    }

    public String login() {
        this.labelError.setValue("");
        User user = this.userService.getUserByPseudo(pseudo);
       
        if (user == null) {
            this.labelError.setValue("Ce compte n'existe pas");
            return "inscription";
        }

         id = user.getId();

        if (user.getPassword().equals(this.password)) {
            this.labelError.setValue("connecté !");
            this.setLongin(true);
            refresh();
            return "";
        }

        this.labelError.setValue("Ce compte n'existe pas");
        return "inscription";
    }

    public String register() {
        if (!password.equals(password2)) {
            this.labelError.setValue("Les mots de passe ne se correspondent pas !");
            return "";
        }

        User user = this.userService.getUserByPseudo(pseudo);
        if (user != null) {
            this.labelError.setValue("ce compte existe déjà !");
            return "";
        }

        this.labelError.setValue("");
        user = new User();
        user.setUserName(pseudo);
        user.setPassword(password);
        user.setEmail(email);
        user.setDateOfBirth(dateOfBirth);
        this.userService.persistuser(user);
        login();
        return "index";
    }

    public String disconnect() {
        this.labelError.setValue("");
        this.setLongin(false);
        refresh();
        return "index";
    }

    public void refresh() {
        this.labelError.setValue("");
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }
}
