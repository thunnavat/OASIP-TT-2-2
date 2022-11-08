package sit.int221.oasipservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JwtResponse {
    private String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public JwtResponse() {
    }
}
