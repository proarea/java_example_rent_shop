package com.snowboard_rental_crm.core_client;

import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardRequest;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardResponse;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardSearchRequest;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SnowboardClient {

    ResponseResult<SnowboardResponse> addSnowboard(SnowboardRequest categoryRequest, MultipartFile photo);

    ResponseResult<ResponseEntity<Void>> deleteSnowboard(Long snowboardId, Long userId);

    ResponseResult<SnowboardResponse[]> getSnowboards(SnowboardSearchRequest request);
}
