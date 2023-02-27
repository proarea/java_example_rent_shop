package com.snowboard_rental_crm.core_module.equipment.controller;

import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskRequest;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskResponse;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskSearchRequest;
import com.snowboard_rental_crm.core_module.equipment.service.MaskService;
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
@RequestMapping("/v1/equipments/masks")
@RequiredArgsConstructor
public class MaskController {
    private final MaskService maskService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MaskResponse addSnowboard(
            @RequestPart MaskRequest request,
            @RequestPart(required = false) MultipartFile photo
    ) {
        return maskService.addMask(request, photo);
    }

    @DeleteMapping(value = "/{maskId}/{userId}")
    public void deleteMask(
            @PathVariable Long maskId,
            @PathVariable Long userId
    ) {
        maskService.deleteMask(maskId, userId);
    }

    @PostMapping(value = "/search")
    public List<MaskResponse> getMasks(@RequestBody MaskSearchRequest request) {
        return maskService.getMasks(request);
    }

}
