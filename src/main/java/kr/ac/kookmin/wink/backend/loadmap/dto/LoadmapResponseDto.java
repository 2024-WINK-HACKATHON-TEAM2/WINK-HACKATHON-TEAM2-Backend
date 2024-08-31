package kr.ac.kookmin.wink.backend.loadmap.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LoadmapResponseDto {
    private LoadmapAndColorDto loadmapAndColor;
    private List<LoadmapCircleDto> loadmapCircleList;

    public LoadmapResponseDto(LoadmapAndColorDto loadmapAndColor, List<LoadmapCircleDto> loadmapCircleDtos) {
        this.loadmapAndColor = loadmapAndColor;
        this.loadmapCircleList = loadmapCircleDtos;
    }
}
