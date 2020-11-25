package space.harbour.java.hw12.courseexamples;

import static spark.Spark.get;

public class MiniWebsite {
    public static void main(String[] args) {
        get("/hello", (request, response) -> "Hello World");
    }

}
