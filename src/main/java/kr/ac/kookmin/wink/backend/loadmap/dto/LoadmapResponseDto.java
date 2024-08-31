package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;

import java.util.List;

public class LoadmapResponseDto {
    private LoadmapAndColorDto loadmapAndColor;
    private List<LoadmapCircleDto> loadmapCircleList;

    public LoadmapResponseDto(LoadmapAndColorDto loadmapAndColor, List<LoadmapCircleDto> loadmapCircleDtos) {
        this.loadmapAndColor = loadmapAndColor;
        this.loadmapCircleList = loadmapCircleDtos;
    }
}
