package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * Person entity managed by JPA
 */
@Entity
@SequenceGenerator(name = "person_seq", sequenceName = "person_seq")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
	public Long id;
	
	@Constraints.Required
	public String surName;

	@Constraints.Required
	public String name;

	@Constraints.Email
	public String email;

	
	public String birthday;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * Insert this new computer.
	 */
	public void save() {
		JPA.em().persist(this);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Person> getPersonList() {
		
		return (List<Person>) JPA.em()
							 .createQuery("from Person")
							 .getResultList();
	}
	
	 public static Person findById(Long id) {
	        return JPA.em().find(Person.class, id);
	    }
	 
	 /**
	     * Delete this computer.
	     */
	    public void delete() {
	        JPA.em().remove(this);
	    }
	    
	    public void update(Long id) {
	        this.id = id;
	        JPA.em().merge(this);
	    }
	
	}


