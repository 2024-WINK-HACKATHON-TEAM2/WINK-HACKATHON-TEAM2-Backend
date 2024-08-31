package kr.ac.kookmin.wink.backend.loadmap.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.backend.user.domain.User;
import lombok.Getter;

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

    public Loadmap() {}

    public Loadmap(User user, String summary, Long view, String title) {
        this.user = user;
        this.summary = summary;
        this.view = view;
        this.title = title;
    }

    public void setView(Long view) {
        this.view = view;
    }
}
