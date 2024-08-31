package kr.ac.kookmin.wink.backend.loadmap.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GetLoadmapsResponseDto {

    List<LoadmapAndColorDto> list;

    public GetLoadmapsResponseDto(List<LoadmapAndColorDto> list) {
        this.list = list;
    }

}