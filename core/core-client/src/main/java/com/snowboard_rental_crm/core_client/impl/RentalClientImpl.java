package com.snowboard_rental_crm.core_client.impl;

import com.snowboard_rental_crm.core_client.RentalClient;
import com.snowboard_rental_crm.core_data.constant.CoreConstants;
import com.snowboard_rental_crm.core_data.model.rental.RentalRequest;
import com.snowboard_rental_crm.core_data.model.rental.RentalResponse;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import com.snowboard_rental_crm.shared_module.service.WebClientService;
import com.snowboard_rental_crm.shared_module.util.UriComponentsBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalClientImpl implements RentalClient {

    private final WebClientService webClientService;

    @Value("${core.url}")
    private String baseUrl;

    @Override
    public ResponseResult<RentalResponse> createRental(RentalRequest request) {
        return webClientService.postRequest(
                UriComponentsBuilderUtil.buildUrl(baseUrl, CoreConstants.RENTAL_PATH),
                request,
                RentalResponse.class
        );
    }

    @Override
    public ResponseResult<Void> endRental(Long userId, Long rentalId) {
        return webClientService.postRequest(
                UriComponentsBuilderUtil.buildUrl(baseUrl, CoreConstants.RENTAL_ID_USER_ID_PATH, rentalId, userId),
                Void.class
        );
    }
}
