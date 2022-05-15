package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_clothing")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clothing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "size")
    private int size;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    private Pol pol;



}
