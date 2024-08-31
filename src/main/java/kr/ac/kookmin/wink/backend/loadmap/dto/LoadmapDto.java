package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.user.domain.User;

public class LoadmapDto {
    private Long id;
    private User user;
    private Long view;
    private String title;

    public LoadmapDto(Long id, User user, Long view, String title) {
        this.id = id;
        this.user = user;
        this.view = view;
        this.title = title;
    }
}
