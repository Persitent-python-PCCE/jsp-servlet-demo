package org.revature.dao;

import jakarta.ejb.Stateless;
import org.revature.model.Department;
import org.revature.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private static final String INSERT_DEPARTMENT_SQL = "INSERT INTO departments (name, description) VALUES (?, ?);";
    private static final String SELECT_DEPARTMENT_BY_ID = "SELECT id, name, description FROM departments WHERE id = ?;";
    private static final String SELECT_ALL_DEPARTMENTS = "SELECT * FROM departments;";
    private static final String DELETE_DEPARTMENT_SQL = "DELETE FROM departments WHERE id = ?;";
    private static final String UPDATE_DEPARTMENT_SQL = "UPDATE departments SET name = ?, description = ? WHERE id = ?;";

    public void insertDepartment(Department department) throws SQLException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEPARTMENT_SQL)) {
//            connection.beginRequest();
            preparedStatement.setString(1, department.getName());
            preparedStatement.setString(2, department.getDescription());
            preparedStatement.executeUpdate();
        }
    }

    public Department selectDepartment(int id) {
        Department department = null;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DEPARTMENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                department = new Department(id, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    public List<Department> selectAllDepartments() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DEPARTMENTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                departments.add(new Department(id, name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public boolean deleteDepartment(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DEPARTMENT_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateDepartment(Department department) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DEPARTMENT_SQL)) {
            statement.setString(1, department.getName());
            statement.setString(2, department.getDescription());
            statement.setInt(3, department.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
