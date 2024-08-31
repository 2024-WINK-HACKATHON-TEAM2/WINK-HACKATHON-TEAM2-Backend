package kr.ac.kookmin.wink.backend.loadmap.controller;

import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.dto.LoadmapAndColor;
import kr.ac.kookmin.wink.backend.loadmap.dto.LoadmapResponseDto;
import kr.ac.kookmin.wink.backend.loadmap.dto.PostLoadmapRequestDto;
import kr.ac.kookmin.wink.backend.loadmap.service.LoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loadmap")
public class LoadmapController {

    private  LoadmapService loadmapService;

    @Autowired
    public LoadmapController(LoadmapService theLoadmapService) {
        loadmapService = theLoadmapService;
    }

    @GetMapping("/")
    public List<Loadmap> getLoadmapsBySearch(@RequestBody String keyword) {
        return loadmapService.getLoadmapBySearch(keyword);
    }

    @GetMapping("/main")
    public List<LoadmapAndColor> getLoadmaps() {
        return loadmapService.getLoadmap();
    }

    @GetMapping("/{id}")
    public LoadmapResponseDto getLoadmapById(@PathVariable Long id) {
        return loadmapService.getLoadmapById(id);
    }

    @PostMapping("/")
    public Loadmap postLoadmap(
            @RequestBody PostLoadmapRequestDto postLoadmapRequestDto
            ) {
        return loadmapService.postLoadmap(postLoadmapRequestDto);
    }

    @PostMapping("/{id}/like")
    public boolean postLoadmapLike(@PathVariable Long id) {
        return loadmapService.postLoadmapLike(id);
    }
}
