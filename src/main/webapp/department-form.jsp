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
            <a href="${pageContext.request.contextPath}/department/list">List All Departments</a>
        </h2>
    </center>
    <div align="center">
        <c:if test="${department != null}">
            <form action="${pageContext.request.contextPath}/department/update" method="post">
        </c:if>
        <c:if test="${department == null}">
            <form action="${pageContext.request.contextPath}/department/insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${department != null}">
                        Edit Department
                    </c:if>
                    <c:if test="${department == null}">
                        Add New Department
                    </c:if>
                </h2>
            </caption>
            <c:if test="${department != null}">
                <input type="hidden" name="id" value="<c:out value='${department.id}' />" />
            </c:if>
            <tr>
                <th>Name: </th>
                <td>
                    <input type="text" name="name" size="45"
                           value="<c:out value='${department.name}' />" required/>
                </td>
            </tr>
            <tr>
                <th>Description: </th>
                <td>
                    <input type="text" name="description" size="45"
                           value="<c:out value='${department.description}' />" required/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>
