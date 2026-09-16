package org.revature.servlet;

import org.revature.dao.StudentDAO;
import org.revature.model.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
@WebServlet("/")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertStudent(request, response);
                    break;
                case "/delete":
                    deleteStudent(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateStudent(request, response);
                    break;
                case "/session-info":
                    showSessionInfo(request, response);
                    break;
                case "/login":
                    login(request, response);
                    break;
                case "/cookie-info":
                    showCookieInfo(request, response);
                    break;
                default:
                    listStudent(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response){
        String u = request.getParameter("username");//
        String p = request.getParameter("password");//

        HttpSession session = request.getSession(true);//JSESSIONID
        Cookie c = new Cookie("key", "cval1");
        response.addCookie(c);
        for (Cookie co: request.getCookies()){
            System.out.println(co.getName() + co.getValue());
        }
        session.setAttribute("loggesinUser", u);
//        session.invalidate();  //logout


    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Student> listStudent = studentDAO.selectAllStudents();
        request.setAttribute("listStudent", listStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDAO.selectStudent(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student-form.jsp");
        request.setAttribute("student", existingStudent);
        dispatcher.forward(request, response);

    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int age = Integer.parseInt(request.getParameter("age"));
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        String course = request.getParameter("course");
        Student newStudent = new Student(name, email, age, departmentId, course);
        studentDAO.insertStudent(newStudent);
        response.sendRedirect("list");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int age = Integer.parseInt(request.getParameter("age"));
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        String course = request.getParameter("course");
        Cookie demoCookie = new Cookie("demo", "upodation occured");
        response.addCookie(demoCookie);



        Student book = new Student(id, name, email, age, departmentId, course);
        studentDAO.updateStudent(book);
        response.sendRedirect("list");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect("list");
    }

    private void showSessionInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(true);//{"visitCount":1}
        
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        session.setAttribute("visitCount", visitCount);//update/create
        
        out.println("<html><body>");
        out.println("<h2>HttpSession Info</h2>");
        out.println("<p>Session ID: " + session.getId() + "</p>");
        out.println("<p>Creation Time: " + new Date(session.getCreationTime()) + "</p>");
        out.println("<p>Last Accessed Time: " + new Date(session.getLastAccessedTime()) + "</p>");
        out.println("<p><strong>You have visited this page " + visitCount + " time(s) during this session.</strong></p>");
        
        
        out.println("<br><a href='list'>Back to List</a>");
        out.println("</body></html>");
    }

    private void showCookieInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        Cookie userCookie = new Cookie("activeUser", "User_" + System.currentTimeMillis());
        userCookie.setMaxAge(60 * 60 * 24);


        
        response.addCookie(userCookie);
        
        out.println("<html><body>");
        out.println("<h2>Cookie Info</h2>");
        out.println("<p>A cookie named 'activeUser' has been set/updated!</p>");
        
        out.println("<h3>Cookies currently received from your browser:</h3><ul>");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                out.println("<li>" + cookie.getName() + " = " + cookie.getValue() + "</li>");
            }
        } else {
            out.println("<li>No cookies found.</li>");
        }
        out.println("</ul>");
        
        out.println("<br><a href='list'>Back to List</a>");
        out.println("</body></html>");
    }
}
