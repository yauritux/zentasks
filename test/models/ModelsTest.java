package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import com.avaje.ebean.Ebean;

import java.util.List;
import models.*;
import org.junit.*;
import play.libs.Yaml;
import play.test.WithApplication;


public class ModelsTest extends WithApplication {

   private User user;

   @Before
   public void setup() {
      start(fakeApplication(inMemoryDatabase(), fakeGlobal()));

      //preparing data
      Ebean.save((List) Yaml.load("test-data.yml"));
   }

   @Test
   public void userDataNotNull() {
      assertNotNull(User.find.all());
   }

   @Test
   public void totalUsersAreThree() {
      assertEquals(3, User.find.findRowCount());
   }

   @Test
   public void totalProjectsAreSeven() {
      assertEquals(7, Project.find.findRowCount());
   }

   @Test
   public void totalTasksAreFive() {
      assertEquals(5, Task.find.findRowCount());
   }

   @Test
   public void userYaurituxExists() {
      assertNotNull(User.authenticate("yauritux@gmail.com", "secret"));
   }

   @Test
   public void userJennyNotExist() {
      assertNull(User.authenticate("jenny@hp.com", "secret"));
   }

   @Test
   public void yaurituxProjectsAreFive() {
      List<Project> projects = Project.findInvolving("yauritux@gmail.com");
      assertEquals(5, projects.size());
   }

   @Test
   public void yaurituxTodoTaskAreFour() {
      List<Task> tasks = Task.findTodoInvolving("yauritux@gmail.com");
      assertEquals(4, tasks.size());
   }
}
