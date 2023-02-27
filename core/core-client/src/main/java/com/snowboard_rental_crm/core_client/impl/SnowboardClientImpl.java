package com.snowboard_rental_crm.core_client.impl;

import com.snowboard_rental_crm.core_client.SnowboardClient;
import com.snowboard_rental_crm.core_data.constant.CoreConstants;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardRequest;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardResponse;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardSearchRequest;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import com.snowboard_rental_crm.shared_module.service.WebClientService;
import com.snowboard_rental_crm.shared_module.util.UriComponentsBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static com.snowboard_rental_crm.shared_module.util.MultipartFormDataRequestBuilder.buildMultipartFormDataRequest;

@Component
@RequiredArgsConstructor
public class SnowboardClientImpl implements SnowboardClient {

    private final WebClientService webClientService;

    @Value("${core.url}")
    private String baseUrl;

    @Override
    public ResponseResult<SnowboardResponse> addSnowboard(SnowboardRequest request, MultipartFile photo) {
        return webClientService.postRequest(
                UriComponentsBuilderUtil.buildUrl(baseUrl, CoreConstants.SNOWBOARD_PATH),
                buildMultipartFormDataRequest(request, photo),
                SnowboardResponse.class,
                MediaType.MULTIPART_FORM_DATA
        );
    }

    @Override
    public ResponseResult<ResponseEntity<Void>> deleteSnowboard(Long snowboardId, Long userId) {
        return webClientService.deleteRequest(
                UriComponentsBuilderUtil.buildUrl(
                        baseUrl,
                        CoreConstants.SNOWBOARD_ID_USER_ID_PATH,
                        snowboardId,
                        userId
                )
        );
    }

    @Override
    public ResponseResult<SnowboardResponse[]> getSnowboards(SnowboardSearchRequest request) {
        return webClientService.postRequest(
                UriComponentsBuilderUtil.buildUrl(baseUrl, CoreConstants.SNOWBOARD_SEARCH_PATH),
                request,
                SnowboardResponse[].class
        );
    }
}
