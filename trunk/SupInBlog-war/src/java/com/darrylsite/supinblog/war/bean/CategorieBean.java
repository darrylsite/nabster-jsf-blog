package com.darrylsite.supinblog.war.bean;

import com.darrylsite.supinblog.model.Category;
import com.darrylsite.supinblog.model.session.local.CategoryServiceLocal;
import com.darrylsite.supinblog.model.Post;
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
public class CategorieBean {

    @EJB
    private CategoryServiceLocal categoryService;
    private Category categorie;
    private boolean modif = false;
    private String catId;
    private String name;
    private String desc;

    /** Creates a new instance of CategorieBean */
    public CategorieBean() {
    }

    public Category getCategorie() {
        return categorie;
    }

    public void setCategorie(Category categorie) {
        this.categorie = categorie;
    }

    public String getCatId() {
        return catId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public boolean isModif() {
        return modif;
    }

    public boolean getModif() {
        return modif;
    }

    public void setModif(boolean modif) {
        this.modif = modif;
    }

    public String createCategory() {
        Category cat = new Category();
        cat.setName(name);
        cat.setDescription(desc);
        categoryService.persistCategory(cat);
        return "afficheCategories";
    }

    public String modifyCategory() {
        Category cat = new Category();
        cat.setId(Long.parseLong(catId));
        cat.setName(name);
        cat.setDescription(desc);
        categoryService.mergeCategory(cat);
        return "afficheCategories";
    }

    public List<Post> getAllPost() {
        if (catId == null || catId.isEmpty()) {
            return null;
        }

        Category cat = categoryService.getCategoryById(Long.parseLong(catId));
        if (cat == null) {
            return null;
        }
        return cat.getPosts();
    }

    public void refreshCategorie() {
        Category cat = categoryService.getCategoryById(Long.parseLong(catId));
        this.name = cat.getName();
        this.desc = cat.getDescription();
    }

}
