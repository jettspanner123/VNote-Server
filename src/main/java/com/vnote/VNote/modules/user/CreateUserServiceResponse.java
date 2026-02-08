package com.vnote.VNote.modules.user;

import com.vnote.VNote.modules.base.TwinTokens;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateUserServiceResponse {
    private TwinTokens tokens;
    private SafeUser safeUser;
}
