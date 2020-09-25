/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import static com.mysql.cj.MysqlType.NULL;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Alex Wagner
 */
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long p_id;
    private String name;
    private int year;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    List<Fee> feeList;
    
    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    List<SwimStyle> styleList;

    public Person(String name, int year) {
        this.name = name;
        this.year = year;
        this.feeList = new ArrayList<>();
        this.styleList = new ArrayList<>();
    }

    public Person() {

    }
    
    public void AddFee(Fee fee){
     this.feeList.add(fee);
     if(fee != null){
         fee.setPerson(this);
     }
    }//AddFee
    
    public void AddSwimStyle(SwimStyle style){
     
     if(style != null){
         this.styleList.add(style);
         style.getPersons().add(this);
     }
    }//AddSwimStyle
    
    public void RemoveSwimStyle(SwimStyle style){
        if(style != null){
            styleList.remove(style);
            style.getPersons().remove(this);
        }
        
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        if(address != null){
           address.setPerson(this);
        }
    }
    
    

}
