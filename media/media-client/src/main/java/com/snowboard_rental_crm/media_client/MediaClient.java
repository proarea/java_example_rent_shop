package com.snowboard_rental_crm.media_client;

import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MediaClient {

    ResponseResult<ByteArrayResource> getFile(String url);

    ResponseResult<ResponseEntity> uploadFile(MultipartFile file, String url);

    ResponseResult<ResponseEntity<Void>> deleteFile(String url);
}
