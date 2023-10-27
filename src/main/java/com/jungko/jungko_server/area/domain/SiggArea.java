package com.jungko.jungko_server.area.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class SiggArea {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String admCode;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "siggArea")
    private Set<EmdArea> emdAreas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_area_id", nullable = false)
    private SidoArea sidoArea;

}
