package controllers;

import java.util.Map;

import models.Person;

import org.junit.*;

import com.fasterxml.jackson.databind.JsonNode;

import play.db.jpa.JPA;
import play.libs.Json;
import play.libs.WS;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class ApplicationTest {

	@Test
	public void whenPersonIsCreatedWithOutErrorsThenReturnStatus201() {
		running(testServer(3333, fakeApplication()), new Runnable() {

			public void run() {
				Person person = new Person();
				person.setName("nameTest");
				person.setSurName("surName");
				JsonNode personAsJson = Json.toJson(person);
				WS.Response response = WS.url("http://localhost:3333/person")
						.post(personAsJson).get();

				String id = (String) Json
						.fromJson(response.asJson(), Map.class).get("id");
				final Long idPerson = new Long(id);

				assertThat(response.getStatus()).isEqualTo(CREATED);

				JPA.withTransaction(new play.libs.F.Callback0() {
					@Override
					public void invoke() {
						Person personCreated = JPA.em().find(Person.class,
								idPerson);

						assertThat(personCreated.name).isEqualTo("nameTest");

					}
				});

			}

		});
	}

	@Test
	public void whenPersonIsUpdatedWithOutErrorsThenReturnStatus200() {
		running(testServer(3333, fakeApplication()), new Runnable() {

			public void run() {
				Person person = new Person();
				person.setName("nameTesti");
				person.setSurName("surName");
				JsonNode personAsJson = Json.toJson(person);
				WS.Response response = WS.url("http://localhost:3333/person")
						.post(personAsJson).get();

				String id = (String) Json
						.fromJson(response.asJson(), Map.class).get("id");

				final Long idPerson = new Long(id);

				JPA.withTransaction(new play.libs.F.Callback0() {
					@Override
					public void invoke() {
						Person personCreated = JPA.em().find(Person.class,
								idPerson);
						personCreated.setName("updatedName");

						JsonNode personAsJson = Json.toJson(personCreated);
						WS.Response response = WS
								.url("http://localhost:3333/person")
								.put(personAsJson).get();
						assertThat(response.getStatus()).isEqualTo(OK);

						String idPersonUpdated = (String) Json.fromJson(
								response.asJson(), Map.class).get("id");
						Long idPersonUp = new Long(idPersonUpdated);

						Person personUpdated = JPA.em().find(Person.class,
								idPersonUp);
						assertThat(personUpdated.name).isEqualTo("updatedName");

					}

				});

			}

		});
	}

	@Test
	public void whenPersonIsDeletedWithOutErrorsThenReturnStatus200() {
		running(testServer(3333, fakeApplication()), new Runnable() {

			public void run() {
				Person person = new Person();
				person.setName("nameTesti");
				person.setSurName("surName");
				JsonNode personAsJson = Json.toJson(person);
				WS.Response response = WS.url("http://localhost:3333/person")
						.post(personAsJson).get();

				final Long id = Json.parse(response.getBody()).get("id").asLong();

			    JPA.withTransaction(new play.libs.F.Callback0() {
					@Override
					public void invoke() {
						
						WS.Response response = WS
								.url("http://localhost:3333/person/"+id)
								.delete().get();
						assertThat(response.getStatus()).isEqualTo(OK);

						Person personUpdated = JPA.em().find(Person.class,
								id);
						assertThat(personUpdated).isNull();

					}

				});

			}

		});
	}
	
	@Test
	public void whenFindaPersonByIdThenReturnAPersonJsonObject() {
		running(testServer(3333, fakeApplication()), new Runnable() {

			public void run() {
				Person person = new Person();
				person.setName("nameTesti");
				person.setSurName("surName");
				JsonNode personAsJson = Json.toJson(person);
				WS.Response response = WS.url("http://localhost:3333/person")
						.post(personAsJson).get();

				final Long id = Json.parse(response.getBody()).get("id").asLong();

			    JPA.withTransaction(new play.libs.F.Callback0() {
					@Override
					public void invoke() {
						
						WS.Response response = WS
								.url("http://localhost:3333/person/"+id)
								.get().get();
						
						
						assertThat(response.getStatus()).isEqualTo(OK);
						
						Person person = (Person) Json
								.fromJson(response.asJson(), Person.class);

						Person personFound = JPA.em().find(Person.class,
								id);
						
						assertThat(person.name).isEqualTo(personFound.name);
					}

				});

			}

		});
	}

	@Test
	public void whenFindaPersonByIdThatNotExisThenReturnABadCodeReason404() {
		running(testServer(3333, fakeApplication()), new Runnable() {

			public void run() {

			    JPA.withTransaction(new play.libs.F.Callback0() {
					@Override
					public void invoke() {
						
						Long id = new Long(1234);
						
						WS.Response response = WS
								.url("http://localhost:3333/person/"+id)
								.get().get();
						
						assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
						assertThat(response.getBody()).isEqualTo("No existe la persona.");

					}

				});

			}

		});
	}

	@Test
	public void whenPersonIsCreatedWithNameOrSurnameNullThenReturnStatus400() {
		running(testServer(3333, fakeApplication()), new Runnable() {

			public void run() {
				Person person = new Person();
				person.setSurName("surName");
				JsonNode personAsJson = Json.toJson(person);
				WS.Response responseNullname = WS.url("http://localhost:3333/person")
						.post(personAsJson).get();

				assertThat(responseNullname.getStatus()).isEqualTo(BAD_REQUEST);
				assertThat(responseNullname.getBody()).isEqualTo("Objeto Persona incompleto.");
				
				person.setName("name");
				person.setSurName(null);
				personAsJson = Json.toJson(person);
				
				WS.Response responseNullSurname = WS.url("http://localhost:3333/person")
						.post(personAsJson).get();

				assertThat(responseNullSurname.getStatus()).isEqualTo(BAD_REQUEST);
				assertThat(responseNullSurname.getBody()).isEqualTo("Objeto Persona incompleto.");
	
					}
				});

			}
	
	@Test
	public void whenPersonIsUpdatedWithNameOrSurnameNullThenReturnStatus400() {
		running(testServer(3333, fakeApplication()), new Runnable() {

			public void run() {
				Person person = new Person();
				person.setSurName("surName");
				JsonNode personAsJson = Json.toJson(person);
				WS.Response responseNullname = WS.url("http://localhost:3333/person")
						.put(personAsJson).get();

				assertThat(responseNullname.getStatus()).isEqualTo(BAD_REQUEST);
				assertThat(responseNullname.getBody()).isEqualTo("Objeto Persona incompleto.");
				
				person.setName("name");
				person.setSurName(null);
				personAsJson = Json.toJson(person);
				
				WS.Response responseNullSurname = WS.url("http://localhost:3333/person")
						.put(personAsJson).get();

				assertThat(responseNullSurname.getStatus()).isEqualTo(BAD_REQUEST);
				assertThat(responseNullSurname.getBody()).isEqualTo("Objeto Persona incompleto.");
	
					}
				});

			}
	
	@Test
	public void whenDeletePersonByIdThatNotExisThenReturnABadCodeReason404() {
		running(testServer(3333, fakeApplication()), new Runnable() {

			public void run() {

			    JPA.withTransaction(new play.libs.F.Callback0() {
					@Override
					public void invoke() {
						
						Long id = new Long(1234);
						
						WS.Response response = WS
								.url("http://localhost:3333/person/"+id)
								.delete().get();
						
						assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
						assertThat(response.getBody()).isEqualTo("No existe la persona.");

					}

				});

			}

		});
	}


	
}
