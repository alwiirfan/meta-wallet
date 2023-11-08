package com.enigma.metawallet.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_user")
@Builder(toBuilder = true)
public class User {

    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(length = 50)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 15, nullable = false, unique = true)
    private String mobilePhone;

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;

}
