package org.revature.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.revature.ejb.DepartmentStatefulBean;
import org.revature.ejb.DepartmentStatelessBean;
import org.revature.model.Department;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebServlet("/department/*")
public class DepartmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private DepartmentStatelessBean departmentStatelessBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String action = pathInfo != null ? pathInfo : "/list";

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertDepartment(request, response);
                    break;
                case "/delete":
                    deleteDepartment(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateDepartment(request, response);
                    break;
                case "/jndi-info":
                    showJNDILookup(request, response);
                    break;
                case "/new-multi":
                    showMultiForm(request, response);
                    break;
                case "/insert-multi":
                    insertMultipleDepartments(request, response);
                    break;
                default:
                    listDepartment(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        if (departmentStatelessBean == null) {
            departmentStatelessBean = new DepartmentStatelessBean();
        }

        List<Department> listDepartment = departmentStatelessBean.selectAllDepartments();
        request.setAttribute("listDepartment", listDepartment);
        
        DepartmentStatefulBean statefulBean = getStatefulBean(request);
        request.setAttribute("recentlyViewed", statefulBean.getRecentlyViewedDepartments());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/department-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/department-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        if (departmentStatelessBean == null) {
            departmentStatelessBean = new DepartmentStatelessBean();
        }
        Department existingDepartment = departmentStatelessBean.selectDepartment(id);

        if (existingDepartment != null) {
            DepartmentStatefulBean statefulBean = getStatefulBean(request);
            statefulBean.addRecentlyViewed(existingDepartment);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/department-form.jsp");
        request.setAttribute("department", existingDepartment);
        dispatcher.forward(request, response);
    }

    private void insertDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        Department newDepartment = new Department(name, description);

        if (departmentStatelessBean == null) {
            departmentStatelessBean = new DepartmentStatelessBean();
        }
        departmentStatelessBean.insertDepartment(newDepartment);
        response.sendRedirect(request.getContextPath() + "/department/list");
    }

    private void updateDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Department department = new Department(id, name, description);
        
        if (departmentStatelessBean == null) {
            departmentStatelessBean = new DepartmentStatelessBean();
        }
        departmentStatelessBean.updateDepartment(department);
        response.sendRedirect(request.getContextPath() + "/department/list");
    }

    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        if (departmentStatelessBean == null) {
            departmentStatelessBean = new DepartmentStatelessBean();
        }
        departmentStatelessBean.deleteDepartment(id);
        response.sendRedirect(request.getContextPath() + "/department/list");
    }

    private void showJNDILookup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h2>JNDI Lookup</h2>");

        try {
            InitialContext ctx = new InitialContext();

            String jndiPath = "java:module/DepartmentStatelessBean";
            out.println("<p>Attempting to look up EJB programmatically at: <strong>" + jndiPath + "</strong></p>");

            DepartmentStatelessBean beanFromJNDI = (DepartmentStatelessBean) ctx.lookup(jndiPath);
            
            if (beanFromJNDI != null) {
                out.println("<p style='color:green;'>Successfully retrieved DepartmentStatelessBean via JNDI!</p>");
                out.println("<p>Total Departments (fetched via JNDI bean): " + beanFromJNDI.selectAllDepartments().size() + "</p>");
            }

        } catch (NamingException e) {
            out.println("<p style='color:red;'>JNDI Lookup Failed</p>");
            out.println("<p><em>Note: This is expected if you are running in plain Tomcat, as it does not have an EJB container or java:module namespace by default.</em></p>");
            out.println("<pre>Exception: " + e.getMessage() + "</pre>");
        } catch (Exception e) {
            out.println("<p style='color:red;'>An error occurred during lookup: " + e.getMessage() + "</p>");
        }

        out.println("<br><a href='" + request.getContextPath() + "/department/list'>Back to List</a>");
        out.println("</body></html>");
    }

    private void showMultiForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/department-multi-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertMultipleDepartments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name1 = request.getParameter("name1");
        String desc1 = request.getParameter("description1");
        String name2 = request.getParameter("name2");
        String desc2 = request.getParameter("description2");
        
        Department dept1 = new Department(name1, desc1);
        Department dept2 = new Department(name2, desc2);

        if (departmentStatelessBean == null) {
            departmentStatelessBean = new DepartmentStatelessBean();
        }
        
        try {
            departmentStatelessBean.insertMultipleDepartments(dept1, dept2);
            request.setAttribute("message", "Attempted to execute the multi-insert transaction!");
        } catch (Exception e) {
            request.setAttribute("error", "Transaction Failed unexpectedly: " + e.getMessage());
        }
        
        try {
            listDepartment(request, response);
        } catch(SQLException e) {
            throw new ServletException(e);
        }
    }

    private DepartmentStatefulBean getStatefulBean(HttpServletRequest request) {
        HttpSession session = request.getSession(true);//retrieve session / create
        DepartmentStatefulBean statefulBean = (DepartmentStatefulBean) session.getAttribute("departmentStatefulBean");
        
        if (statefulBean == null) {
            try {
                InitialContext ctx = new InitialContext();
                statefulBean = (DepartmentStatefulBean) ctx.lookup("java:module/DepartmentStatefulBean");//JNDI lookups
            } catch (Exception e) {
                statefulBean = new DepartmentStatefulBean();
            }
            session.setAttribute("departmentStatefulBean", statefulBean);
        }
        return statefulBean;
    }
}
