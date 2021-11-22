package com.destruction.myDemolish.domain;

import com.destruction.myDemolish.domainOne.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private boolean enabled;
    private ApplicationUserRole applicationUserRole;

}
