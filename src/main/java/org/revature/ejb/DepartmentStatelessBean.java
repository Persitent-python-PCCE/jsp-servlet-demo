package org.revature.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.transaction.Transactional;
import org.revature.dao.DepartmentDAO;
import org.revature.model.Department;

import java.sql.SQLException;
import java.util.List;

@Stateless//bean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DepartmentStatelessBean {

    private DepartmentDAO departmentDAO;

    @Resource
    private SessionContext sessionContext; 

    public DepartmentStatelessBean() {
        this.departmentDAO = new DepartmentDAO();
    }
//    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)//propagation strategy
    public void insertDepartment(Department department) throws SQLException {
        departmentDAO.insertDepartment(department);

    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Department selectDepartment(int id) {
        //service layer logic
        return departmentDAO.selectDepartment(id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void doTransaction() throws SQLException{
        selectAllDepartments();

        deleteDepartment(1);
        updateDepartment(new Department());

    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Department> selectAllDepartments() {
        return departmentDAO.selectAllDepartments();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteDepartment(int id) throws SQLException {
        return departmentDAO.deleteDepartment(id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean updateDepartment(Department department) throws SQLException {
        return departmentDAO.updateDepartment(department);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insertMultipleDepartments(Department dept1, Department dept2) {
        try {
            departmentDAO.insertDepartment(dept1);
            
            if (dept2.getName() == null || dept2.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Department 2 name cannot be empty!");
            }

            departmentDAO.insertDepartment(dept2);

        } catch (Exception e) {
            System.err.println("Error inserting departments, rolling back entire transaction: " + e.getMessage());
            
            sessionContext.setRollbackOnly(); 
        }
    }
}
