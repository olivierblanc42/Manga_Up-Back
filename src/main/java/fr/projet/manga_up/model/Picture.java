package fr.projet.manga_up.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "picture", schema = "manga_up")
public class Picture{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_picture", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "Blob_img", columnDefinition="blob")
    private byte[] img;

    @Column(name = "is_Poster", nullable = false)
    private Boolean isPoster = false;

    @Column(name = "title", length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Id_manga", nullable = false)
    @JsonIgnore
    private Manga manga;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public Manga getManga() {
		return manga;
	}

    public Boolean getIsPoster() {
        return isPoster;
    }

    public void setIsPoster(Boolean poster) {
        isPoster = poster;
    }

    public void setManga(Manga manga) {
		this.manga = manga;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}