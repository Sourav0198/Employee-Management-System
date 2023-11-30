package com.example.EmployeeManager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
@Builder
//@ToString(exclude = "employee")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @NotBlank
    @Column(name = ("street"), length = 20)
    private String street;

    @NotBlank
    @Column(name = ("city"), length = 20)
    private String city;

    @NotBlank
    @Column(name = ("state"), length = 20)
    private String state;

    @NotBlank
    @Column(name = ("postal_code"), length = 6)
    private String postalCode;

    @NotBlank
    @Column(name = ("country"), length = 20)
    private String country;

//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
//    @NotNull
//
//    private Employee addressEmployee;


    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
