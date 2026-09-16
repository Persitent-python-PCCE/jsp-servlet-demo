package org.revature.ejb;

import jakarta.ejb.Stateful;
import org.revature.model.Department;

import java.util.ArrayList;
import java.util.List;

@Stateful
public class DepartmentStatefulBean {

    private List<Department> recentlyViewedDepartments;//store

    public DepartmentStatefulBean() {
        this.recentlyViewedDepartments = new ArrayList<>();
    }

    public void addRecentlyViewed(Department department) {
        if (department != null) {
            boolean exists = recentlyViewedDepartments.stream()
                                .anyMatch(d -> d.getId() == department.getId());
            if (!exists) {
                recentlyViewedDepartments.add(department);
            }
        }
    }

    public List<Department> getRecentlyViewedDepartments() {
        return recentlyViewedDepartments;
    }
    
    public void clearRecentlyViewed() {
        recentlyViewedDepartments.clear();
    }
}
