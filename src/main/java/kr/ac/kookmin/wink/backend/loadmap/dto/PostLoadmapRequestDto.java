package kr.ac.kookmin.wink.backend.loadmap.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostLoadmapRequestDto {

    private String title;

    private List<LoadmapCircleRequestDto> loadmapCircleList;
}
