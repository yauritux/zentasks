package controllers;

import static play.data.Form.*;

import models.*;

import play.*;
import play.data.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

   public static class Login {

      public String email;
      public String password;

      public String validate() {
         if (User.authenticate(email, password) == null) {
            return "Invalid user or password";
         }
         return null;
      }
   }

   @Security.Authenticated(Secured.class)
    public static Result index() {
       return ok(index.render(
          //Project.find.all(), 
          Project.findInvolving(request().username()),
          //Task.find.all()
          Task.findTodoInvolving(request().username()),
          User.find.byId(request().username())
       ));
    }

    public static Result login() {
       return ok(login.render(Form.form(Login.class)));
    }

    public static Result logout() {
       session().clear();
       flash("success", "You've been logout");
       return redirect(routes.Application.login());
    }

    public static Result authenticate() {
       Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
       if (loginForm.hasErrors()) {
          return badRequest(login.render(loginForm));
       }

       session().clear();
       session("email", loginForm.get().email);
       return redirect(routes.Application.index());
    }
}
