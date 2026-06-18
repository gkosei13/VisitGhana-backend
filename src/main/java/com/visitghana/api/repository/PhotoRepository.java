package com.visitghana.api.repository;

import com.visitghana.api.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhotoRepository
        extends JpaRepository<Photo, Long> {

    List<Photo> findBySiteSiteIdAndStatus(
            Long siteId, String status);
    List<Photo> findBySiteSiteId(long siteId);
}