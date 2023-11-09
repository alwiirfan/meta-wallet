package com.enigma.metawallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_transfer")
@Builder(toBuilder = true)
public class Transfer {

    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(nullable = false)
    private Long balance;

    @CreationTimestamp
    private LocalDateTime transDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
