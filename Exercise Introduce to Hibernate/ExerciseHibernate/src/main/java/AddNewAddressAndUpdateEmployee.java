import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class AddNewAddressAndUpdateEmployee {

    static final String ADD_ADDRESS_TO_GIVEN_EMPLOYEE = "UPDATE Employee AS e SET e.address = :newAddress WHERE e.lastName = :ln";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        String lastNameInput = scanner.nextLine();

        entityManager.getTransaction().begin();

        Address newAddress = new Address();
        newAddress.setText("Vitoshka 15");

        entityManager.persist(newAddress);


        int countUpdates = entityManager.createQuery(ADD_ADDRESS_TO_GIVEN_EMPLOYEE).setParameter("newAddress", newAddress)
                .setParameter("ln", lastNameInput).executeUpdate();

        if(countUpdates > 0){
            entityManager.getTransaction().commit();
        }else{
            entityManager.getTransaction().rollback();
        }



        entityManager.close();
    }

}
