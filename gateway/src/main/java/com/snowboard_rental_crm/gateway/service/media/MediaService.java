package com.snowboard_rental_crm.gateway.service.media;

import com.snowboard_rental_crm.media_client.MediaClient;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaClient mediaClient;
    private final ResponseUnwrapper responseUnwrapper;

    @SneakyThrows
    public InputStream getFile(String url) {
        return responseUnwrapper.unwrapOrThrowException(mediaClient.getFile(url)).getInputStream();
    }
}