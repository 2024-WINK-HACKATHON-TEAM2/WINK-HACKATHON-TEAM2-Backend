package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;
import lombok.Getter;

import java.util.List;

@Getter
public class PostLoadmapRequestDto {

    private String summary;

    private Long view;

    private String title;

    private List<LoadmapCircleRequestDto> loadmapCircleList;
}
