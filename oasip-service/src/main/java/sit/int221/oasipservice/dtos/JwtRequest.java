package sit.int221.oasipservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JwtRequest {
    private String email;
    private String password;

    //need default constructor for JSON Parsing
    public JwtRequest() {

    }

    public JwtRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
