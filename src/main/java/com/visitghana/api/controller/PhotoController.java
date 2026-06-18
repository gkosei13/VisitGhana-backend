package com.visitghana.api.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.visitghana.api.model.Photo;
import com.visitghana.api.model.TouristSite;
import com.visitghana.api.repository.PhotoRepository;
import com.visitghana.api.repository.TouristSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "*")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private TouristSiteRepository siteRepository;

    @Autowired
    private Cloudinary cloudinary;

    // Upload a photo
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("siteId") Long siteId,
            @RequestParam(value = "caption",
                    required = false) String caption,
            @RequestParam(value = "userId",
                    required = false) Long userId) {

        try {
            // Confirm the site exists
            TouristSite site = siteRepository
                    .findById(siteId)
                    .orElse(null);

            if (site == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Site not found");
            }

            // Upload the image to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "visitghana/sites/" + siteId
                    )
            );

            String imageUrl =
                    (String) uploadResult.get("secure_url");

            // Save the photo record in the database
            Photo photo = new Photo();
            photo.setSite(site);
            photo.setImageUrl(imageUrl);
            photo.setCaption(caption != null ? caption : "");
            photo.setStatus("pending");
            photoRepository.save(photo);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(photo);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

    // Get approved photos for a site
    @GetMapping("/{siteId}/approved")
    public List<Photo> getApprovedPhotos(
            @PathVariable Long siteId) {
        return photoRepository
                .findBySiteSiteIdAndStatus(siteId, "approved");
    }

    // Get ALL photos for a site (for demo — shows pending too)
    @GetMapping("/{siteId}/all")
    public List<Photo> getAllPhotos(
            @PathVariable Long siteId) {
        return photoRepository.findBySiteSiteId(siteId);
    }
}