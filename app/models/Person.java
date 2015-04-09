package models;

import play.db.jpa.JPA;
import play.libs.F.Function0;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Akka;
import play.libs.F;
import play.libs.Json;
import play.libs.F.Promise;
import play.mvc.Result;

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

	public static Promise<Long> save(final Person person) {

		return Promise.promise(new Function0<Long>() {
			public Long apply() throws Throwable {

				try {
					return JPA.withTransaction(new Function0<Long>() {
						public Long apply() throws Throwable {
							EntityManager e = JPA.em();
							e.persist(person);
							return person.getId();

						}
					});
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return null;
			}
		});

	}

	public static Promise<Person> findPerson(final Long personId) {

		return Promise.promise(new Function0<Person>() {
			public Person apply() throws Throwable {

				try {
					return JPA.withTransaction(new Function0<Person>() {
						public Person apply() throws Throwable {
							Person personResult = JPA.em().find(Person.class,
									personId);

							return personResult;
						}
					});
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return null;
			}
		});

	}

	public static Promise<Long> delete(final Long personId) {

		return Promise.promise(new Function0<Long>() {

			public Long apply() throws Throwable {

				try {
						return JPA.withTransaction(new Function0<Long>() {
						public Long apply() throws Throwable {
							Person personResult = JPA.em().find(Person.class,personId);
							
							if (personResult != null){
							   JPA.em().remove(personResult);
							   return new Long(0);
							
							}
							 return new Long(1); //error Message
						}
					});
					
					
				} catch (Throwable e) {
					e.printStackTrace();
					return new Long(1); //error Message
				}

			}
		});

	}

	
	public static Promise<Long> update(final Person person) {

		return Promise.promise(new Function0<Long>() {

			public Long apply() throws Throwable {

				try {
						return JPA.withTransaction(new Function0<Long>() {
								public Long apply() throws Throwable {
									JPA.em().merge(person);
									return new Long(0);
								}


					});

				} catch (Throwable e) {
					e.printStackTrace();
					return new Long(1); //error Message

				}
				
			}
		});

	}

	
}
