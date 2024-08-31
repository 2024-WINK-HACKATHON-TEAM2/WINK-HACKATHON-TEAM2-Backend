package kr.ac.kookmin.wink.backend.loadmap.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "loadmap_like")
public class LoadmapLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "loadmap_id")
    private Loadmap loadmap;
    
    public LoadmapLike() {}

    public LoadmapLike(User user, Loadmap loadmap) {
        this.user = user;
        this.loadmap = loadmap;
    }
}
