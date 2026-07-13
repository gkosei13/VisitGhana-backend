package com.visitghana.api.controller;

import com.visitghana.api.model.SitePhoto;
import com.visitghana.api.repository.SitePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/site-photos")
@CrossOrigin(origins = "*")
public class SitePhotoController {

    @Autowired
    private SitePhotoRepository sitePhotoRepository;

    @GetMapping("/{siteId}")
    public List<SitePhoto> getSitePhotos(
            @PathVariable Long siteId) {
        return sitePhotoRepository
                .findBySiteIdOrderByDisplayOrder(siteId);
    }
}