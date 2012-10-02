package com.darrylsite.supinblog.war.bean;

import com.darrylsite.supinblog.war.utils.Utils;
import com.darrylsite.supinblog.model.Category;
import com.darrylsite.supinblog.model.session.local.CategoryServiceLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author nabster
 */
@ManagedBean
@RequestScoped
public class action {

    @EJB
    private CategoryServiceLocal categoryService;
    private String catId;
    private Category category;

    /** Creates a new instance of action */
    public action() {
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String gotoCategory() {
        CategorieBean bean = Utils.findBean("categorieBean", CategorieBean.class);
        bean.refreshCategorie();
        return "postlist";
    }

    public String goTonewPost() {
        return null;
    }

    public List<Category> getCategories() {
        return categoryService.getCategoryFindAll();
    }

    public String delecategory() {
        Category cat = new Category();
        cat.setId(Long.parseLong(catId));
        this.categoryService.removeCategory(cat);
        this.refresh();
        return "";
    }

    public String modifyCategory() {
        return "";
    }

    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }
}
