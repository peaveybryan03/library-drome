package library.drome.data;

import library.drome.models.User;

public interface UserRepository {
    User findByEmail(String email);

    User findById(int userId);

    User create(User user);
}
