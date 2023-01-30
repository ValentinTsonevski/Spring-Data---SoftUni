import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class IncreaseSalaries {

    static final String UPDATE_SALARIES = "UPDATE Employee AS e SET e.salary = e.salary * 1.12 WHERE e.department.id IN(1,2,4,11)";
    static final String UPDATED_EMPLOYEES = "SELECT e FROM Employee AS e WHERE e.department.id IN(1,2,4,11)";
    static final String PRINT_FORMAT = "%s %s ($%.2f)%n";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        int countUpdates = entityManager.createQuery(UPDATE_SALARIES).executeUpdate();

        if(countUpdates > 0){
            entityManager.getTransaction().commit();
            List<Employee> updatedListToPrint = entityManager.createQuery(UPDATED_EMPLOYEES, Employee.class).getResultList();

            for (Employee e : updatedListToPrint) {
                System.out.printf(PRINT_FORMAT,e.getFirstName(),e.getLastName(),e.getSalary());
            }

        }else{
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
    }

}
