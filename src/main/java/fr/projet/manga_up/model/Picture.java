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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_manga", nullable = false)
    @JsonIgnore
    private Manga manga;
    
    @Column(name = "title", length = 255)
    private String title;
    
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

	public Boolean getIsPoster() {
        return isPoster;
    }

    public void setIsPoster(Boolean isPoster) {
        this.isPoster = isPoster;
    }

	public Manga getManga() {
		return manga;
	}

	public void setManga(Manga manga) {
		this.manga = manga;
	}
}