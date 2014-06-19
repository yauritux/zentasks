package controllers;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;

import org.junit.*;

import java.util.*;

import play.mvc.*;
import play.libs.*;
import play.test.*;

public class LoginTest extends WithApplication {

   @Before
   public void setup() {
      start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
      Ebean.save((List) Yaml.load("test-data.yml"));
   }

   @Test
   public void authenticateSuccess() {
      Result result = callAction(
        controllers.routes.ref.Application.authenticate(),
           fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
             "email", "yauritux@gmail.com",
             "password", "secret"))
      );
      assertEquals(303, status(result));
      assertEquals("yauritux@gmail.com", session(result).get("email"));
   }

   @Test
   public void authenticateFailure() {
      Result result = callAction(
        controllers.routes.ref.Application.authenticate(),
           fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
             "email", "chacha.annisa@yahoo.co.id",
             "password", "badpassword"
           )
        )
      );
      assertEquals(400, status(result));
      assertNull(session(result).get("email"));
   }

   @Test
   public void authenticated() {
      Result result = callAction(
        controllers.routes.ref.Application.index(),
        fakeRequest().withSession("email", "yauritux@gmail.com")
      );
      assertEquals(200, status(result));
   }

   @Test
   public void notAuthenticated() {
      Result result = callAction(
        controllers.routes.ref.Application.index(),
        fakeRequest()
      );
      assertEquals("/login", header("Location", result));
   }
}

