package com.snowboard_rental_crm.media_client.impl;

import com.snowboard_rental_crm.media_client.MediaClient;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import com.snowboard_rental_crm.shared_module.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

import static com.restaurant_crm.media_data.constant.MediaConstants.MEDIA_FILE_PARAM;
import static com.restaurant_crm.media_data.constant.MediaConstants.MEDIA_URL_PARAM;
import static com.snowboard_rental_crm.shared_module.util.RequestParamBuilderUtil.buildRequestParam;
import static com.snowboard_rental_crm.shared_module.util.UriComponentsBuilderUtil.buildUrl;


@Component
@RequiredArgsConstructor
public class MediaClientImpl implements MediaClient {

    private final WebClientService webClientService;

    @Value("${media.url}")
    private String baseUrl;

    @Override
    public ResponseResult<ByteArrayResource> getFile(String url) {
        return webClientService.getRequest(
                buildUrl(baseUrl, buildRequestParam(MEDIA_URL_PARAM, url)),
                ByteArrayResource.class,
                MediaType.APPLICATION_OCTET_STREAM
        );
    }

    @Override
    public ResponseResult<ResponseEntity> uploadFile(MultipartFile file, String url) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part(MEDIA_FILE_PARAM, file.getResource());
        builder.part(MEDIA_URL_PARAM, url);

        return webClientService.putRequest(
                baseUrl,
                BodyInserters.fromMultipartData(builder.build()),
                ResponseEntity.class,
                MediaType.MULTIPART_FORM_DATA
        );
    }

    @Override
    public ResponseResult<ResponseEntity<Void>> deleteFile(String url) {
        return webClientService.deleteRequest(buildUrl(baseUrl, buildRequestParam(MEDIA_URL_PARAM, url)));
    }
}
