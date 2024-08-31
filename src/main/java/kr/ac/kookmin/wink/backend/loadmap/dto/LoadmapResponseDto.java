package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;

import java.util.List;

public class LoadmapResponseDto {
    private LoadmapAndColor loadmapAndColor;
    private List<LoadmapCircle> loadmapCircleList;

    public LoadmapResponseDto(LoadmapAndColor loadmapAndColor, List<LoadmapCircle> loadmapCircleList) {
        this.loadmapAndColor = loadmapAndColor;
        this.loadmapCircleList = loadmapCircleList;
    }
}
