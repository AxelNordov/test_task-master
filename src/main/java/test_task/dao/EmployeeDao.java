package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    //TODO Get a list of employees receiving a salary greater than that of the boss
    @Query(
            value = "select * from employee e " +
                    "where salary > (select salary from employee where id = e.boss_id)",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    //TODO Get a list of employees receiving the maximum salary in their department
    @Query(
            value = "select e1.* from employee e1 " +
                    "    join (select e.department_id, max(e.salary) as sum " +
                    "        from employee e " +
                    "        group by e.department_id) as e2 " +
                    "        on e2.sum = e1.salary and e2.department_id = e1.department_id;",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    //TODO Get a list of employees who do not have boss in the same department
    @Query(
            value = "select e1.* from employee e1 " +
                    "where boss_id not in ( " +
                    "    select id " +
                    "    from employee e2 " +
                    "    where boss_id is null " +
                    "      and e1.department_id = e2.department_id)",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
