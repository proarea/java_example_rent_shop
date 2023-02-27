package com.snowboard_rental_crm.core_client.impl;

import com.snowboard_rental_crm.core_client.MaskClient;
import com.snowboard_rental_crm.core_data.constant.CoreConstants;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskRequest;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskResponse;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskSearchRequest;
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
public class MaskClientImpl implements MaskClient {

    private final WebClientService webClientService;

    @Value("${core.url}")
    private String baseUrl;

    @Override
    public ResponseResult<MaskResponse> addMask(MaskRequest request, MultipartFile photo) {
        return webClientService.postRequest(
                UriComponentsBuilderUtil.buildUrl(baseUrl, CoreConstants.MASK_PATH),
                buildMultipartFormDataRequest(request, photo),
                MaskResponse.class,
                MediaType.MULTIPART_FORM_DATA
        );
    }

    @Override
    public ResponseResult<ResponseEntity<Void>> deleteMask(Long userId, Long maskId) {
        return webClientService.deleteRequest(
                UriComponentsBuilderUtil.buildUrl(
                        baseUrl,
                        CoreConstants.MASK_ID_USER_ID_PATH,
                        maskId,
                        userId
                )
        );
    }

    @Override
    public ResponseResult<MaskResponse[]> getMasks(MaskSearchRequest request) {
        return webClientService.postRequest(
                UriComponentsBuilderUtil.buildUrl(baseUrl, CoreConstants.MASK_SEARCH_PATH),
                request,
                MaskResponse[].class
        );
    }
}
