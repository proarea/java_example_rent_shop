package com.snowboard_rental_crm.gateway.service.equipment;

import com.snowboard_rental_crm.core_client.SnowboardClient;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardRequest;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardResponse;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardSearchRequest;
import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SnowboardService {

    private final SnowboardClient snowboardClient;
    private final ResponseUnwrapper responseUnwrapper;

    public SnowboardResponse addSnowboard(
            Long userId,
            SnowboardRequest request,
            MultipartFile photo,
            UserType userType
    ) {
        request.setAuditFields(userId, userType);

        return responseUnwrapper.unwrapOrThrowException(snowboardClient.addSnowboard(request, photo));
    }

    public void deleteSnowboard(Long snowboardId, Long userId) {
        responseUnwrapper.unwrapOrThrowException(snowboardClient.deleteSnowboard(snowboardId, userId));
    }

    public List<SnowboardResponse> getSnowboards(SnowboardSearchRequest request) {
        return Arrays.asList(responseUnwrapper.unwrapOrThrowException(snowboardClient.getSnowboards(request)));
    }
}
