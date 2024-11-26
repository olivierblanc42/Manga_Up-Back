package fr.projet.manga_up.model;

import jakarta.persistence.*;

@Entity
@Table(name = "basket_line", schema = "manga_up")
public class BasketLine {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="Id_basket_line", nullable=false)
  private Integer id;

  @Column(name="quantity")
  private Integer quantity;

  @ManyToOne(fetch=FetchType.EAGER, optional = false)
  @JoinColumn(name="Id_user", nullable = false)
  private AppUser user;

  @ManyToOne(fetch=FetchType.EAGER, optional = false)
  @JoinColumn(name="Id_manga", nullable = false)
  private Manga manga;

  public AppUser getUser() {
    return user;
  }

  public void setUser(AppUser user) {
    this.user = user;
  }

  public Manga getManga() {
    return manga;
  }

  public void setManga(Manga manga) {
    this.manga = manga;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getId() {
    return id;
  }
}