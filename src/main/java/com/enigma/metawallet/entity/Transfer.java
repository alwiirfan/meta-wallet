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

    @Column(name = "transfer_in")
    private Long transferIn;

    @Column(name = "transfer_out")
    private Long transferOut;

    @Column(name = "from_user_id", nullable = false)
    private String fromUserId;

    @Column(name = "to_user_id", nullable = false)
    private String toUserId;

    @Column(name = "trans_date")
    private LocalDateTime transDate;

    @Column(name = "tax")
    private Long tax;

}
