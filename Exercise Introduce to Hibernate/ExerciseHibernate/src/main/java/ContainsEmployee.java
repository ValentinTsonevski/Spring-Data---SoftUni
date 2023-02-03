import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class ContainsEmployee {

    static  final  String CONTAIN_EMPLOYEE_NAME = "SELECT COUNT(e) FROM Employee AS e WHERE e.firstName = :fn AND e.lastName =:ln";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        String[] inputName = scanner.nextLine().split(" ");
        String firstName = inputName[0];
        String lastName = inputName[1];

        entityManager.getTransaction().begin();

        Long matchedCount = entityManager.createQuery(CONTAIN_EMPLOYEE_NAME, Long.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();

        if(matchedCount > 0){
            System.out.println("Yes");
        }else{
            System.out.println("No");
        }


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
