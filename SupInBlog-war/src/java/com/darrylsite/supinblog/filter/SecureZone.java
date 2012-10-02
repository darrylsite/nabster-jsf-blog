package com.darrylsite.supinblog.filter;

import com.darrylsite.supinblog.war.bean.UserBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nabster
 */
public class SecureZone implements Filter
{

    private FilterConfig filterConfig = null;

    public SecureZone() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getServletPath().startsWith("/pages/"))
        {
            httpResponse.sendRedirect("/SupInBlog-war/index.xhtml");
            return;
        }

        UserBean userBean = (UserBean) httpRequest.getSession().getAttribute("userBean");
        if (httpRequest.getServletPath().equals("/managePost.xhtml") || httpRequest.getServletPath().equals("/manageCategorie.xhtml"))
        {
            if ((userBean == null) || (!userBean.isLongin()))
            {
                httpResponse.sendRedirect("index.xhtml");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig()
    {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    /**
     * Init method for this filter 
     */
    @Override
    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy()
    {
        
    }
}
