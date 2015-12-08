/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.phreebie.testfreemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ilya
 */
public class test extends HttpServlet {
    public static Configuration cfg;

    @Override
    public void init() throws ServletException {
        
        Configuration localInstance;
        if (cfg == null) {
		synchronized (Configuration.class) {
                    localInstance = cfg;
                    if (localInstance == null) {
			cfg = localInstance = new Configuration(Configuration.VERSION_2_3_22);
                    }
		}
	}

        try {
            cfg.setDirectoryForTemplateLoading(new File("C:\\src_java\\TestFreemarker\\freemarker\\templates"));
        } catch (Exception e){
            throw new ServletException(e);
        }

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * (root)
            |
            +- user = "Big Joe"
            |
            +- latestProduct
                |
                +- url = "products/greenmouse.html"
                |
                +- name = "green mouse" 
         */
        Map<String, Object> root = new HashMap<>();
        root.put("user", "Big Joe");
        Map<String, Object> latest = new HashMap<>();
        root.put("latestProduct1", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");
        
        Product latestProducts = getLatestProductFromDatabaseOrSomething();
        root.put("latestProduct", latestProducts);
        
        Template temp = cfg.getTemplate("test.ftl");
        
        //Writer out = new OutputStreamWriter(System.out);
        //temp.process(root, out);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try{
                temp.process(root, out);
            } catch (TemplateException te){
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet test</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Error</h1>");
                out.println(te.getMessage());
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

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
        return "Short description";
    }

    private Product getLatestProductFromDatabaseOrSomething() {
        return new Product("test_product","test_product");
    }

}
