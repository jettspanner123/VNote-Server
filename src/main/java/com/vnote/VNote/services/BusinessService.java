package com.vnote.VNote.services;

import com.vnote.VNote.entities.BusinessEntity;
import com.vnote.VNote.exceptions.BusinessAlreadyExists;
import com.vnote.VNote.exceptions.UserNotFound;
import com.vnote.VNote.interfaces.IBusinessService;
import com.vnote.VNote.modules.business.Business;
import com.vnote.VNote.modules.validators.BusinessInputDTO;
import com.vnote.VNote.repositories.BusinessRepository;
import com.vnote.VNote.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BusinessService implements IBusinessService {
    final BusinessRepository businessRepository;
    final UserRepository userRepository;

    public BusinessService(BusinessRepository businessRepository, UserRepository userRepository) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
    }


    @Override
    public boolean healthCheck() {
        this.businessRepository.count();
        return true;
    }

    @Override
    public Business create(BusinessInputDTO businessData) {
        if(this.businessRepository.existsByUserIdAndName(businessData.userId, businessData.name)) throw new BusinessAlreadyExists("Business already exists!");

        final var user = this.userRepository.findById(businessData.userId).orElseThrow(() -> new UserNotFound("User id not found!"));
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setName(businessData.name);
        businessEntity.setUser(user);
        businessEntity.setType(businessData.type);

        this.businessRepository.save(businessEntity);

        return Business.fromEntity(businessEntity);
    }
}
