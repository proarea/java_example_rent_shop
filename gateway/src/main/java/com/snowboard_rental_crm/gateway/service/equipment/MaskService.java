package com.snowboard_rental_crm.gateway.service.equipment;

import com.snowboard_rental_crm.core_client.MaskClient;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskRequest;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskResponse;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskSearchRequest;
import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaskService {

    private final ResponseUnwrapper responseUnwrapper;
    private final MaskClient maskClient;

    public MaskResponse addMask(
            Long userId,
            MaskRequest request,
            MultipartFile photo,
            UserType userType
    ) {
        request.setAuditFields(userId, userType);

        return responseUnwrapper.unwrapOrThrowException(maskClient.addMask(request, photo));
    }

    public void deleteMask(Long userId, Long maskId) {
        responseUnwrapper.unwrapOrThrowException(maskClient.deleteMask(userId, maskId));
    }

    public List<MaskResponse> getMasks(MaskSearchRequest request) {
        return Arrays.asList(responseUnwrapper.unwrapOrThrowException(maskClient.getMasks(request)));
    }
}
