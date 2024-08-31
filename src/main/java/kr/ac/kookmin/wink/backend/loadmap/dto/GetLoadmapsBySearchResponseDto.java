package kr.ac.kookmin.wink.backend.loadmap.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GetLoadmapsBySearchResponseDto {

    List<LoadmapAndColorDto> list;

    public GetLoadmapsBySearchResponseDto(List<LoadmapAndColorDto> list) {
        this.list = list;
    }
}
