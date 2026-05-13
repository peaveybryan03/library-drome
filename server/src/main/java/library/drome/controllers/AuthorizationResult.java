package library.drome.controllers;

import library.drome.models.User;
import org.springframework.http.ResponseEntity;

public class AuthorizationResult {
    private User user;
    private ResponseEntity<Object> responseEntity;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResponseEntity<Object> getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity<Object> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public boolean isSuccess() {
        return this.user != null;
    }
}
