package kr.ac.kookmin.wink.backend.loadmap.service;

import kr.ac.kookmin.wink.backend.global.service.GeminiService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LoadmapService {

    private final LoadmapRepository loadmapRepository;
    private final LoadmapLikeRepository loadmapLikeRepository;
    private final LoadmapCircleRepository loadmapCircleRepository;
    private final UserRepository userRepository;
    private final GeminiService geminiService;

    @Transactional
    public GetLoadmapsBySearchResponseDto getLoadmapBySearch(String keyword) {
        List<Loadmap> loadmapList = loadmapRepository.findAll(LoadmapSpecifications.search(keyword.trim()));
        loadmapList.sort(Comparator.comparingLong(Loadmap::getView).reversed()); // 조회수 내림차순
        List<LoadmapAndColorDto> loadmapDtoList = new ArrayList<>();
        for (Loadmap loadmap : loadmapList) {
            User user = loadmap.getUser();
            ColorType color = getColor(loadmap);
            LoadmapDto loadmapDto = new LoadmapDto(loadmap.getId(), user.getId(), user.getName(), loadmap.getView(), loadmap.getTitle(), loadmap.getSummary());
            loadmapDtoList.add(new LoadmapAndColorDto(loadmapDto, color));
        }
        return new GetLoadmapsBySearchResponseDto(loadmapDtoList);
    }

    @Transactional
    public ColorType getColor(Loadmap loadmap) {
        ColorType color;
        Map<ColorType, Integer> map = new HashMap<>();
        List<LoadmapCircle> loadmapCircleList = loadmapCircleRepository.findAllByLoadmapId(loadmap.getId());
        for (LoadmapCircle loadmapCircle : loadmapCircleList) {
            map.put(loadmapCircle.getColorType(), map.getOrDefault(loadmapCircle.getColorType(), 0)+1);
        }
        List<ColorType> keySet = new ArrayList<>(map.keySet());
        keySet.sort((o1, o2) -> map.get(o2).compareTo(map.get(o1)));
        color = keySet.get(0);
        return color;
    }

    @Transactional
    public GetLoadmapsResponseDto getLoadmap(String name) {
        List<LoadmapAndColorDto> result = new ArrayList<>();
        List<Loadmap> loadmapList = loadmapRepository.findAllByOrderByViewDesc();
        for (Loadmap loadmap : loadmapList) {
            ColorType color = getColor(loadmap);
            User user = loadmap.getUser();
            LoadmapDto loadmapDto = new LoadmapDto(loadmap.getId(), user.getId(), user.getName(), loadmap.getView(), loadmap.getTitle(), loadmap.getSummary());
            result.add(new LoadmapAndColorDto(loadmapDto, color));
        }
        return new GetLoadmapsResponseDto(result, name);
    }

    @Transactional
    public LoadmapResponseDto getLoadmapById(long id) {
        Loadmap loadmap = loadmapRepository.findById(id);
        Long view = loadmap.getView();
        if (view == null) view = 0L;
        loadmap.setView(view+1);
        loadmapRepository.save(loadmap);
        ColorType color = getColor(loadmap);
        User user = loadmap.getUser();
        LoadmapDto loadmapDto = new LoadmapDto(loadmap.getId(), user.getId(), user.getName(), loadmap.getView(), loadmap.getTitle(), loadmap.getSummary());
        LoadmapAndColorDto loadmapAndColor = new LoadmapAndColorDto(loadmapDto, color);

        List<LoadmapCircle> loadmapCircleList = loadmapCircleRepository.findAllByLoadmapId(loadmap.getId());
        List<LoadmapCircleDto> loadmapCircleDtoList = new ArrayList<>();
        for (LoadmapCircle circle : loadmapCircleList) {
            Loadmap loadmap1 = circle.getLoadmap();
            User user1 = loadmap1.getUser();
            LoadmapDto loadmapDto1 = new LoadmapDto(loadmap1.getId(), user1.getId(), user1.getName(), loadmap1.getView(), loadmap1.getTitle(), loadmap1.getSummary());
            loadmapCircleDtoList.add(new LoadmapCircleDto(circle.getId(), loadmapDto1, circle.getTitle(), circle.getDate(), circle.getContent(), circle.getLevel(), circle.getColorType()));
        }
        return new LoadmapResponseDto(loadmapAndColor, loadmapCircleDtoList);
    }

    @Transactional
    public void postLoadmap(PostLoadmapRequestDto postLoadmapRequestDto, Long userId) {
        User user = userRepository.findById(userId).get();
        Loadmap loadmap = new Loadmap(user, postLoadmapRequestDto.getTitle());
        Loadmap savedLoadmap = loadmapRepository.save(loadmap);
        for (LoadmapCircleRequestDto circle : postLoadmapRequestDto.getLoadmapCircleList()) {
            LoadmapCircle loadmapCircle = new LoadmapCircle(savedLoadmap, circle.getTitle(), circle.getDate(), circle.getContent(), circle.getLevel(), circle.getColorType());
            loadmapCircleRepository.save(loadmapCircle);
        }

        // 3. 비동기 작업 시작
        geminiService.getLoadmapSummaryAsync(savedLoadmap, postLoadmapRequestDto.getLoadmapCircleList()).thenAccept(summary -> {
            savedLoadmap.setSummary(summary);
            loadmapRepository.save(savedLoadmap);
        });
    }

    @Transactional
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
