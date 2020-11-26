package space.harbour.java;

import org.json.JSONException;
import org.json.JSONObject;

public class Student {
    String name;
    int grade;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("grade", grade);
        return null;
    }

    public static Student fromJsonObject(JSONObject object) throws JSONException {
        String name = object.getString("name");
        int grade = object.getInt("grade");
        return new Student(name, grade);
    }
}
