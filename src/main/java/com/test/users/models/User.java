package com.test.users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",  uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Size(max = 36)
    private String id;

    @Size(max = 100)
    private String name;

    private String email;

    @Size(max = 500)
    private String password;

    @CreatedDate
    private Date created;

    @LastModifiedDate
    private Date updated;

    @CreatedDate
    private Date lastlogin;

    @Size(max = 500)
    private String token;

    private Boolean isactive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Phone> phones;

    @Override
    public String toString(){
        return "User: {" +
                "name:" + this.name +
                ", email:" + this.email +
                ", password:" + this.password +
                ", created:" + this.created +
                ", updated:" + this.updated +
                ", last_login:" + this.lastlogin +
                "}";
    }

}

