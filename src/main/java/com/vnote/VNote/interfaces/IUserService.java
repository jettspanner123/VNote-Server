package com.vnote.VNote.interfaces;

import com.vnote.VNote.modules.BaseResponse;
import com.vnote.VNote.modules.user.SafeUser;
import com.vnote.VNote.modules.user.UserResponse;
import com.vnote.VNote.modules.validators.UserInputDTO;

import java.util.UUID;

public interface IUserService {
    public BaseResponse healthCheck();
    public SafeUser createUser(UserInputDTO userData);
    public SafeUser getUserById(UUID userId);
    public java.util.List<SafeUser> getAllUsers();
}
