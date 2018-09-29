package space.eignatik.testSpark;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    private static List<User> users = new ArrayList<>(List.of(
            new User()
                    .setId(1)
                    .setFirstName("John")
                    .setLastName("Dou")
                    .setGender("Male")
                    .setBirthdate("12-01-1993")
                    .setWorkTitle("Senior UI/UX Engineer")
                    .setSsn("67489349340"),
            new User()
                    .setId(2)
                    .setFirstName("Jane")
                    .setLastName("Dou")
                    .setGender("Female")
                    .setBirthdate("26-02-1991")
                    .setWorkTitle("")
                    .setSsn("87348534857"),
            new User()
                    .setId(3)
                    .setFirstName("Kate")
                    .setLastName("Smith")
                    .setGender("Female")
                    .setBirthdate("05-01-1989")
                    .setWorkTitle("CTO")
                    .setSsn("9387464578")
    ));

    private static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
        get("/getTestUser", (req, res) -> getUser());
        get("/getUser/:id", (req, res) -> {
            String param = req.params(":id");
            if (!param.matches("\\d+")) {
                res.redirect("/forbidden");
            }
            return getUserById(req.params(":id"));
        });
        get("/forbidden", (req, res) -> "You have been redirected because you tried to access users ids incorrectly");

        post("/signUp", (req, res) -> {
            return "User with login " + req.params("login") + " has been signed in";
        });

        post("/addUser", (req, res) -> {
            res.type("application/json");
            User user = new Gson().fromJson(req.body(), User.class);
            users.add(user);
            return new Gson().toJson("SUCCESS");
        });

        post("pizza/order", (req, res) -> {
            res.type("application/json");
            Order order = new Gson().fromJson(req.body(), Order.class);
            orders.add(order);
            return new Gson().toJson("SUCCESS");
        });

        get("/orders/:userId", (req, res) ->
                new Gson().toJson(
                        getList(Integer.parseInt(req.params(":userId"))))
                );
    }

    private static List<Order> getList(long id) {
        List<Order> wtf = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == id) {
                wtf.add(order);
            }
        }
        return wtf;
    }

    private static User getUserById(String inputId) {
        long id = Long.parseLong(inputId);
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return new User();
    }

    private static String getUser() {
        return new User()
                .setId(1)
                .setFirstName("John")
                .setLastName("Dou")
                .setGender("Male")
                .setBirthdate("12-01-1993")
                .setWorkTitle("Senior UI/UX Engineer")
                .setSsn("67489349340")
                .toString();
    }
}

class User {
    private String firstName;
    private long id;
    private String lastName;
    private String birthdate;
    private String gender;
    private String workTitle;
    private String ssn;

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public User setBirthdate(String birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public User setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
        return this;
    }

    public String getSsn() {
        return ssn;
    }

    public User setSsn(String ssn) {
        this.ssn = ssn;
        return this;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"firstName\":\"" + firstName + "\",\n" +
                "\"id\":\"" + id + "\",\n" +
                "\"lastName\":\"" + lastName + "\",\n" +
                "\"birthdate\":\"" + birthdate + "\",\n" +
                "\"gender\":\"" + gender + "\",\n" +
                "\"workTitle\":\"" + workTitle + "\",\n" +
                "\"ssn\":\"" + ssn + "\"\n" +
                '}';
    }
}

class Order {
    private String pizzaName;
    private int quantity;
    private long userId;

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
