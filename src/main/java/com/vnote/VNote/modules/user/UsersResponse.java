package com.vnote.VNote.modules.user;

import com.vnote.VNote.modules.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UsersResponse extends BaseResponse {
    public Optional<java.util.List<SafeUser>> users;
}
