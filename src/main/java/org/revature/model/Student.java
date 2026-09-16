package org.revature.model;

//POJO
public class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    private int departmentId;
    private String course;

    public Student() {
    }

    public Student(String name, String email, int age, int departmentId, String course) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.departmentId = departmentId;
        this.course = course;
    }

    public Student(int id, String name, String email, int age, int departmentId, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.departmentId = departmentId;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
