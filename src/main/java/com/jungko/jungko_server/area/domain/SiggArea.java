package com.jungko.jungko_server.area.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
