package kr.ac.kookmin.wink.backend.loadmap.repository;

import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoadmapRepository extends JpaRepository<Loadmap, Long>, JpaSpecificationExecutor<Loadmap> {

    public List<Loadmap> findAllByOrderByViewDesc();

    public Loadmap findById(long id);

}
