package com.snowboard_rental_crm.gateway.controller.equipment;

import com.snowboard_rental_crm.auth_data.constant.AuthConstants;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskRequest;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskResponse;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskSearchRequest;
import com.snowboard_rental_crm.gateway.service.equipment.MaskService;
import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Mask API")
@RestController
@RequestMapping("v1/equipments/masks")
@RequiredArgsConstructor
@SecurityRequirement(name = "snowboard-rental-crm")
public class MaskController {

    private final MaskService maskService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(description = "Add mask")
    public MaskResponse addMask(
            @Parameter(hidden = true) @RequestAttribute(AuthConstants.USER_ID_ATTRIBUTE) Long userId,
            @RequestPart @Valid MaskRequest request,
            @RequestPart(required = false) MultipartFile photo
    ) {
        return maskService.addMask(userId, request, photo, UserType.ADMIN);
    }

    @DeleteMapping(value = "/{maskId}")
    @Operation(description = "Mask snowboard")
    public ResponseEntity<Void> deleteMask(
            @Parameter(hidden = true) @RequestAttribute(AuthConstants.USER_ID_ATTRIBUTE) Long userId,
            @PathVariable Long maskId
    ) {
        maskService.deleteMask(userId, maskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    @Operation(description = "Get mask")
    public List<MaskResponse> getMasks(@RequestBody MaskSearchRequest request) {
        return maskService.getMasks(request);
    }

}
