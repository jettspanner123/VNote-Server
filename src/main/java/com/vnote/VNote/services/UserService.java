package com.vnote.VNote.services;

import com.vnote.VNote.entities.UserEntity;
import com.vnote.VNote.exceptions.UserAlreadyExists;
import com.vnote.VNote.exceptions.UserNotFound;
import com.vnote.VNote.interfaces.IUserService;
import com.vnote.VNote.modules.BaseResponse;
import com.vnote.VNote.modules.user.SafeUser;
import com.vnote.VNote.modules.user.UserResponse;
import com.vnote.VNote.modules.validators.UserInputDTO;
import com.vnote.VNote.repositories.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public BaseResponse healthCheck() {
        this.userRepository.count();
        return new BaseResponse(true, "User Service Working Fine!");
    }

    @Override
    public SafeUser getUserById(UUID userId) {
        UserEntity user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User id not found!"));
        return SafeUser.fromEntity(user);
    }

    @Override
    public SafeUser createUser(UserInputDTO userData) {
        this.userRepository.findByEmail(userData.email).ifPresent(user -> {
            throw new UserAlreadyExists("Email Already Exist!");
        });

//        final String encodedPassword = this.passwordEncoder.encode(userData.password);

        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userData.fullName);
        userEntity.setPassword(userData.password);
        userEntity.setPhoneNumber(userData.phoneNumber);
        userEntity.setEmail(userData.email);

        this.userRepository.save(userEntity);

        return SafeUser.fromEntity(userEntity);
    }

    @Override
    public java.util.List<SafeUser> getAllUsers() {
        final var users = this.userRepository.findAll();
        return users.stream().map(SafeUser::fromEntity).toList();
    }
}
