package kr.ac.kookmin.wink.backend.loadmap.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.backend.user.domain.User;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "loadmap")
public class Loadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "summary")
    private String summary;

    @Column(name = "view")
    private Long view = 0L;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "loadmap", cascade = CascadeType.ALL)
    private List<LoadmapCircle> loadmapCircles;

    public Loadmap() {}

    public Loadmap(User user, String title) {
        this.view = 0L;
        this.user = user;
        this.title = title;
    }

    public void setView(Long view) {
        this.view = view;
    }
}
