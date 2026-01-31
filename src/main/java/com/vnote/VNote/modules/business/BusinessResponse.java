package com.vnote.VNote.modules.business;

import com.vnote.VNote.modules.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponse extends BaseResponse {
    public Optional<Business> business;
}
