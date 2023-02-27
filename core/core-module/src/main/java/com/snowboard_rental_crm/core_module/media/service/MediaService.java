package com.snowboard_rental_crm.core_module.media.service;

import com.snowboard_rental_crm.core_data.enumiration.PhotoType;
import com.snowboard_rental_crm.media_client.MediaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.snowboard_rental_crm.shared_module.util.MediaUrlGeneratorUtil.generateMediaUrl;


@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaClient mediaClient;

    public String uploadPhoto(MultipartFile photo, PhotoType photoType, Long id) {
        return uploadPhoto(photo, photoType, id, null);
    }

    public String uploadPhoto(MultipartFile photo, PhotoType photoType, Long id, String existingUrl) {
        if (Objects.nonNull(photo)) {
            String photoUrl = generateMediaUrl(photoType.name(), id);
            mediaClient.uploadFile(photo, photoUrl);
            return photoUrl;
        } else if (Objects.nonNull(existingUrl)) {
            mediaClient.deleteFile(existingUrl);
        }
        return null;
    }
}
