package kr.ac.kookmin.wink.backend.loadmap.dto;

import lombok.Getter;

@Getter
public class LoadmapDto {
    private Long id;
    private Long user_id;
    private String user_name;
    private Long view;
    private String title;
    private int like;
    private boolean i_like;

    private String summary;

    public LoadmapDto(Long id, Long user_id, String user_name, Long view, String title, String summary, int like, boolean i_like) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.view = view;
        this.title = title;
        this.summary = summary;
        this.like = like;
        this.i_like = i_like;
    }
}
