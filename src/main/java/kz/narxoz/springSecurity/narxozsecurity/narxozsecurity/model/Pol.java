package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_pol")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pol {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pol")
    private String pol ;




}
