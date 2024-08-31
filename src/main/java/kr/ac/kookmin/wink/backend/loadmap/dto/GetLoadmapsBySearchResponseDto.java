package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;

import java.util.List;

public class GetLoadmapsBySearchResponseDto {

    List<Loadmap> list;

    public GetLoadmapsBySearchResponseDto(List<Loadmap> list) {
        this.list = list;
    }
}
