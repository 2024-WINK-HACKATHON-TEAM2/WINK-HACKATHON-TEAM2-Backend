package kr.ac.kookmin.wink.backend.loadmap.repository;

import jakarta.persistence.criteria.*;
import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LoadmapSpecifications {

    public static Specification<Loadmap> search(String keyword) {
        return (Root<Loadmap> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.distinct(true);

            Join<Loadmap, LoadmapCircle> loadmapCircleJoin = root.join("loadmapCircle", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();
            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("summary"), "%" + keyword + "%"));
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + keyword + "%"));
                predicates.add(criteriaBuilder.like(loadmapCircleJoin.get("title"), "%" + keyword + "%"));
                predicates.add(criteriaBuilder.like(loadmapCircleJoin.get("content"), "%" + keyword + "%"));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
