package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;

import java.time.LocalDateTime;

public class LoadmapCircleDto {
    private Long id;
    private LoadmapDto loadmapDto;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String content;
    private ColorType colorType;

    public LoadmapCircleDto(Long id, LoadmapDto loadmapDto, String title, LocalDateTime startDate, LocalDateTime endDate, String content, ColorType colorType) {
        this.id = id;
        this.loadmapDto = loadmapDto;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.colorType = colorType;
    }
}
