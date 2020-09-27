/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Address;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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
    public PersonDTO addPerson(String fName, String lName, String phone, String street, String city, int zip) throws MissingInputException {
        if (fName.length() == 0) {
            throw new MissingInputException("First name is missing");
        }
        if (lName.length() == 0) {
            throw new MissingInputException("Last name is missing");
        }

        EntityManager em = emf.createEntityManager();
        Person p = new Person(fName, lName, phone);

        try {
            em.getTransaction().begin();
            Query q = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.city = :city AND a.zip = :zip");
            q.setParameter("street", street);
            q.setParameter("city", city);
            q.setParameter("zip", zip);
            List<Address> addressList = q.getResultList();
            if (addressList.size() > 0) {
                p.setAddress(addressList.get(0));
            } else {
                System.out.println("-------------make new address!------------");
                p.setAddress(new Address(street, city, zip));
            }
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {

        EntityManager em = emf.createEntityManager();

        Person deletedPerson = em.find(Person.class, id);
        if (deletedPerson == null) {
            throw new PersonNotFoundException("No person with provided id found");
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
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();

        Person foundPerson = em.find(Person.class, id);
        if (foundPerson == null) {
            throw new PersonNotFoundException("No person with provided id found");

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
//            List<Person> personList = tq1.getResultList();
//
//            return new PersonsDTO(personList);
            return new PersonsDTO(tq1.getResultList());
        } finally {
            em.close();
        }
    }//getAllPersons

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();

        Person nyPersonData = em.find(Person.class, p.getId());
        if (nyPersonData == null) {
            throw new PersonNotFoundException("No person with provided id found");
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
