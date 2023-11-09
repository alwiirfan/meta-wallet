package com.enigma.metawallet.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_wallet")
@Builder(toBuilder = true)
public class Wallet {

    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    private Long balance;

    @OneToOne(mappedBy = "wallet")
    private User user;

    @OneToOne(mappedBy = "wallet")
    private Admin admin;

}
