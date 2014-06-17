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
}

