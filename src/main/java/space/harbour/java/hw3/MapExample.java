package space.harbour.java.hw3;

import java.util.HashMap;
import java.util.Map;
import space.harbour.java.hw3.MyHashMap;

public class MapExample {
    public static void main(String[] args) {
        MyHashMap<String, String> contacts = new MyHashMap<>();
        System.out.println(contacts.size());
        System.out.println(contacts.isEmpty());
        contacts.put("Ahmed", "123-456-789");
        contacts.put("Vasilii", "987-654-321");
        contacts.put("Pierre", "567-890-342");

        System.out.println(contacts.get("Ahmed"));
        System.out.println(contacts.remove("Pierre"));
        //for (Map.Entry<String, String> contact : contacts.entrySet()) {
        //System.out.println(contact.getKey() + ": " + contact.getValue());
        //}
        System.out.println(contacts.size());
        System.out.println(contacts.isEmpty());
        System.out.println(contacts.containsKey("Vasilii"));
        System.out.println("Contains Pierre?: " + contacts.containsKey("Pierre"));
        System.out.println(contacts.containsValue("987-654-321"));

        HashMap<String, String> newContacts = new HashMap<>();
        newContacts.put("Duc", "888-888-888");
        contacts.putAll(newContacts);
        System.out.println(contacts.containsValue("888-888-888"));
        contacts.put("Ahmed", "333-333-333");
        System.out.println(contacts.keySet());
        System.out.println(contacts.values());
        

    }
}
