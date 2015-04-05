package controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.*;

import play.mvc.*;
import play.mvc.Http.HeaderNames;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.Json;
import play.libs.WS;
import play.libs.F.*;
import models.Person;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


public class ApplicationTest {

	@Test
	public void whenCreateaPersonWithouterrorThenResponseCodeisOK() {
		
		running(testServer(3333, fakeApplication(inMemoryDatabase("test"))), new Runnable() {
			public void run() {
				
				Person person = new Person();
				person.setId(new Long (1));
				person.setName("name");
				person.setSurName("surName");
				
				WS.Response response = WS.url("http://localhost:3333/save")
						.setHeader(HeaderNames.CONTENT_TYPE, "text/html")
						.post(Json.stringify(Json.toJson(person)))
						.get();

				//Asserts
				assertThat(response.getStatus()).isEqualTo(play.mvc.Http.Status.OK);

			}
	});
		

	}
}



