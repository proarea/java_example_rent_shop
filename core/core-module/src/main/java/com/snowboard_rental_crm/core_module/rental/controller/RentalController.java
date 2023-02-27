package com.snowboard_rental_crm.core_module.rental.controller;

import com.snowboard_rental_crm.core_data.model.rental.RentalRequest;
import com.snowboard_rental_crm.core_data.model.rental.RentalResponse;
import com.snowboard_rental_crm.core_module.rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public RentalResponse createRental(@RequestBody RentalRequest request) {
        return rentalService.createRental(request);
    }

    @PostMapping("/{rentalId}/{userId}")
    public void endRental(
            @PathVariable Long rentalId,
            @PathVariable Long userId
    ) {
        rentalService.endRental(rentalId, userId);
    }
}
