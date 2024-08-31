package kr.ac.kookmin.wink.backend.loadmap.dto;

import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;

public class LoadmapAndColor {
    private Loadmap loadmap;
    private ColorType color;

    public LoadmapAndColor(Loadmap loadmap, ColorType color) {
        this.loadmap = loadmap;
        this.color = color;
    }
}
