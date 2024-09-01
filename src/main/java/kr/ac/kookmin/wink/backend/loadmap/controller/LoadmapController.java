package kr.ac.kookmin.wink.backend.loadmap.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import kr.ac.kookmin.wink.backend.global.config.security.JwtProvider;
import kr.ac.kookmin.wink.backend.loadmap.dto.GetLoadmapsBySearchResponseDto;
import kr.ac.kookmin.wink.backend.loadmap.dto.GetLoadmapsResponseDto;
import kr.ac.kookmin.wink.backend.loadmap.dto.LoadmapResponseDto;
import kr.ac.kookmin.wink.backend.loadmap.dto.PostLoadmapRequestDto;
import kr.ac.kookmin.wink.backend.loadmap.service.LoadmapService;
import kr.ac.kookmin.wink.backend.user.domain.User;
import kr.ac.kookmin.wink.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/loadmap")
public class LoadmapController {

    private final LoadmapService loadmapService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Operation(summary = "로드맵 검색")
    @GetMapping
    public ResponseEntity<GetLoadmapsBySearchResponseDto> getLoadmapsBySearch(@RequestParam String keyword) {
        return ResponseEntity.ok(loadmapService.getLoadmapBySearch(keyword));
    }

    @Operation(summary = "로드맵 메인 페이지 조회")
    @GetMapping("/main")
    public ResponseEntity<GetLoadmapsResponseDto> getLoadmaps(
            HttpServletRequest request
    ) {
        Long userId = jwtProvider.getUserId(request);
        Optional<User> user = userRepository.findById(userId);
        String name = user.get().getName();
        return ResponseEntity.ok(loadmapService.getLoadmap(name));
    }

    @Operation(summary = "로드맵 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<LoadmapResponseDto> getLoadmapById(@PathVariable Long id) {
        return ResponseEntity.ok(loadmapService.getLoadmapById(id));
    }

    @Operation(summary = "로드맵 등록")
    @PostMapping
    public ResponseEntity<Void> postLoadmap(
            @RequestBody PostLoadmapRequestDto postLoadmapRequestDto,
            HttpServletRequest request
            ) {
        loadmapService.postLoadmap(postLoadmapRequestDto, jwtProvider.getUserId(request));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로드맵 좋아요 & 좋아요 취소")
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> postLoadmapLike(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        loadmapService.postLoadmapLike(id, jwtProvider.getUserId(request));
        return ResponseEntity.ok().build();
    }
}
