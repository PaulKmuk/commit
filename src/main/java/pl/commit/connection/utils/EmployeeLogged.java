package pl.commit.connection.utils;

import pl.commit.connection.model.Employee;

public class EmployeeLogged {

    private static Employee employee;

    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        EmployeeLogged.employee = employee;
    }
}
