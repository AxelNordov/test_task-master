package test_task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.dao.EmployeeDao;
import test_task.model.Employee;
import test_task.service.EmployeeService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAllBySalaryGreaterThatBoss() {
        return employeeDao.findAllWhereSalaryGreaterThatBoss();
    }

    @Override
    public List<Employee> findAllByMaxSalary() {
        return employeeDao.findAllByMaxSalary();
    }

    @Override
    public List<Employee> findAllWithoutBoss() {
        return employeeDao.findAllWithoutBoss();
    }

    @Override
    public Long fireEmployee(String name) {
        Iterable<Employee> employees = employeeDao.findAll();

        //TODO Implement method using Collection
        // ---write your code here
        Long id = 0L;
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (name.equals(employee.getName())) {
                id = employee.getId();
                iterator.remove();
                employeeDao.deleteById(id);
            }
        }

        employeeDao.saveAll(employees);
        return id;
    }

    @Override
    public Long changeSalary(String name) {
        Iterable<Employee> employees = employeeDao.findAll();

        //TODO Implement method using Collection
        // ---write your code here
        Long id = 0L;
        for (Employee employee : employees) {
            if (name.equals(employee.getName())) {
                employee.setSalary(employee.getSalary().multiply(new BigDecimal(2)));
                id = employee.getId();
            }
        }

        employeeDao.saveAll(employees);
        return id;
    }

    @Override
    public Long hireEmployee(Employee employee) {
        //TODO Implement method using Collection and DAO
        // ---write your code here
        return employeeDao.save(employee).getId();
    }
}
