package org.fizzy.busywithfish.dataserver.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.fizzy.busywithfish.dataserver.dto.User;
import org.fizzy.busywithfish.dataserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create a new user
     */
    @PostMapping
    public boolean createUser(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * Update an existing user
     */
    @PutMapping
    public boolean updateUser(@RequestBody User user) {
        return userService.updateById(user);
    }

    /**
     * Delete a user by ID
     */
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.removeById(id);
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    /**
     * Get all users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.list();
    }

    /**
     * Get users with pagination
     */
    @GetMapping("/page")
    public Page<User> getUsersWithPagination(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        return userService.page(page);
    }
}