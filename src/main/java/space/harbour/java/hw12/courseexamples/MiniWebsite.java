package space.harbour.java.hw12.courseexamples;

import com.google.gson.Gson;
import spark.Spark;

import static spark.Spark.get;

public class MiniWebsite {
    public static void main(String[] args) {
        get("/hello", (request, response) -> "Hello World");

        //example to show how to use parameters
        // then go to http://127.0.0.1:4567/sum/4/8
        get("/sum/:left/:right", (request, response) -> "Sum is: "
                + String.valueOf(Integer.parseInt(request.params(":left"))
                + Integer.parseInt(request.params(":right"))));

        Gson gson = new Gson();
        Spark.get("/message", (request, response) -> new Message("Hello GSON"), gson::toJson);
        Spark.get("/json", (request, response) -> new Gson().toJson(new Message("Hello JSON")));

        



    }

}
