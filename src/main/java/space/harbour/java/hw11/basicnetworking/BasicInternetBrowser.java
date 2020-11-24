package space.harbour.java.hw11.basicnetworking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
This class is making a raw HTTP request for a file
you could change the file name to "index.html" for simplicity
example usage:
$ java space.harbour.java.hw11.utils.BasicInternetBrowser
HTTP/1.1 200 OK                                                 //200 means OK
Date: Tue, 24 Nov 2020 01:23:40 GMT
Server: Apache
Last-Modified: Sun, 13 Sep 2020 11:32:26 GMT                     //last modified date
Accept-Ranges: bytes
Content-Length: 29419                                           //size about 29kB
Vary: Accept-Encoding,User-Agent
Keep-Alive: timeout=15, max=82
Connection: Keep-Alive
Content-Type: text/html
Set-Cookie: BIGipServer~CUIT~www.columbia.edu-80-pool=1781021568.20480.0000;
expires=Tue, 24-Nov-2020 07:23:40 GMT; path=/; Httponly

<!DOCTYPE HTML>                           //here you get the file you asked for (sample.html)
<html lang="en">
<head>
<!-- THIS IS A COMMENT -->
<title>Sample Web Page</title>
........

//btw, can get the same result from command line with telnet:
$ telnet 128.59.105.24 80
Trying 128.59.105.24...
Connected to 128.59.105.24.
Escape character is '^]'.
GET /~fdc/sample.html HTTP/1.0   //need to type this then ENTER
Accept: text/html, text/plain, text/* //and type that then type ENTER twice


 */

public class BasicInternetBrowser {
    public static void main(String[] args) {
        //in the browser it will look like: http://www.columbia.edu/~fdc/sample.edu
        String host = "www.columbia.edu";
        String file = "/~fdc/sample.html";
        int port = 80;

        try (Socket clientSocket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(clientSocket
                    .getOutputStream(), false);
            out.print("GET " + file + " HTTP/1.0\r\n");
            //accept any kind of text as an answer:
            out.print("Accept: text/plain, text/html, text/*\r\n");
            out.print("\r\n");
            out.flush();

            InputStreamReader inr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader br = new BufferedReader(inr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (UnknownHostException e) {
            System.out.println("Could not find host: " + host);
        } catch (IOException e) {
            System.out.println("No server on port" + port + ".");
        }
    }
}
