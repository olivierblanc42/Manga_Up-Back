package fr.projet.manga_up.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "manga", schema = "manga_up")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_manga", nullable = false)
    private Integer id;

    @Column(name = "title", length = 500, unique=true, nullable = false)
    private String title;

    @Column(name = "release_date")
    private Instant releaseDate;

    @Lob
    @Column(name = "summary")
    private String summary;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "point_fidelity")
    private Integer pointFidelity;

    @Column(name = "price_excluding_taxe", precision = 10, scale = 2, nullable = false)
    private BigDecimal priceExcludingTaxe;

    @Column(name = "vat_rate", precision = 10, scale = 3, nullable = false)
    private BigDecimal vatRate;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "discount_percentage", precision = 10, scale = 2)
    private BigDecimal discountPercentage;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "Id_category")
    private Category category;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "author_manga",
            joinColumns = @JoinColumn(name = "manga_Id_manga"),
            inverseJoinColumns = @JoinColumn(name = "author_Id_author"))
    private Set<Author> authors = new HashSet<>();


    @JsonManagedReference
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "genre_manga",
            joinColumns = @JoinColumn(name = "manga_Id_manga"),
            inverseJoinColumns = @JoinColumn(name = "genre_Id_genre")
    )
    private Set<Genre> genres = new HashSet<>();


    @ManyToMany(mappedBy = "mangas")
    private Set<AppUser> users = new HashSet<>();

    @OneToMany(mappedBy="manga")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "manga")
    private List<BasketLine> basketLines;

    public BigDecimal getPriceExcludingTaxe() {
        return priceExcludingTaxe;
    }

    public void setPriceExcludingTaxe(BigDecimal priceExcludingTaxe) {
        this.priceExcludingTaxe = priceExcludingTaxe;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<BasketLine> getBasketLines() {
        return basketLines;
    }

    public void setBasketLines(List<BasketLine> basketLines) {
        this.basketLines = basketLines;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPointFidelity() {
        return pointFidelity;
    }

    public void setPointFidelity(Integer pointFidelity) {
        this.pointFidelity = pointFidelity;
    }

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }
/*
    @Override
    public String toString() {
        return "Manga{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                ", pointFidelity=" + pointFidelity +
                ", category=" + category +
                ", users=" + users +
                '}';
    }

 */
}