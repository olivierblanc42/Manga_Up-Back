package fr.projet.manga_up.dto;

import java.util.Set;

import java.time.Instant;

public class GenreDto {
    private Integer id;
    private String label;
    private Instant createdDate;
    private byte[] img;
    private Set<Integer> mangaIds;  // Liste des IDs des mangas

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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Set<Integer> getMangaIds() {
        return mangaIds;
    }

    public void setMangaIds(Set<Integer> mangaIds) {
        this.mangaIds = mangaIds;
    }
}
