package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;

import java.util.List;

public class LoadmapResponseDto {
    private LoadmapAndColorDto loadmapAndColor;
    private List<LoadmapCircle> loadmapCircleList;

    public LoadmapResponseDto(LoadmapAndColorDto loadmapAndColor, List<LoadmapCircle> loadmapCircleList) {
        this.loadmapAndColor = loadmapAndColor;
        this.loadmapCircleList = loadmapCircleList;
    }
}
