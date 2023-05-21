package de.merkeg.strichlistebackend.user;


import de.merkeg.strichlistebackend.exception.UserDoesNotExistException;
import de.merkeg.strichlistebackend.transaction.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/user")
@Tag(name = "User Management")
public class UserResource {


    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{id}")
    @Operation(summary = "Get user information - Does include transaction history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public User getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return user;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "User with name does already exist", content = @Content)
    })
    public ResponseEntity<User> createNewUser(@RequestBody User.NewUserRequestBody user){
        return ResponseEntity.created(null).body(userService.createUser(user));
    }

    @DeleteMapping(path = "{id}")
    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }
}
