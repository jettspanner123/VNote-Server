package com.vnote.VNote.controllers;

import com.vnote.VNote.exceptions.UserAlreadyExists;
import com.vnote.VNote.exceptions.UserNotFound;
import com.vnote.VNote.modules.BaseResponse;
import com.vnote.VNote.modules.base.TwinTokens;
import com.vnote.VNote.modules.user.CreateUserResponse;
import com.vnote.VNote.modules.user.CreateUserServiceResponse;
import com.vnote.VNote.modules.user.UserResponse;
import com.vnote.VNote.modules.user.UsersResponse;
import com.vnote.VNote.modules.validators.UserInputDTO;
import com.vnote.VNote.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // MARK: Get Mappings
    @GetMapping("/health")
    public ResponseEntity<BaseResponse> hello() {
        return ResponseEntity.ok(this.userService.healthCheck());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        try {
            final var data = this.userService.getUserById(userId);
            return ResponseEntity.ok(
                    UserResponse.builder()
                            .success(true)
                            .message("Here is the user!")
                            .user(Optional.ofNullable(data))
                            .build()
            );
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UserResponse.builder().success(false).message(e.getMessage()).build());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<UsersResponse> getAllUsers() {
        try {
            final var data = this.userService.getAllUsers();
            return ResponseEntity.ok(
                    UsersResponse.builder()
                            .users(Optional.ofNullable(data))
                            .success(true)
                            .message("List Of All Users!").build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UsersResponse.builder().success(false).users(Optional.empty()).message(e.getMessage()).build());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody UserInputDTO userData) {
        try {
            final var data = this.userService.createUser(userData);
            return ResponseEntity.ok(CreateUserResponse.builder()
                    .success(true)
                    .message("User Created Successfully!")
                    .user(data.getSafeUser())
                    .tokens(data.getTokens())
                    .build()
            );
        } catch (UserAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(CreateUserResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .user(null)
                    .tokens(null)
                    .build());
        }

    }

    @DeleteMapping("/user/soft-delete/{userId}")
    public ResponseEntity<UserResponse> softDeleteUser(@PathVariable UUID userId) {
        try {
            final var data = this.userService.softDeleteUser(userId);
            return ResponseEntity.ok(UserResponse.builder()
                    .success(true)
                    .message("User Deleted Successfully!")
                    .user(Optional.ofNullable(data)).build()
            );
        } catch (UserNotFound e) {
            return ResponseEntity.ok(UserResponse.builder()
                    .success(false)
                    .message("User Deletion Failed!")
                    .user(Optional.empty()).build()
            );
        }
    }


    @DeleteMapping("/hard-delete/{userId}")
    public ResponseEntity<UserResponse> hardDeleteUser(@PathVariable UUID userId) {
        try {
            final var data = this.userService.hardDeleteUser(userId);
            return ResponseEntity.ok(UserResponse.builder()
                    .success(true)
                    .message("User Deleted Successfully!")
                    .user(Optional.ofNullable(data)).build()
            );
        } catch (UserNotFound e) {
            return ResponseEntity.ok(UserResponse.builder()
                    .success(false)
                    .message("User Deletion Failed!")
                    .user(Optional.empty()).build()
            );
        }
    }


}
