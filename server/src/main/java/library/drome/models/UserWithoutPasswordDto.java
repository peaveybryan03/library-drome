package library.drome.models;

public class UserWithoutPasswordDto {
    private int userId;
    private String email;

    public static UserWithoutPasswordDto fromUser(User user) {
        return new UserWithoutPasswordDto(user.getUserId(), user.getEmail());
    }

    public UserWithoutPasswordDto(int userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
