package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoadmapCircleRequestDto {

    private String title;

    private String date;

    private String content;

    private Integer level;

    private ColorType colorType;
}
