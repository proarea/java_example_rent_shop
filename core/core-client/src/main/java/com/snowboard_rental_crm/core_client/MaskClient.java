package com.snowboard_rental_crm.core_client;

import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskRequest;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskResponse;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskSearchRequest;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MaskClient {
    ResponseResult<MaskResponse> addMask(MaskRequest request, MultipartFile photo);

    ResponseResult<ResponseEntity<Void>> deleteMask(Long userId, Long maskId);

    ResponseResult<MaskResponse[]> getMasks(MaskSearchRequest request);
}
