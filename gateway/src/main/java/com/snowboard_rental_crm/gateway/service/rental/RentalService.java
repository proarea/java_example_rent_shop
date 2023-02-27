package com.snowboard_rental_crm.gateway.service.rental;

import com.snowboard_rental_crm.core_client.RentalClient;
import com.snowboard_rental_crm.core_data.model.rental.RentalRequest;
import com.snowboard_rental_crm.core_data.model.rental.RentalResponse;
import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final ResponseUnwrapper responseUnwrapper;
    private final RentalClient rentalClient;

    public RentalResponse createRental(RentalRequest request, Long userId, UserType admin) {
        request.setAuditFields(userId, admin);

        return responseUnwrapper.unwrapOrThrowException(rentalClient.createRental(request));
    }

    public void endRental(Long userId, Long rentalId) {
        responseUnwrapper.unwrapOrThrowException(rentalClient.endRental(userId, rentalId));
    }
}
