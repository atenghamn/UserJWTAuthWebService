package com.example.userjwtauthwebservice.user.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdministrator;


    public User(String username, String password, boolean isAdministrator){
        this.username = username;
        this.password = password;
        this.isAdministrator = isAdministrator;
    }

    public boolean getIsAdministrator() {
        return isAdministrator;
    }


}
