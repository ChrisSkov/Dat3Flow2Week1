/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entity.Customer;
import entity.Address;

/**
 *
 * @author stoff
 */
public class Tester2 {

    public static void main(String[] args)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        
        EntityManager em = emf.createEntityManager();
        Customer cust = new Customer("Karl", "efternavn");
        Address add = new Address("Satanvej", "SatanBY");
        cust.setAddress(add);
        try
        {
            em.getTransaction().begin();
            em.persist(cust);
          //  em.persist(add);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
        em = emf.createEntityManager();
        Customer found = em.find(Customer.class,cust.getId());
        System.out.println("Address " + found.getAddress().getCity());
    }
}
