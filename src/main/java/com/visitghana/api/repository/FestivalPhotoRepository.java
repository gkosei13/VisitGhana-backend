package com.visitghana.api.repository;

import com.visitghana.api.model.FestivalPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FestivalPhotoRepository
        extends JpaRepository<FestivalPhoto, Long> {

    List<FestivalPhoto> findByFestivalIdOrderByDisplayOrder(Long festivalId);
}