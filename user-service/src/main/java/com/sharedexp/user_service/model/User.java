package com.sharedexp.user_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user") // or "users", "user_account", etc.
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    //    public String getName() { return name; }
    //    public void setName(String name) { this.name = name; }
    //    public String getEmail() { return email; }
    //    public void setEmail(String email) { this.email = email; }

}



