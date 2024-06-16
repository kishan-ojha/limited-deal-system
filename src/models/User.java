package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class User {
    private int id;
    private String email;
    private Map<String, String> metaData;
}
