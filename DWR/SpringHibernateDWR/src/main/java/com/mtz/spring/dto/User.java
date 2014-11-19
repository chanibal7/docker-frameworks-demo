/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtz.spring.dto;

import java.io.Serializable;
import javax.persistence.*;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.hibernate.H3BeanConverter;
/**
 *
 * @author salemmo
 */
@DataTransferObject(type="hibernate3", converter=H3BeanConverter.class)
@Entity
@Table(name="user_", catalog="spring")
public class User implements Serializable{
    @RemoteProperty
    private String name;
    @RemoteProperty
    private int age;
    @RemoteProperty
    private int id;

    /**
     * @return the name
     */
    @Column(name="name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    @Column(name="age")
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy=javax.persistence.GenerationType.IDENTITY)
    @Column(name="id")
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
