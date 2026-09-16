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
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${student != null}">
            <form action="${pageContext.request.contextPath}/update" method="post">
                </c:if>
                <c:if test="${student == null}">
                <form action="${pageContext.request.contextPath}/insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${student != null}">
                                Edit Student
                            </c:if>
                            <c:if test="${student == null}">
                                Add New Student
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${student != null}">
                        <input type="hidden" name="id" value="<c:out value='${student.id}' />" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>Student Name</label> <input type="text" value="<c:out value='${student.name}' />" class="form-control" name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Student Email</label> <input type="email" value="<c:out value='${student.email}' />" class="form-control" name="email" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Student Age</label> <input type="number" value="<c:out value='${student.age}' />" class="form-control" name="age" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Department ID</label> <input type="number" value="<c:out value='${student.departmentId}' />" class="form-control" name="departmentId" required="required">
                    </fieldset>
                    
                    <fieldset class="form-group">
                        <label>Course</label> <input type="text" value="<c:out value='${student.course}' />" class="form-control" name="course" required="required">
                    </fieldset>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>
</html>
