package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import lombok.Getter;

@Getter
public class LoadmapCircleDto {
    private Long id;
    private LoadmapDto loadmapDto;
    private String title;
    private String date;
    private String content;
    private Integer level;
    private ColorType colorType;

    public LoadmapCircleDto(Long id, LoadmapDto loadmapDto, String title, String date, String content, Integer level, ColorType colorType) {
        this.id = id;
        this.loadmapDto = loadmapDto;
        this.title = title;
        this.date = date;
        this.content = content;
        this.level = level;
        this.colorType = colorType;
    }
}
