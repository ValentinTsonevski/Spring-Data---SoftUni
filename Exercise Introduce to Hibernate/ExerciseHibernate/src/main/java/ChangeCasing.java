import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class ChangeCasing {
    static final String ALL_TOWNS_NAMES = "SELECT t FROM Town AS t WHERE LENGTH(t.name) <= 5";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Town> townsList = entityManager.createQuery(ALL_TOWNS_NAMES, Town.class).getResultList();

        for (Town town : townsList) {
            if (town.getName().length() <= 5) {
                town.setName(town.getName().toUpperCase());
                entityManager.persist(town);
            }else{
                entityManager.detach(town);
            }

        }


        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
