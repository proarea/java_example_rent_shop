package com.snowboard_rental_crm.gateway.controller.equipment;

import com.snowboard_rental_crm.auth_data.constant.AuthConstants;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardRequest;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardResponse;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardSearchRequest;
import com.snowboard_rental_crm.gateway.service.equipment.SnowboardService;
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

@Tag(name = "Snowboard API")
@RestController
@RequestMapping("v1/equipments/snowboards")
@RequiredArgsConstructor
@SecurityRequirement(name = "snowboard-rental-crm")
public class SnowboardController {

    private final SnowboardService snowboardService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(description = "Add snowboard")
    public SnowboardResponse addSnowboard(
            @Parameter(hidden = true) @RequestAttribute(AuthConstants.USER_ID_ATTRIBUTE) Long userId,
            @RequestPart @Valid SnowboardRequest request,
            @RequestPart(required = false) MultipartFile photo
    ) {
        return snowboardService.addSnowboard(userId, request, photo, UserType.ADMIN);
    }

    @DeleteMapping("/{snowboardId}")
    @Operation(description = "Delete snowboard")
    public ResponseEntity<Void> deleteSnowboard(
            @Parameter(hidden = true) @RequestAttribute(AuthConstants.USER_ID_ATTRIBUTE) Long userId,
            @PathVariable Long snowboardId
    ) {
        snowboardService.deleteSnowboard(snowboardId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    @Operation(description = "Get snowboard")
    public List<SnowboardResponse> getSnowboards(@RequestBody SnowboardSearchRequest request) {
        return snowboardService.getSnowboards(request);
    }
}
