package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;

import java.util.List;

public class GetLoadmapsBySearchResponseDto {

    List<LoadmapDto> list;

    public GetLoadmapsBySearchResponseDto(List<LoadmapDto> list) {
        this.list = list;
    }
}
