package com.vnote.VNote.interfaces;

import com.vnote.VNote.modules.business.Business;
import com.vnote.VNote.modules.validators.BusinessInputDTO;

public interface IBusinessService {
    public boolean healthCheck();
    public Business create(BusinessInputDTO businessData);
}
