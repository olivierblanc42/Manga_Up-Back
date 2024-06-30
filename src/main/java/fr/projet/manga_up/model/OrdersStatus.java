package fr.projet.manga_up.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders_status", schema = "manga_up")
public class OrdersStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_orders_status", nullable = false)
    private Integer id;

    @Column(name = "label", length = 50)
    private String label;

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

}