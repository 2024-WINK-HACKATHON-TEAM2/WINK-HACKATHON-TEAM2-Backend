package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import lombok.Getter;

import java.util.List;

@Getter
public class GetLoadmapsResponseDto {

    List<LoadmapAndColorDto> list;

    public GetLoadmapsResponseDto(List<LoadmapAndColorDto> list) {
        this.list = list;
    }

}