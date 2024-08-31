package kr.ac.kookmin.wink.backend.loadmap.controller;

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

    @GetMapping
    public ResponseEntity<GetLoadmapsBySearchResponseDto> getLoadmapsBySearch(@RequestParam String keyword) {
        return ResponseEntity.ok(loadmapService.getLoadmapBySearch(keyword));
    }

    @GetMapping("/main")
    public ResponseEntity<GetLoadmapsResponseDto> getLoadmaps(
            HttpServletRequest request
    ) {
        Long userId = jwtProvider.getUserId(request);
        Optional<User> user = userRepository.findById(userId);
        String name = user.get().getName();
        return ResponseEntity.ok(loadmapService.getLoadmap(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoadmapResponseDto> getLoadmapById(@PathVariable Long id) {
        return ResponseEntity.ok(loadmapService.getLoadmapById(id));
    }

    @PostMapping
    public ResponseEntity<Void> postLoadmap(
            @RequestBody PostLoadmapRequestDto postLoadmapRequestDto,
            HttpServletRequest request
            ) {
        loadmapService.postLoadmap(postLoadmapRequestDto, jwtProvider.getUserId(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> postLoadmapLike(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        loadmapService.postLoadmapLike(id, jwtProvider.getUserId(request));
        return ResponseEntity.ok().build();
    }
}
