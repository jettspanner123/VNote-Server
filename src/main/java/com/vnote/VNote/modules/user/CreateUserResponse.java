package com.vnote.VNote.modules.user;

import com.vnote.VNote.modules.BaseResponse;
import com.vnote.VNote.modules.base.TwinTokens;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class CreateUserResponse extends BaseResponse {
    public SafeUser user;
    public TwinTokens tokens;
}
