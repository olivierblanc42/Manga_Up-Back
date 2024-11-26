package fr.projet.manga_up.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "line_orders")
public class LinesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_line_orders", nullable = false)
    private Integer id;

    @Column(name = "number_articles", nullable = false)
    private Integer numberArticles;

    @Column(name = "price_excluding_taxe", precision = 10, scale = 2, nullable = false)
    private BigDecimal priceExcludingTaxe;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "discount_percentage", precision = 10, scale = 2, nullable = false)
    private BigDecimal discountPercentage;

    @Column(name = "vat_rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal vatRate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_manga", nullable = false)
    private Manga manga;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_cart", nullable = false)
    private Cart cart;

    public BigDecimal getPriceExcludingTaxe() {
        return priceExcludingTaxe;
    }

    public void setPriceExcludingTaxe(BigDecimal priceExcludingTaxe) {
        this.priceExcludingTaxe = priceExcludingTaxe;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberArticles() {
        return numberArticles;
    }

    public void setNumberArticles(Integer numberArticles) {
        this.numberArticles = numberArticles;
    }

	public Manga getManga() {
		return manga;
	}

	public void setManga(Manga manga) {
		this.manga = manga;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}


}