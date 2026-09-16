<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Student Management Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
        <div>
            <a href="${pageContext.request.contextPath}/" class="navbar-brand"> Student Management App</a>
        </div>
    </nav>
</header>
<br>

<div class="container">
    <h3 class="text-center">List of Students</h3>
    <hr>
    <div class="container text-left">
        <a href="${pageContext.request.contextPath}/new" class="btn btn-success">Add New Student</a>
    </div>
    <br>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Age</th>
            <th>Department ID</th>
            <th>Course</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="student" items="${listStudent}">
            <tr>
                <td><c:out value="${student.id}" /></td>
                <td><c:out value="${student.name}" /></td>
                <td><c:out value="${student.email}" /></td>
                <td><c:out value="${student.age}" /></td>
                <td><c:out value="${student.departmentId}" /></td>
                <td><c:out value="${student.course}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/edit?id=<c:out value='${student.id}' />">Edit</a> &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${pageContext.request.contextPath}/delete?id=<c:out value='${student.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
