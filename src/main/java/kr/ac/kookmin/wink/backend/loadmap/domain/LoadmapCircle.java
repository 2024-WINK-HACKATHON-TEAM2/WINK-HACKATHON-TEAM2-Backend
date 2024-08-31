package kr.ac.kookmin.wink.backend.loadmap.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "content")
    private String content;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private ColorType colorType;

    public LoadmapCircle() {}

    public LoadmapCircle(Loadmap loadmap, String title, LocalDateTime startDate, LocalDateTime endDate, String content, ColorType colorType) {
        this.loadmap = loadmap;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.colorType = colorType;
    }
}
