package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.user.domain.User;
import lombok.Getter;

@Getter
public class LoadmapDto {
    private Long id;
    private User user;
    private Long view;
    private String title;

    private String summary;

    public LoadmapDto(Long id, User user, Long view, String title, String summary) {
        this.id = id;
        this.user = user;
        this.view = view;
        this.title = title;
        this.summary = summary;
    }
}
