import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class FindEmployeesByFirstName {

    static final String FIND_EMPLOYEE_BY_STRING = "SELECT e FROM Employee AS e WHERE e.firstName LIKE :pattern";
    static final String PRINT_FORMAT = "%s %s - %s - ($%.2f)%n";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();

        entityManager.getTransaction().begin();

        List<Employee> employeeListResult = entityManager.createQuery(FIND_EMPLOYEE_BY_STRING, Employee.class).setParameter("pattern", pattern + "%").getResultList();

        for (Employee employee : employeeListResult) {
            System.out.printf(PRINT_FORMAT,employee.getFirstName(),employee.getLastName(),employee.getJobTitle(),employee.getSalary());
        }

        entityManager.close();
    }

}
