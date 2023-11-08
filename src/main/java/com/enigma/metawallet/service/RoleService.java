package com.enigma.metawallet.service;

import com.enigma.metawallet.entity.Role;
import com.enigma.metawallet.entity.roleContract.ERole;

public interface RoleService {

    Role getOrSave(ERole role);

}
