package space.harbour.java.hw12.courseexamples;

import static spark.Spark.get;


/**
 * A simple Hello World implementation.
 * go to http://127.0.0.1:4567/hello
 */
public class HelloWorld {
    public static void main(String[] args) {

        //any request that will come to this url:
        //127.0.0.1:4567/hello
        //and do a GET request
        //will get the answer: "Hello World"
        get("/hello", (request, response) -> "Hello World");


    }
}
