

import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class GetEmployeeWithProject {
    static final String GET_EMPLOYEE_BY_ID = "SELECT e FROM Employee e WHERE e.id = :idInput";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        int idToLookingFor = Integer.parseInt(scanner.nextLine());

        entityManager.getTransaction().begin();

        List<Employee> employees = entityManager.createQuery(GET_EMPLOYEE_BY_ID,Employee.class).setParameter("idInput", idToLookingFor).getResultList();

        for (Employee employee : employees) {
            System.out.println(employee);

        }

        entityManager.close();
    }

}
