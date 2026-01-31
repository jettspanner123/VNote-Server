package com.vnote.VNote.controllers;

import com.vnote.VNote.exceptions.BusinessAlreadyExists;
import com.vnote.VNote.modules.BaseResponse;
import com.vnote.VNote.modules.business.BusinessResponse;
import com.vnote.VNote.modules.validators.BusinessInputDTO;
import com.vnote.VNote.repositories.BusinessRepository;
import com.vnote.VNote.services.BusinessService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/business")
public class BusinessController {

    final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/health")
    public ResponseEntity<BaseResponse> healthCheck() {
        this.businessService.healthCheck();
        return ResponseEntity.ok().body(new BaseResponse(true, "Business Service Working Fine!"));
    }

    @PostMapping("/add")
    public ResponseEntity<BusinessResponse> create(@Valid @RequestBody BusinessInputDTO businessData) {
        try {
            final var data = this.businessService.create(businessData);
            return ResponseEntity.ok().body(BusinessResponse.builder().success(true).message("Business Added Successfully!").business(Optional.ofNullable(data)).build());
        } catch (BusinessAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BusinessResponse.builder().success(false).message(e.getMessage()).business(Optional.empty()).build());
        }
    }
}
