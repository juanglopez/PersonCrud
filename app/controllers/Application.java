package controllers;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import models.Person;
import play.libs.F;
import play.libs.Json;
import play.libs.F.Promise;
import play.mvc.*;


public class Application extends Controller {

	/**
	 * POST create Person
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static Result create() {

		JsonNode json = request().body().asJson();
		final Person person = Json.fromJson(json, Person.class);
		if (person == null || person.getId() != null
				|| person.getName() == null || person.getSurName() == null) {
			return badRequest("Objeto Persona incompleto.");
		}

		Promise<Long> promiseId = Person.save(person);

		return async(promiseId.map(new F.Function<Long, Result>() {

			@Override
			public Result apply(Long id) throws Throwable {
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("id", id + "");
				return created(Json.toJson(resultMap));
			}
		}));

	}

	/**
	 * PUT update Person
	 * 
	 */
	
	@SuppressWarnings("deprecation")
	public static Result update() {
		
				JsonNode json = request().body().asJson();
				final Person person = Json.fromJson(json, Person.class);

				if (person == null || person.getName() == null
						|| person.getSurName() == null) {
					return badRequest("Objeto Persona incompleto.");
				}

					Promise<Long> promiseStatus = Person.update(person);
					return async(promiseStatus.map(new F.Function<Long, Result>() {

						@Override
						public Result apply(Long id) throws Throwable {
							
							if (id.intValue() == 0) {
								Map<String, String> resultMap = new HashMap<String, String>();
								resultMap.put("id", person.getId().toString());
								return ok(Json.toJson(resultMap));
							}
							return notFound("No existe la persona.");
			            }
					}));
					

	}

	/**
	 * DELETE delete Person
	 * 
	 */

	@SuppressWarnings("deprecation")
	public static Result delete(final Long id) {
		
		Promise<Long> promiseStatus = Person.delete(id);
		return async(promiseStatus.map(new F.Function<Long, Result>() {

			@Override
			public Result apply(Long id) throws Throwable {
				
				if (id.intValue() == 0) {
					return ok();
				}
				return notFound("No existe la persona.");
            }
		}));

	}

	/**
	 * GET find Person
	 * 
	 */

	@SuppressWarnings("deprecation")
	public static Result findPersonById(final Long id) {

		Promise<Person> promisePerson = Person.findPerson(id);
		return async(promisePerson.map(new F.Function<Person, Result>() {

			@Override
			public Result apply(Person promisePerson) throws Throwable {

				if (promisePerson != null) {
					return ok(Json.toJson(promisePerson));
				} else {
					return notFound("No existe la persona.");
				}

			}
		}));

	}

}
