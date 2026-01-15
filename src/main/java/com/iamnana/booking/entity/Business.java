package com.iamnana.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "businesses")
public class Business extends BaseEntity {

    @NotBlank(message = "Business name is required")
    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Boolean active = true;

}
