import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

  get("/", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/phone", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("phone", Phone.all());
    model.put("template", "templates/phone.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("phone/new", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("template", "templates/task-form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/phone/:id", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Phone task = Phone.find(Integer.parseInt(request.params(":id")));
    model.put("task", task);
    model.put("template", "templates/task.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/contactList", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("contactList", Contact.all());
    model.put("template", "templates/contactList.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("contactList/new", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("template", "templates/contact-form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/contactList", (request, response) ->{
    HashMap<String, Object> model = new HashMap<String, Object>();
    String firstName = request.queryParams("firstName");
    String lastName = request.queryParams("lastName");
    Contact newContact = new Contact(firstName,lastName);
    model.put("contact", newContact);
    model.put("template", "templates/success.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/contactList/:id", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Contact contact = Contact.find(Integer.parseInt(request.params(":id")));
    model.put("contact", contact);

    model.put("template", "templates/contact.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("contactList/:id/phone/new", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    Contact contact = Contact.find(Integer.parseInt(request.params(":id")));
    ArrayList<Phone> phone = contact.getPhone();
    model.put("contact", contact);
    model.put("phone", phone);
    model.put("template", "templates/contact-phone-form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/phone", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Contact contact = Contact.find(Integer.parseInt(request.queryParams("contactId")));
    //System.out.println("x");
    ArrayList<Phone> phone = contact.getPhone();

    if (phone == null) {
      phone = new ArrayList<Phone>();
      request.session().attribute("phone", phone);
    }

    String phoneType = request.queryParams("phoneType");
    String areaCode = request.queryParams("areaCode");
    String phoneNum = request.queryParams("phoneNum");
    Phone newPhone = new Phone(phoneType, areaCode, phoneNum);

    phone.add(newPhone);

    model.put("phone", phone);
    model.put("contact", contact);
    model.put("template", "templates/contact.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("contactList/:id/email/new", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    Contact contact = Contact.find(Integer.parseInt(request.params(":id")));
    ArrayList<Email> email = contact.getEmail();
    model.put("contact", contact);
    model.put("email", email);
    model.put("template", "templates/contact-email-form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/email", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Contact contact = Contact.find(Integer.parseInt(request.queryParams("contactId")));
    //System.out.println("x");
    ArrayList<Email> email = contact.getEmail();

    if (email == null) {
      email = new ArrayList<Email>();
      request.session().attribute("email", email);
    }

    String emailType = request.queryParams("emailType");
    String emailAddress = request.queryParams("emailAddress");

    Email newEmail = new Email(emailType, emailAddress);

    email.add(newEmail);

    model.put("email", email);
    model.put("contact", contact);
    model.put("template", "templates/contact.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("contactList/:id/address/new", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    Contact contact = Contact.find(Integer.parseInt(request.params(":id")));
    ArrayList<Address> address = contact.getAddress();
    model.put("contact", contact);
    model.put("address", address);
    model.put("template", "templates/contact-address-form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/address", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Contact contact = Contact.find(Integer.parseInt(request.queryParams("contactId")));
    ArrayList<Address> address = contact.getAddress();

    if (address == null) {
      address = new ArrayList<Address>();
      request.session().attribute("address", address);
    }

    String addressType = request.queryParams("addressType");
    String street = request.queryParams("street");
    String city = request.queryParams("city");
    String state = request.queryParams("state");
    String zip = request.queryParams("zip");

    Address newAddress = new Address(addressType, street, city, state, zip);

    address.add(newAddress);

    model.put("address", address);
    model.put("contact", contact);
    model.put("template", "templates/contact.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

 }
}
