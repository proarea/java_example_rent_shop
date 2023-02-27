package com.snowboard_rental_crm.core_client;

import com.snowboard_rental_crm.core_data.model.rental.RentalRequest;
import com.snowboard_rental_crm.core_data.model.rental.RentalResponse;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;

public interface RentalClient {
    ResponseResult<RentalResponse> createRental(RentalRequest request);

    ResponseResult<Void> endRental(Long userId, Long rentalId);
}
