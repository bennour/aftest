package fr.salah.aftest.controller;

import fr.salah.aftest.exception.ResourceNotFoundException;
import fr.salah.aftest.mapper.UserMapper;
import fr.salah.aftest.model.User;
import fr.salah.aftest.model.UserForm;
import fr.salah.aftest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(description="User resource API")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    /**
     * Create user user.
     *
     * @param form the form
     * @return the user
     */
    @PostMapping
    @ApiOperation(value = "Create user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid UserForm form) {
        User user = userMapper.asUser(form);

        return userService.create(user);
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by id")
    public User getUser(@PathVariable String id) {
        return userService.getById(id).orElseThrow(() -> new ResourceNotFoundException("User id ["+id+"] not found"));
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    @GetMapping
    @ApiOperation(value = "Get all users")
    public List<User> getUsers() {
        return userService.getAll();
    }

    /**
     * Delete user.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user by id")
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}
