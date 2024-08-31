package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoadmapCircleRequestDto {

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String content;

    private ColorType colorType;
}
