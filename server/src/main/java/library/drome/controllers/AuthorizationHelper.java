package library.drome.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import library.drome.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AuthorizationHelper {

    public static AuthorizationResult getUserFromHeaders(Map<String, String> headers) {
        AuthorizationResult result = new AuthorizationResult();

        String diyJwt = headers.get("authorization");

        if (diyJwt == null) {
            result.setResponseEntity(new ResponseEntity<>(List.of("You must be authorized to do that."), HttpStatus.UNAUTHORIZED));
            return result;
        }

        String[] halvesOfDiyJwt = diyJwt.split("\\|");

        String userJsonPlusSecret = halvesOfDiyJwt[0] + "backend-secret-string";
        int hashResult = Objects.hash(userJsonPlusSecret);
        int hashFromDiyJwt = Integer.parseInt(halvesOfDiyJwt[1]);
        if (hashResult != hashFromDiyJwt) {
            result.setResponseEntity(new ResponseEntity<>(List.of("Detected tampering with authorization header"), HttpStatus.UNAUTHORIZED));
            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        try {
            user = mapper.readValue(halvesOfDiyJwt[0], User.class);
        } catch (JsonProcessingException ex) {
            result.setResponseEntity(new ResponseEntity<>(List.of("Invalid user"), HttpStatus.UNAUTHORIZED));
        }

        result.setUser(user);
        return result;
    }
}
