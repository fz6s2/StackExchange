package ru.inp.stackexchange.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Context for client requests.
 */
public class ActionContext {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private String codePage;
    
    public ActionContext(HttpServletRequest aRequest, HttpServletResponse aResponse){
        request = aRequest;
        response = aResponse;    
    };       
    
    public void setError(String text) {
        if(text == null)
            return;
        
        request.setAttribute("error", text);
    }
    
    public String getError() {
        String err = "";
        try {
            err = (String )request.getAttribute("error");
        } catch(Exception e) {}
        return err;
    }
    
    public HttpSession getSession() {
        return request.getSession();
    }
    
    public String getParameter(String name) {
        return request.getParameter(name);
    }
    
    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }        
    
    public void setAttribute(String name, Object obj) {
        request.setAttribute(name, obj);
    }
    
    public <T> T getAttributeT(String name) {
        try{
            return (T)request.getAttribute(name);
        } catch( Exception e) {}
        
        return null;
    }

    public void setSessionAttribute(String name, Object o) {
        getSession().setAttribute(name, o);
    }
    
    public Object getSessionAttribute(String name) {
        return getSession().getAttribute(name);
    }
    
    public <T> T getSessionAttributeT(String name) {
        try{
            return (T)getSession().getAttribute(name);
        } catch( Exception e) {}
        
        return null;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }
    
    public HttpServletResponse getResponse() {
        return response;
    }
}
