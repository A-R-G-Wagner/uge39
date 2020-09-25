/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Alex Wagner
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {

        EntityManager em = emf.createEntityManager();
        Person p1 = new Person(fName, lName, phone);

        em.getTransaction().begin();
        em.persist(p1);
        em.getTransaction().commit();

        return new PersonDTO(p1);
    }//addPerson

    @Override
    public PersonDTO deletePerson(int id) {

        EntityManager em = emf.createEntityManager();

        Person deletedPerson = em.find(Person.class, id);
        if (deletedPerson == null) {

        }
        try {
            em.getTransaction().begin();
            em.remove(deletedPerson);
            em.getTransaction();
        } finally {
            em.close();
        }
        return new PersonDTO(deletedPerson);

    }//deletePerson

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = emf.createEntityManager();

        Person foundPerson = em.find(Person.class, id);
        if (foundPerson != null) {

        }
        try {

            return new PersonDTO(foundPerson);
        } finally {
            em.close();
        }
    }//getPerson

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {

            TypedQuery<Person> tq1 = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> personList = tq1.getResultList();

            return new PersonsDTO(personList);
        } finally {
            em.close();
        }
    }//getAllPersons

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        EntityManager em = emf.createEntityManager();

        Person nyPersonData = em.find(Person.class, p.getId());
        if (nyPersonData != null){
            
        }
        try {
            em.getTransaction().begin();
            nyPersonData.setFirstName(p.getFirstName());
            nyPersonData.setLastName(p.getLastName());
            nyPersonData.setPhone(p.getPhone());
            nyPersonData.setLastEdited();
            em.getTransaction().commit();

            return new PersonDTO(nyPersonData);
        } finally {
            em.close();
        }
    }//editPerson

}//class
