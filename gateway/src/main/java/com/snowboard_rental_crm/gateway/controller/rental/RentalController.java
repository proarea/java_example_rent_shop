package com.snowboard_rental_crm.gateway.controller.rental;

import com.snowboard_rental_crm.auth_data.constant.AuthConstants;
import com.snowboard_rental_crm.core_data.model.rental.RentalRequest;
import com.snowboard_rental_crm.core_data.model.rental.RentalResponse;
import com.snowboard_rental_crm.gateway.service.rental.RentalService;
import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Rental API")
@RestController
@RequestMapping("v1/rental")
@RequiredArgsConstructor
@SecurityRequirement(name = "snowboard-rental-crm")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    @Operation(description = "Create rental")
    public RentalResponse createRental(
            @Parameter(hidden = true) @RequestAttribute(AuthConstants.USER_ID_ATTRIBUTE) Long userId,
            @RequestBody @Valid RentalRequest request
    ) {
        return rentalService.createRental(request, userId, UserType.ADMIN);
    }

    @PostMapping(value = "/{rentalId}")
    @Operation(description = "End rental")
    public ResponseEntity<Void> endRental(
            @Parameter(hidden = true) @RequestAttribute(AuthConstants.USER_ID_ATTRIBUTE) Long userId,
            @PathVariable Long rentalId
    ) {
        rentalService.endRental(userId, rentalId);
        return ResponseEntity.noContent().build();
    }
}

