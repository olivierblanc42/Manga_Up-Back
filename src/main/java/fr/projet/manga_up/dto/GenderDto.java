package fr.projet.manga_up.dto;

import java.util.Set;

public class GenderDto {
    private Integer id;
    private String label;
    private Set<Integer> userId;  // Liste des IDs des utilisateurs

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Integer> getUserId() {
        return userId;
    }

    public void setUserId(Set<Integer> userId) {
        this.userId = userId;
    }
}
