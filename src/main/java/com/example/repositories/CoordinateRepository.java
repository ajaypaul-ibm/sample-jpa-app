package com.example.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import java.util.List;
import com.example.model.Package;

public class CoordinateRepository {

    private final EntityManager entityManager;

    public CoordinateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void updatePackage() {
        // Begin transaction
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            List<Package> tallToShort = entityManager
                    .createQuery("SELECT o FROM Package o WHERE (o.height < :height) ORDER BY o.height DESC, o.length",
                            Package.class)
                    .setParameter("height", 8.0)
                    // .setLockMode(LockModeType.PESSIMISTIC_WRITE) // Uncomment if needed
                    .setMaxResults(2)
                    .getResultList();

            // Print the list of packages
            System.out.println("tall to short:");
            for (Package pkg : tallToShort) {
                System.out.println(pkg); // This will call the toString() method of the Package class
            }

            // Remove entities
            for (Package pkg : tallToShort) {
                if (pkg != null) {
                    entityManager.remove(entityManager.contains(pkg) ? pkg : entityManager.merge(pkg));
                }
            }

            // Commit transaction
            transaction.commit();
        } catch (Exception e) {
            // Rollback in case of an exception
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Re-throw the exception after rollback
        }
    }

    //Test case for deletion of wide packages
    public void deleteWidePackages(float widthThreshold) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
    
        try {
            List<Package> widePackages = entityManager
                    .createQuery("SELECT p FROM Package p WHERE p.width > :width", Package.class)
                    .setParameter("width", widthThreshold)
                    .getResultList();
    
            System.out.println("Wide packages to delete:");
            for (Package pkg : widePackages) {
                System.out.println(pkg);
                entityManager.remove(entityManager.contains(pkg) ? pkg : entityManager.merge(pkg));
            }
    
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public void printTallPackages(float minHeight) {
        List<Package> tallPackages = entityManager
                .createQuery("SELECT p FROM Package p WHERE p.height > :height ORDER BY p.height DESC", Package.class)
                .setParameter("height", minHeight)
                .getResultList();
    
        System.out.println("Tall packages:");
        for (Package pkg : tallPackages) {
            System.out.println(pkg);
        }
    }
    
    // Other methods as needed
}
