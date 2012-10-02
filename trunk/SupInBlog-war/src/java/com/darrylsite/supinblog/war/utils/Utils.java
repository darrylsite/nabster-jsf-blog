/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darrylsite.supinblog.war.utils;

import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author nabster
 */
@ManagedBean
public class Utils {

    public static <T> T findBean(String beanName, Class<T> beanClass) {
        FacesContext context = FacesContext.getCurrentInstance();
        return beanClass.cast(context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", beanClass));
    }

    public static void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }

    public static void redirect(String page) {
//        try
//        {
//                    FacesContext facesContext = FacesContext.getCurrentInstance();
//
//                    UIViewRoot uiViewRoot = facesContext.getApplication().getViewHandler().createView(facesContext, page);
//                    facesContext.renderResponse();
//                    facesContext.renderResponse();
//        } 
//            catch (IOException ex)
//            {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
