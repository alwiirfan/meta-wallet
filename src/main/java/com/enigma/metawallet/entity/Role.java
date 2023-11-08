package com.enigma.metawallet.entity;

import com.enigma.metawallet.entity.roleContract.ERole;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_role")
@Builder(toBuilder = true)
public class Role {

    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Enumerated(EnumType.STRING)
    private ERole role;

}
