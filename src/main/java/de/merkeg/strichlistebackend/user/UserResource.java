package de.merkeg.strichlistebackend.user;


import de.merkeg.strichlistebackend.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserResource {


    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{id}")
    public User getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return user;
    }

    @PostMapping
    public User createNewUser(@RequestBody User.NewUserRequestBody user){
        return userService.createUser(user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }
}
