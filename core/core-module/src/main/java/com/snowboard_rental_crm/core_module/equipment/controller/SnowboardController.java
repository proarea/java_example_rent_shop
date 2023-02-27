package com.snowboard_rental_crm.core_module.equipment.controller;

import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardRequest;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardResponse;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardSearchRequest;
import com.snowboard_rental_crm.core_module.equipment.service.SnowboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/equipments/snowboards")
@RequiredArgsConstructor
public class SnowboardController {

    private final SnowboardService snowboardService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SnowboardResponse addSnowboard(
            @RequestPart SnowboardRequest request,
            @RequestPart(required = false) MultipartFile photo
    ) {
        return snowboardService.addSnowboard(request, photo);
    }

    @DeleteMapping(value = "/{snowboardId}/{userId}")
    public void deleteSnowboard(
            @PathVariable Long snowboardId,
            @PathVariable Long userId
    ) {
        snowboardService.deleteSnowboard(snowboardId, userId);
    }

    @PostMapping(value = "/search")
    public List<SnowboardResponse> getSnowboards(@RequestBody SnowboardSearchRequest request) {
        return snowboardService.getSnowboards(request);
    }

}
