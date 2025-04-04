package org.example;

import com.example.model.Coordinate;
import com.example.model.MyEntity;
import com.example.model.Package;
import com.example.repositories.CoordinateRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Package p1 = Package.of(1, 17.0f, 17.1f, 7.7f, "first package"); // tallest and smallest length
        Package p2 = Package.of(2, 77.0f, 17.7f, 7.7f, "second package"); // tallest and largest length
        Package p3 = Package.of(3, 70.0f, 10.7f, 0.7f, "third package");
        if (em.find(Package.class, p1.id) == null) {
            em.persist(p1);
        }
        if (em.find(Package.class, p2.id) == null) {
            em.persist(p2);
        }
        if (em.find(Package.class, p3.id) == null) {
            em.persist(p3);
        }
        // em.persist(p2);
        // em.persist(p3);

        // Coordinate coordinate = new Coordinate();
        // coordinate.setX(10);
        // coordinate.setY(15);
        // em.persist(coordinate);

        em.getTransaction().commit();

        // MyEntity foundEntity = em.find(MyEntity.class, entity.getId());
        // System.out.println("Found entity: " + foundEntity.getName());
        // Update the coordinate
        CoordinateRepository coordinateRepository = new CoordinateRepository(em);
        coordinateRepository.updatePackage();
        em.close();
        emf.close();
    }
}