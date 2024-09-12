package fr.projet.manga_up.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "gender", schema = "manga_up")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_gender", nullable = false)
    private Integer id;

    @Column(name = "label", length = 50)
    private String label;

    @OneToMany(mappedBy="gender")
    @JsonIgnore
    private List<AppUser> users;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

	public List<AppUser> getUsers() {
		return users;
	}

	public void setUsers(List<AppUser> users) {
		this.users = users;
	}

}