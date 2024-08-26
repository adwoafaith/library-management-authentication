package com.alibou.library.modal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String role;

    //we inporting users here because multiples users can have multiple roles so it a many to many relationship
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // when i role is fetched users will be fetched as well it will result in an infinite loops so we use the JsonIgnore annotation to prevent that
    private List<User> users;

    @CreatedDate
    @Column(updatable = false) // false meaning when i update my entity it shouldn't update the date created as well
    private LocalDate dateCreated;

    @LastModifiedDate
    @Column(insertable  = false,updatable = false)
    private LocalDate dateModified;
}
