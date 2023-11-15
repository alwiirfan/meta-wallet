package com.enigma.metawallet.util;

import com.enigma.metawallet.service.impl.UserDetailImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccountUtil {

    public UserDetailImpl blockAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailImpl) authentication.getPrincipal();
    }

}
