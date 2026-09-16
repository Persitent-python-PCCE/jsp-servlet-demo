<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Department Management Application</title>
</head>
<body>
    <center>
        <h1>Department Management</h1>
        <h2>
            <a href="${pageContext.request.contextPath}/department/new">Add New Department</a>
            &nbsp;&nbsp;&nbsp;
            <a href="${pageContext.request.contextPath}/department/new-multi">CMT Multi-Insert</a>
            &nbsp;&nbsp;&nbsp;
            <a href="${pageContext.request.contextPath}/department/list">List All Departments</a>
        </h2>
        <c:if test="${not empty message}">
            <h3 style="color: blue;"><c:out value="${message}"/></h3>
            <p>Check the list below to verify if the departments were added, or if the entire transaction was safely rolled back.</p>
        </c:if>
        <c:if test="${not empty error}">
            <h3 style="color: red;"><c:out value="${error}"/></h3>
        </c:if>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Departments</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="department" items="${listDepartment}">
                <tr>
                    <td><c:out value="${department.id}" /></td>
                    <td><c:out value="${department.name}" /></td>
                    <td><c:out value="${department.description}" /></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/department/edit?id=<c:out value='${department.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="${pageContext.request.contextPath}/department/delete?id=<c:out value='${department.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <c:if test="${not empty recentlyViewed}">
            <br><br>
            <table border="1" cellpadding="5">
                <caption><h2>Recently Viewed Departments</h2></caption>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                </tr>
                <c:forEach var="dept" items="${recentlyViewed}">
                    <tr>
                        <td><c:out value="${dept.id}" /></td>
                        <td><c:out value="${dept.name}" /></td>
                        <td><c:out value="${dept.description}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</body>
</html>
