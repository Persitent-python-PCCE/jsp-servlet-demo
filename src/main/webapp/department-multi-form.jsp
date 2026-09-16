<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Multi-Department Insert (CMT)</title>
</head>
<body>
    <center>
        <h1>Container Managed Transactions</h1>
        <h2>
            <a href="${pageContext.request.contextPath}/department/list">Back to List</a>
        </h2>
        <p>If you leave the second department's name empty, it will throw an error and rollback the ENTIRE transaction. The first department will NOT be saved.</p>
    </center>
    <div align="center">
        <form action="${pageContext.request.contextPath}/department/insert-multi" method="post">
        <table border="1" cellpadding="5">
            <caption><h2>Insert Multiple Departments</h2></caption>
            <tr>
                <th colspan="2" style="background-color: lightblue;">Department 1 (Valid)</th>
            </tr>
            <tr>
                <th>Name 1: </th>
                <td><input type="text" name="name1" size="45" value="Department 1" required/></td>
            </tr>
            <tr>
                <th>Description 1: </th>
                <td><input type="text" name="description1" size="45" value="Will be rolled back if Dept 2 fails" required/></td>
            </tr>
            
            <tr>
                <th colspan="2" style="background-color: lightcoral;">Department 2 (Failure Trigger)</th>
            </tr>
            <tr>
                <th>Name 2: </th>
                <td><input type="text" name="name2" size="45" placeholder="Leave empty to trigger rollback!" /></td>
            </tr>
            <tr>
                <th>Description 2: </th>
                <td><input type="text" name="description2" size="45" value="Some description"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Execute Transaction" />
                </td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>
