package org.revature.dao;

import org.revature.model.Student;
import org.revature.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String INSERT_STUDENTS_SQL = "INSERT INTO students (name, email, age, department_id, course) VALUES (?, ?, ?, ?, ?);";//SQL-Injection
    private static final String SELECT_STUDENT_BY_ID = "SELECT id, name, email, age, department_id, course FROM students WHERE id =?;";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM students;";
    private static final String DELETE_STUDENTS_SQL = "DELETE FROM students WHERE id = ?;";
    private static final String UPDATE_STUDENTS_SQL = "UPDATE students SET name = ?, email= ?, age =?, department_id =?, course =? WHERE id = ?;";
//JDBC -> Connection, PreparedStatement, Statement, CallableStatement, Resultset(Select)
    public void insertStudent(Student student) throws SQLException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENTS_SQL)) {//try with resources
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setInt(4, student.getDepartmentId());
            preparedStatement.setString(5, student.getCourse());
            preparedStatement.executeUpdate();
        }
    }

    public Student selectStudent(int id) {
        Student student = null;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                int departmentId = rs.getInt("department_id");
                String course = rs.getString("course");
                student = new Student(id, name, email, age, departmentId, course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public List<Student> selectAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                int departmentId = rs.getInt("department_id");
                String course = rs.getString("course");
                students.add(new Student(id, name, email, age, departmentId, course));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean deleteStudent(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENTS_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateStudent(Student student) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENTS_SQL)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setInt(3, student.getAge());
            statement.setInt(4, student.getDepartmentId());
            statement.setString(5, student.getCourse());
            statement.setInt(6, student.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
