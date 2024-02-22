package com.test.users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Size(max = 36)
    private String id;

    @Size(max = 12)
    private String number;

    @Size(max = 10)
    private String citycode;

    @Size(max = 10)
    private String countrycode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
