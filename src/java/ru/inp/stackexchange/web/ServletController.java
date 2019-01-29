package ru.inp.stackexchange.web;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.inp.stackexchange.AppConfig;

/**
 * Single controller for all client requests.
 */
public class ServletController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String codePage = "page.null";
        ActionContext context = new ActionContext(request, response);
        try {            
            Action action = ActionFactory.getAction(context);
            codePage = action.execute(context);
            
            doForward(context, codePage);            
        } catch(Exception e) {
            doRedirect(context, codePage);
        }
    }
    
    private void doForward(ActionContext context, String codePage) throws ServletException, IOException{
        RequestDispatcher dispatcher = context.getRequest().getRequestDispatcher(getPageAddress(codePage));
        dispatcher.forward(context.getRequest(), context.getResponse()); 
    }
    
    private void doRedirect(ActionContext context, String codePage) throws ServletException, IOException{
        context.getResponse().sendRedirect(getPageAddress(codePage));
    }
    
    private String getPageAddress(String codePage) {
        if(codePage == null)
            codePage = "page.null";        
        
        String address = AppConfig.get(codePage);
        if(address == null)
            address = AppConfig.get("page.null");
        if(address == null)
            address = "/";
        
        return address;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet Controller";
    }// </editor-fold>

}
