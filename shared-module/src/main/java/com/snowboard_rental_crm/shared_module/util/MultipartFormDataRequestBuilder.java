package com.snowboard_rental_crm.shared_module.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Objects;

import static com.snowboard_rental_crm.shared_data.constant.SharedConstants.PHOTO_PARAM;
import static com.snowboard_rental_crm.shared_data.constant.SharedConstants.REQUEST_BODY_PARAM;


@UtilityClass
public class MultipartFormDataRequestBuilder {

    public static BodyInserters.MultipartInserter buildMultipartFormDataRequest(Object request, MultipartFile photo) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        if (Objects.nonNull(photo)) {
            builder.part(PHOTO_PARAM, photo.getResource());
        }
        builder.part(REQUEST_BODY_PARAM, request);
        return BodyInserters.fromMultipartData(builder.build());
    }
}
