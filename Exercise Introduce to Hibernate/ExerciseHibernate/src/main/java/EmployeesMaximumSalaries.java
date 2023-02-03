import entities.Department;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class EmployeesMaximumSalaries {
    static final String MAX_SALARY = "SELECT d FROM Department AS d GROUP BY d.name HAVING SUM(d.manager.salary) NOT BETWEEN 30000 AND 70000";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Department> departmentList = entityManager.createQuery(MAX_SALARY, Department.class).getResultList();

        for (Department department : departmentList) {
            System.out.printf("%s %.2f%n",department.getName(),(department.getManager().getSalary()));
        }

        entityManager.getTransaction().begin();

        entityManager.close();
    }

}
