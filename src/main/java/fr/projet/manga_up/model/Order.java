package fr.projet.manga_up.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders", schema = "manga_up")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_orders", nullable = false)
    private Integer id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "validation_date")
    private Instant validationDate;

    @Column(name = "invoice_date", length = 50)
    private String invoiceDate;

    /* Quantité total hors taxe */
    @Column(name = "total_amount_excluding_taxe", precision = 10, scale = 2)
    private BigDecimal totalAmountExcludingTaxe;

    /* Total TVA */
    @Column(name = "total_vat", precision = 10, scale = 2)
    private BigDecimal totalVat;

    /* Total de remise en pourcentage */
    @Column(name = "total_discount_percentage", precision = 10, scale = 2)
    private BigDecimal totalDiscountPercentage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_carts", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_address", nullable = false)
    private Address address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

    public BigDecimal getTotalAmountExcludingTaxe() {
        return totalAmountExcludingTaxe;
    }

    public void setTotalAmountExcludingTaxe(BigDecimal totalAmountExcludingTaxe) {
        this.totalAmountExcludingTaxe = totalAmountExcludingTaxe;
    }

    public BigDecimal getTotalVat() {
        return totalVat;
    }

    public void setTotalVat(BigDecimal totalVat) {
        this.totalVat = totalVat;
    }

    public BigDecimal getTotalDiscountPercentage() {
        return totalDiscountPercentage;
    }

    public void setTotalDiscountPercentage(BigDecimal totalDiscountPercentage) {
        this.totalDiscountPercentage = totalDiscountPercentage;
    }
}