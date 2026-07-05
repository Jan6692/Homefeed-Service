package com.br.inspo.Homefeed_Service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sale_banner")
public class SaleBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "headline", nullable = false)
    private String headline;

    @Column(name = "cta_label", nullable = false)
    private String ctaLabel;

    @Column(name = "picture_url")
    private String pictureUrl;

}
