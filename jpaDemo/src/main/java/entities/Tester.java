/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dtos.PersonStyleDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Alex Wagner
 */
public class Tester {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Joe", 1979);
        Person p2 = new Person("May", 1981);

        Address a1 = new Address("Leet Street", 1337, "CyberCity");
        Address a2 = new Address("Missing Street", 404, "CyberCity");

        p1.setAddress(a1);
        p2.setAddress(a2);

        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(350);

        p1.AddFee(f1);
        p2.AddFee(f2);
        p1.AddFee(f3);

        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("Butterfly");
        SwimStyle s3 = new SwimStyle("Breast stroke");

        p1.AddSwimStyle(s3);
        p1.AddSwimStyle(s2);
        p2.AddSwimStyle(s1);
        p2.AddSwimStyle(s2);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        p1.RemoveSwimStyle(s2);
        p2.RemoveSwimStyle(s2);
        em.getTransaction().commit();

        System.out.println("Missing Street befolkning = " + a2.getPerson().getName());

        System.out.println("p1 city = " + p1.getAddress().getCity());

        System.out.println("Hvem har betalt f2 = Navn: " + f2.getPerson().getName() + ". Adresse: " + f2.getPerson().getAddress().FullAdress());

        TypedQuery<Fee> q1 = em.createQuery("SELECT f FROM Fee f", Fee.class);
        List<Fee> feeList = q1.getResultList();

        for (Fee f : feeList) {
            System.out.println(f.getPerson().getName() + " har betalt " + f.getAmount() + ",- kroner");
        }

        Query q4 = em.createQuery("SELECT p.NAME, ss.STYLENAME FROM PERSON p\n"
                + "INNER JOIN SWIMSTYLE_PERSON sp\n"
                + "ON p.P_ID = sp.persons_P_ID\n"
                + "INNER JOIN SWIMSTYLE ss\n"
                + "ON sp.styleList_ID = ss.ID;");

        List<PersonStyleDTO> q4List = q4.getResultList();
        
        for (PersonStyleDTO p : q4List){
            
        }
    }//main
}//class
