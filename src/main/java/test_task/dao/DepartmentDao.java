package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Department;

import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
    //TODO Get a list of department IDS where the number of employees doesn't exceed 3 people
    @Query(
            value = "select id " +
                    "from (select d.id, count(*) count " +
                    "      from department d " +
                    "               join employee e on d.id = e.department_id " +
                    "      group by d.id) as res " +
                    "where count <= 3",
            nativeQuery = true)
    List<Long> findAllWhereDepartmentDoesntExceedThreePeople();

    //TODO Get a list of departments IDs with the maximum total salary of employees
    @Query(
            value = "select id " +
                    "from (select e.department_id as id, sum(salary) salary_sum from employee e group by e.department_id) as sum1 " +
                    "         join " +
                    "     (select max(salary_sum) salary_max " +
                    "      from (select sum(salary) salary_sum from employee e group by e.department_id) as sum2) as sum3 " +
                    "     on sum1.salary_sum = sum3.salary_max",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
