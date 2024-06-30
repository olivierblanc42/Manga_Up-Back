package fr.projet.manga_up.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "genre", schema = "manga_up")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_genre", nullable = false)
    private Integer id;

    @Column(name = "label", length = 50)
    private String label;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToMany
    @JoinTable(name = "genre_manga",
            joinColumns = @JoinColumn(name = "genre_Id_genre"),
            inverseJoinColumns = @JoinColumn(name = "manga_Id_manga"))
    @JsonIgnore
    private Set<Manga> mangas = new HashSet<>();

    public Set<Manga> getMangas() {
        return mangas;
    }

    public void setMangas(Set<Manga> mangas) {
        this.mangas = mangas;
    }

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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}