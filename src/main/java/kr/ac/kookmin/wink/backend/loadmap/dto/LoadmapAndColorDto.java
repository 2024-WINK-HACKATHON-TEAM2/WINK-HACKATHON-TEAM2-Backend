package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import lombok.Getter;

@Getter
public class LoadmapAndColorDto {
    private LoadmapDto loadmapDto;
    private ColorType color;

    public LoadmapAndColorDto(LoadmapDto loadmapDto, ColorType color) {
        this.loadmapDto = loadmapDto;
        this.color = color;
    }
}