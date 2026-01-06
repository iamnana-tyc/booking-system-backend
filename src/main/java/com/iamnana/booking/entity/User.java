package com.iamnana.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private AppRole role;

    @Email
    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String userName;

    @NotBlank(message = "Password is required.")
    private String password;
}
