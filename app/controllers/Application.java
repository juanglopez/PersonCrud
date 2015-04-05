package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Person;
import play.*;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	 /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Application.index()
    );
    

	@Transactional
    public static Result index() {
    	List<Person> personList = Person.getPersonList();
    	Form<Person> personForm = Form.form(Person.class);
        return ok(index.render(personForm,personList));
    }
    
    /**
     * Handle the 'new person form' submission 
     */
    @Transactional
    public static Result save() {
        Form<Person> personForm = form(Person.class).bindFromRequest();
        List<Person> personList = Person.getPersonList();
        if(personForm.hasErrors()) {
        	return badRequest(index.render(personForm,personList));
          
        }
        personForm.get().save();
        flash("success", "Computer " + personForm.get().name + " has been created");
        Form<Person> personNewForm = Form.form(Person.class);
        
        return GO_HOME;
    }
    
    @Transactional(readOnly=true)
    public static Result edit(Long id) {
        Form<Person> computerForm = form(Person.class).fill(
        		Person.findById(id)
        );
        return ok(
            editForm.render(id, computerForm)
        );
    }
    
    @Transactional
    public static Result delete(Long id) {
        Person.findById(id).delete();
        flash("success", "Computer has been deleted");
        return GO_HOME;
    }
    
    @Transactional
    public static Result update(Long id) {
        Form<Person> personForm = form(Person.class).bindFromRequest();
        if(personForm.hasErrors()) {
            return badRequest(editForm.render(id, personForm));
        }
        personForm.get().update(id);
        flash("success", "Computer " + personForm.get().name + " has been updated");
        return GO_HOME;
    }

}
