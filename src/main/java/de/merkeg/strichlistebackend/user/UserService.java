package de.merkeg.strichlistebackend.user;

import de.merkeg.strichlistebackend.exception.UserAlreadyExistsException;
import de.merkeg.strichlistebackend.exception.UserDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User createUser(User.NewUserRequestBody userRequest) {

        Optional<User> userByName = userRepository.findUserByName(userRequest.getName());

        if(userByName.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User user = new User(userRequest.getName(), userRequest.getStartingBalance());
        userRepository.save(user);
        log.info("New User created: {}", user.getName());
        return user;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        userRepository.delete(user.get());
        log.info("User deleted: {}", user.get().getName());
    }

    public User getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        return user.get();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
