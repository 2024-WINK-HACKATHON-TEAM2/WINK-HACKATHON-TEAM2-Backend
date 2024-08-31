package kr.ac.kookmin.wink.backend.loadmap.service;

import jakarta.persistence.criteria.*;
import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;
import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapLike;
import kr.ac.kookmin.wink.backend.loadmap.dto.LoadmapAndColor;
import kr.ac.kookmin.wink.backend.loadmap.dto.LoadmapCircleRequestDto;
import kr.ac.kookmin.wink.backend.loadmap.dto.LoadmapResponseDto;
import kr.ac.kookmin.wink.backend.loadmap.dto.PostLoadmapRequestDto;
import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import kr.ac.kookmin.wink.backend.loadmap.repository.LoadmapCircleRepository;
import kr.ac.kookmin.wink.backend.loadmap.repository.LoadmapLikeRepository;
import kr.ac.kookmin.wink.backend.loadmap.repository.LoadmapRepository;
import kr.ac.kookmin.wink.backend.loadmap.repository.LoadmapSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoadmapService {

    private Loadmap loadmap;
    private LoadmapRepository loadmapRepository;
    private LoadmapLikeRepository loadmapLikeRepository;
    private LoadmapCircleRepository loadmapCircleRepository;

    public List<Loadmap> getLoadmapBySearch(String keyword) {
        List<Loadmap> loadmapList = loadmapRepository.findAll(LoadmapSpecifications.search(keyword));
        loadmapList.sort(Comparator.comparingLong(Loadmap::getView).reversed()); // 조회수 내림차순
        return loadmapList;
    }

    public ColorType getColor(Loadmap loadmap) {
        ColorType color;
        Map<ColorType, Integer> map = new HashMap<>();
        List<LoadmapCircle> loadmapCircleList = loadmapCircleRepository.findAllByLoadmapId(loadmap.getId());
        for (LoadmapCircle loadmapCircle : loadmapCircleList) {
            map.put(loadmapCircle.getColorType(), map.getOrDefault(loadmapCircle.getColorType(), 0));
        }
        List<ColorType> keySet = new ArrayList<>(map.keySet());
        Collections.sort(keySet);
        Collections.reverse(keySet);
        color = keySet.get(0);
        return color;
    }

    public List<LoadmapAndColor> getLoadmap() {
        List<LoadmapAndColor> result = new ArrayList<>();
        List<Loadmap> loadmapList = loadmapRepository.findAllByOrderByViewDesc();
        for (Loadmap loadmap : loadmapList) {
            ColorType color = getColor(loadmap);
            result.add(new LoadmapAndColor(loadmap, color));
        }

        return result;
    }

    public LoadmapResponseDto getLoadmapById(long id) {
        Loadmap loadmap = loadmapRepository.findById(id);
        Long view = loadmap.getView();
        loadmap.setView(view+1);
        loadmapRepository.save(loadmap);
        ColorType color = getColor(loadmap);
        LoadmapAndColor loadmapAndColor = new LoadmapAndColor(loadmap, color);

        return new LoadmapResponseDto(loadmapAndColor, loadmapCircleRepository.findAllByLoadmapId(loadmap.getId()));
    }

    public Loadmap postLoadmap(PostLoadmapRequestDto postLoadmapRequestDto) {
        Loadmap loadmap = new Loadmap(postLoadmapRequestDto.getSummary(), postLoadmapRequestDto.getView(), postLoadmapRequestDto.getTitle());
        Loadmap savedLoadmap = loadmapRepository.save(loadmap);
        for (LoadmapCircleRequestDto circle : postLoadmapRequestDto.getLoadmapCircleList()) {
            LoadmapCircle loadmapCircle = new LoadmapCircle(savedLoadmap, circle.getTitle(), circle.getStartTime(), circle.getEndTime(), circle.getContent(), circle.getColorType());
            loadmapCircleRepository.save(loadmapCircle);
        }

        return savedLoadmap;
    }

    public boolean postLoadmapLike(Long id) {
        List<LoadmapLike> loadmapLikeList = loadmapLikeRepository.findAllByUserId();
        if (loadmapLikeList.size() == 0) {
            LoadmapLike loadmapLike = new LoadmapLike();
            loadmapLikeRepository.save(loadmapLike);
            return true;
        } else {
            loadmapLikeRepository.deleteAllById(Collections.singleton(loadmapLikeList.get(0).getId()));
            return false;
        }
    }
}
