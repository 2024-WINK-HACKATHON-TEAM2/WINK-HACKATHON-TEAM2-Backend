package kr.ac.kookmin.wink.backend.loadmap.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GetLoadmapsResponseDto {

    List<LoadmapAndColorDto> list;
    String name;

    public GetLoadmapsResponseDto(List<LoadmapAndColorDto> list, String name) {
        this.list = list;
        this.name = name;
    }

}