package kr.ac.kookmin.wink.backend.loadmap.service;

import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;
import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapLike;
import kr.ac.kookmin.wink.backend.loadmap.dto.*;
import kr.ac.kookmin.wink.backend.loadmap.enums.ColorType;
import kr.ac.kookmin.wink.backend.loadmap.repository.LoadmapCircleRepository;
import kr.ac.kookmin.wink.backend.loadmap.repository.LoadmapLikeRepository;
import kr.ac.kookmin.wink.backend.loadmap.repository.LoadmapRepository;
import kr.ac.kookmin.wink.backend.loadmap.repository.LoadmapSpecifications;
import kr.ac.kookmin.wink.backend.user.domain.User;
import kr.ac.kookmin.wink.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LoadmapService {

    private final LoadmapRepository loadmapRepository;
    private final LoadmapLikeRepository loadmapLikeRepository;
    private final LoadmapCircleRepository loadmapCircleRepository;
    private final UserRepository userRepository;

    public GetLoadmapsBySearchResponseDto getLoadmapBySearch(String keyword) {
        List<Loadmap> loadmapList = loadmapRepository.findAll(LoadmapSpecifications.search(keyword.trim()));
        loadmapList.sort(Comparator.comparingLong(Loadmap::getView).reversed()); // 조회수 내림차순
        List<LoadmapDto> loadmapDtoList = new ArrayList<>();
        for (Loadmap loadmap : loadmapList) {
            LoadmapDto loadmapDto = new LoadmapDto(loadmap.getId(), loadmap.getUser(), loadmap.getView(), loadmap.getTitle());
            loadmapDtoList.add(loadmapDto);
        }
        return new GetLoadmapsBySearchResponseDto(loadmapDtoList);
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

    public GetLoadmapsResponseDto getLoadmap() {
        List<LoadmapAndColorDto> result = new ArrayList<>();
        List<Loadmap> loadmapList = loadmapRepository.findAllByOrderByViewDesc();
        for (Loadmap loadmap : loadmapList) {
            ColorType color = getColor(loadmap);
            LoadmapDto loadmapDto = new LoadmapDto(loadmap.getId(), loadmap.getUser(), loadmap.getView(), loadmap.getTitle());
            result.add(new LoadmapAndColorDto(loadmapDto, color));
        }
        return new GetLoadmapsResponseDto(result);
    }

    public LoadmapResponseDto getLoadmapById(long id) {
        Loadmap loadmap = loadmapRepository.findById(id);
        Long view = loadmap.getView();
        if (view == null) view = 0L;
        loadmap.setView(view+1);
        loadmapRepository.save(loadmap);
        ColorType color = getColor(loadmap);
        LoadmapDto loadmapDto = new LoadmapDto(loadmap.getId(), loadmap.getUser(), loadmap.getView(), loadmap.getTitle());
        LoadmapAndColorDto loadmapAndColor = new LoadmapAndColorDto(loadmapDto, color);

        List<LoadmapCircle> loadmapCircleList = loadmapCircleRepository.findAllByLoadmapId(loadmap.getId());
        List<LoadmapCircleDto> loadmapCircleDtoList = new ArrayList<>();
        for (LoadmapCircle circle : loadmapCircleList) {
            Loadmap loadmap1 = circle.getLoadmap();
            LoadmapDto loadmapDto1 = new LoadmapDto(loadmap1.getId(), loadmap1.getUser(), loadmap1.getView(), loadmap1.getTitle());
            loadmapCircleDtoList.add(new LoadmapCircleDto(circle.getId(), loadmapDto1, circle.getTitle(), circle.getStartDate(), circle.getEndDate(), circle.getContent(), circle.getLevel(), circle.getColorType()));
        }
        return new LoadmapResponseDto(loadmapAndColor, loadmapCircleDtoList);
    }

    public void postLoadmap(PostLoadmapRequestDto postLoadmapRequestDto, Long userId) {
        User user = userRepository.findById(userId).get();
        Loadmap loadmap = new Loadmap(user, postLoadmapRequestDto.getSummary(), postLoadmapRequestDto.getView(), postLoadmapRequestDto.getTitle());
        Loadmap savedLoadmap = loadmapRepository.save(loadmap);
        for (LoadmapCircleRequestDto circle : postLoadmapRequestDto.getLoadmapCircleList()) {
            LoadmapCircle loadmapCircle = new LoadmapCircle(savedLoadmap, circle.getTitle(), circle.getStartTime(), circle.getEndTime(), circle.getContent(), circle.getLevel(), circle.getColorType());
            loadmapCircleRepository.save(loadmapCircle);
        }
    }

    public void postLoadmapLike(Long loadMapId, Long userId) {
        Optional<LoadmapLike> optionalLoadmapLike = loadmapLikeRepository.findByLoadmapIdAndUserId(loadMapId, userId);
        if (optionalLoadmapLike.isEmpty()) {
            User user = userRepository.findById(userId).get();
            Loadmap loadmap = loadmapRepository.findById(loadMapId).get();
            loadmapLikeRepository.save(new LoadmapLike(user, loadmap));
        } else {
            loadmapLikeRepository.deleteAllById(Collections.singleton(optionalLoadmapLike.get().getId()));
        }
    }
}
