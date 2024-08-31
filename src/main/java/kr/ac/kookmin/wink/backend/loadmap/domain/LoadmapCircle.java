package kr.ac.kookmin.wink.backend.loadmap.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import lombok.Getter;

@Getter
@Entity
@Table(name = "loadmap_circle")
public class LoadmapCircle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loadmap_id")
    private Loadmap loadmap;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private String date;

    @Column(name = "content")
    private String content;

    @Column(name = "level")
    private Integer level;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private ColorType colorType;

    public LoadmapCircle() {}

    public LoadmapCircle(Loadmap loadmap, String title, String date, String content, Integer level, ColorType colorType) {
        this.loadmap = loadmap;
        this.title = title;
        this.date = date;
        this.content = content;
        this.level = level;
        this.colorType = colorType;
    }
}
