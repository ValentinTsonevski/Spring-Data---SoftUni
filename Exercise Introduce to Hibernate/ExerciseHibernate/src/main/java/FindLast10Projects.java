import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.List;

public class FindLast10Projects {

    static final String LAST_10_PROJECTS = "SELECT p FROM Project p ORDER BY p.startDate DESC";


    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(LAST_10_PROJECTS, Project.class).setMaxResults(10).getResultList().stream()
                .sorted(Comparator.comparing(Project::getName)).forEach(System.out::println);


        entityManager.close();
    }

}
