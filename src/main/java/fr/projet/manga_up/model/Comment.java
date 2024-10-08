package fr.projet.manga_up.model;

import jakarta.persistence.*;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comment", schema = "manga_up")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_comment", nullable = false)
    private Integer id;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at")
    private Instant createdAt;

    @Lob
    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_manga", nullable = false)
    @JsonIgnore
    private Manga manga;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Id_user", nullable = false)
    private AppUser user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Manga getManga() {
		return manga;
	}

	public void setManga(Manga manga) {
		this.manga = manga;
	}

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

	@Override
	public String toString() {
		return "Comment [id=" + id + ", rating=" + rating + ", createdAt=" + createdAt + ", comment=" + comment
				+ ", manga=" + manga + "]";
	}
	
}