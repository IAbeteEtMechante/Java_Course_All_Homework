package space.harbour.java.hw7;

import javax.json.JsonObject;

public interface Jsonable {

    public String toJsonString();
    
    public JsonObject toJsonObject();

    //public fromJsonString();
    //public fromJsonObject();
}
