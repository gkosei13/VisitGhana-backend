package com.visitghana.api.repository;

import com.visitghana.api.model.SitePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SitePhotoRepository
        extends JpaRepository<SitePhoto, Long> {

    List<SitePhoto> findBySiteIdOrderByDisplayOrder(Long siteId);
}