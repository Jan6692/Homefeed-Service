package entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "greeting")
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "text", nullable = false)
    private String text;

}
