package fr.projet.manga_up.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    private String role;

    public Role() {
        super();
    }

    public Role(String role) {
        super();
        this.role=role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
