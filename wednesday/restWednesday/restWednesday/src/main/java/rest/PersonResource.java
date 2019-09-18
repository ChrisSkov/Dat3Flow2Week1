package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author stoff
 */
@Path("person")
public class PersonResource {

    private static Map<Integer, Person> persons = new HashMap();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static int nextId = 3;

    public PersonResource()
    {
        if (persons.isEmpty())
        {
            persons.put(1, new Person("Kurt Wonnegut", "a@b.dk"));
            persons.put(2, new Person("Hanne Wonnegut", "h@b.dk"));
        }
    }

    class Person {

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        private String name;
        private String email;

        public Person(String name, String email)
        {
            this.name = name;
            this.email = email;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson()
    {
        return "{}";
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll()
    {
        return gson.toJson(persons.values());
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id)
    {
        persons.remove(id);
        return "{\"status\"}";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String editPerson(String personAsJson, @PathParam("id") int id)
    {
        Person pOriginal = persons.get(id);
        Person personNew = gson.fromJson(personAsJson, Person.class);
        pOriginal.setName(personNew.getName());
        pOriginal.setEmail(personNew.getEmail());
        return gson.toJson(pOriginal);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPerson(String personAsJson)
    {
        Person personNew = gson.fromJson(personAsJson, Person.class);
        persons.put(nextId, personNew);
        nextId++;
        return gson.toJson(personNew);
    }
}
