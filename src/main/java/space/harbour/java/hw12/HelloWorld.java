package space.harbour.java.hw12;

public class HelloWorld
{
    public static void main(String[] args)
    {
        get("/hello", (q, a) -> "Hello Wold");
    }
}
