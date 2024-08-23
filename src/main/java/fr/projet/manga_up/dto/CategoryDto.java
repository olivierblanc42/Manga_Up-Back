package fr.projet.manga_up.dto;

import fr.projet.manga_up.model.Manga;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class CategoryDto {
    private Integer id;
    private String name;
    private String description;
    private Instant createdAt;
    private List<Integer> mangaIds;



    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<Integer> getMangaIds() {
        return mangaIds;
    }

    public void setMangaIds(List<Integer> mangaIds) {
        this.mangaIds = mangaIds;
    }
}
