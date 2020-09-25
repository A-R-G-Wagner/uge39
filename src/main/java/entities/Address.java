package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Alex Wagner
 */
@Entity
public class Address implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String street;
    private String city;
    private int zip;
    
    @OneToMany
    private List<Person> personList;
    
    public Address(String street, String city, int zip) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.personList = new ArrayList<>();
    }
    
    public Address() {
        
    }
    
    public void addPerson(Person person) {
        
        if (person != null) {
            this.personList.add(person);
            person.setAddress(this);
        }
    }//addPerson
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
}//class
