package com.vnote.VNote.services;

import com.vnote.VNote.entities.UserEntity;
import com.vnote.VNote.exceptions.UserAlreadyExists;
import com.vnote.VNote.exceptions.UserNotFound;
import com.vnote.VNote.interfaces.IUserService;
import com.vnote.VNote.modules.BaseResponse;
import com.vnote.VNote.modules.base.TwinTokens;
import com.vnote.VNote.modules.user.CreateUserServiceResponse;
import com.vnote.VNote.modules.user.SafeUser;
import com.vnote.VNote.modules.validators.UserInputDTO;
import com.vnote.VNote.repositories.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.ToString;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    public final JWTService jwtService;

    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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

    @Transactional
    @Override
    public CreateUserServiceResponse createUser(UserInputDTO userData) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userData.fullName);
        userEntity.setPassword(userData.password);
        userEntity.setPhoneNumber(userData.phoneNumber);
        userEntity.setEmail(userData.email.toLowerCase().trim());

        String newAccessToken = "", newRefreshToken = "";

        try {
            newAccessToken = this.jwtService.generateAccessToken(userEntity.getId());
            newRefreshToken = this.jwtService.generateRefreshToken(userEntity.getId());
            this.userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            final var message = e.getMessage();
            if (message.contains("users_email_key")) throw new UserAlreadyExists("Email already exists!");
            if (message.contains("users_phone_number_key")) throw new UserAlreadyExists("Phone number already exists!");
        }

        return CreateUserServiceResponse.builder()
                .safeUser(SafeUser.fromEntity(userEntity))
                .tokens(TwinTokens.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build()
                )
                .build();
    }

    @Override
    public List<SafeUser> getAllUsers() {
        final var users = this.userRepository.findAll();
        return users.stream().map(SafeUser::fromEntity).toList();
    }


    @Transactional
    @Override
    public SafeUser softDeleteUser(UUID userId) {
        final var user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User id not found!"));
        if (user.getDeletedAt() != null) throw new UserNotFound("User Not Found!");
        user.setDeletedAt(LocalDateTime.now());
        this.userRepository.save(user);
        return SafeUser.fromEntity(user);
    }

    @Transactional
    @Override
    public SafeUser hardDeleteUser(UUID userId) {
        final var user = this._checkAndGiveIfUserExists(userId);
        this.userRepository.delete(user);
        return SafeUser.fromEntity(user);
    }


    UserEntity _checkAndGiveIfUserExists(UUID userId) throws UserNotFound {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User id not found!"));
    }
}
