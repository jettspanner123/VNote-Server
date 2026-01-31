package com.vnote.VNote.modules.user;

import com.vnote.VNote.modules.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class UserResponse extends BaseResponse {
    public Optional<SafeUser> user;
}
