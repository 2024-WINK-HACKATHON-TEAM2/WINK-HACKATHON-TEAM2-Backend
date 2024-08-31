package kr.ac.kookmin.wink.backend.loadmap.repository;

import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoadmapCircleRepository extends JpaRepository<LoadmapCircle, Long> {

    public List<LoadmapCircle> findAllByLoadmapId(Long loadmapId);

}
