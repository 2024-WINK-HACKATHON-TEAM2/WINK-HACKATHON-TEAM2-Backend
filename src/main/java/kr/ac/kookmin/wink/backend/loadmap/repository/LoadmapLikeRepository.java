package kr.ac.kookmin.wink.backend.loadmap.repository;

import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoadmapLikeRepository extends JpaRepository<LoadmapLike, Long> {

    public List<LoadmapLike> findAllByUserId();
}
