package models;

import play.db.jpa.JPA;
import play.libs.F.Function;
import play.libs.F.Function0;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import play.data.validation.Constraints;
import play.libs.F.Promise;

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

		Promise<Person> person = Person.findPerson(personId);
		return person.map(new Function<Person,Long>() {

			@Override
			public Long apply(final Person person) throws Throwable {
				
				if (person != null){
					try {
						return JPA.withTransaction(new Function0<Long>() {
						public Long apply() throws Throwable {
						   Person temporalPerson = JPA.em().merge(person);
						   JPA.em().remove(temporalPerson);
						return new Long(0);
						}
					});
					
					
				} catch (Throwable e) {
					e.printStackTrace();
			
				}
					
				};
				return new Long(1);
			}
		});
	}

	
	public static Promise<Long> update(final Person person) {

		Promise<Person> personresult = Person.findPerson(person.getId());
		return personresult.map(new Function<Person,Long>() {

			@Override
			public Long apply(final Person person) throws Throwable {
				
				if (person != null){
					try {
						return JPA.withTransaction(new Function0<Long>() {
						public Long apply() throws Throwable {
						JPA.em().merge(person);
						return new Long(0);
						}
					});
					
					
				} catch (Throwable e) {
					e.printStackTrace();
		
				}
					
				};
				return new Long(1);
			}
		});
	}
	
}
