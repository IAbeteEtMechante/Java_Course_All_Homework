package space.harbour.java.hw11.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*
This class gives the IP address of a domain name
example use:
$ java space.harbour.java.hw11.utils.DnsLookup harbour.space
IP address: 104.28.29.226

on the command line we could also do:
$ nslookup harbour.space
or
$ nslookup harbour.space 1.1.1.1
This last one is using cloudera to lookup the DNS

 */

public class DnsLookup {
    public static void main(String[] args) throws IOException {
        String hostname = args[0];

        try {
            InetAddress ipAddress = InetAddress.getByName(hostname);
            System.out.println("IP address: " + ipAddress.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Could not find IP address for: "
                    + hostname);
        }
    }
}
