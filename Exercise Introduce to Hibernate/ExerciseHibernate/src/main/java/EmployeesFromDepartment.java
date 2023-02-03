import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class EmployeesFromDepartment {
    static final String EMPLOYEE_INFO = "SELECT e FROM Employee e WHERE e.department.id = 6" +
            " ORDER by e.salary,e.id";
    static final String PRINT_FORMAT = "%s %s from Research and Development - $%.2f%n";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> employeeResultList = entityManager.createQuery(EMPLOYEE_INFO, Employee.class).getResultList();

       employeeResultList.forEach(e  -> System.out.printf(PRINT_FORMAT,e.getFirstName(),e.getLastName(),e.getSalary()));


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
