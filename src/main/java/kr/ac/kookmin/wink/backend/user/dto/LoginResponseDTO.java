package kr.ac.kookmin.wink.backend.user.dto;

import lombok.Builder;

@Builder
public record LoginResponseDTO(
    String token,
    Long userId
) {
}
