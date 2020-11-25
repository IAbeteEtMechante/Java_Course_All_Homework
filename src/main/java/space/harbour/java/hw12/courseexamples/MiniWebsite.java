package space.harbour.java.hw12.courseexamples;


import static spark.Spark.get;
import static spark.Spark.halt;

import com.google.gson.Gson;
import spark.Spark;



public class MiniWebsite {
    public static class Message {
        public String message;

        public Message(String message) {
            this.message = message;
        }
    }

    public static void main(String[] args) {
        get("/hello", (request, response) -> "Hello World");

        //example to show how to use parameters
        // then go to http://127.0.0.1:4567/sum/4/8
        get("/sum/:left/:right", (request, response) -> "Sum is: "
                + String.valueOf(Integer.parseInt(request.params(":left"))
                + Integer.parseInt(request.params(":right"))));

        Gson gson = new Gson();
        Spark.get("/message", (request, response) -> new Message("Hello GSON"), gson::toJson);
        //same thing, old style:
        Spark.get("/json", (request, response) -> new Gson().toJson(new Message("Hello JSON")));

        get("/private", ((request, response) -> {
            response.status(401);
            return "Go Away!";
        }));

        get("/protected", ((request, response) -> {
            halt(403);
            return null;
        }));

        get("/redirect", (request, response) -> {
            response.redirect("/sum/1024/2048");
            return null;
        });







    }

}
