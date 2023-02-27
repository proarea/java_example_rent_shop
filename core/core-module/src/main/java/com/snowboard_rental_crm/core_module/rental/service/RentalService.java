package com.snowboard_rental_crm.core_module.rental.service;

import com.snowboard_rental_crm.core_data.model.rental.RentalRequest;
import com.snowboard_rental_crm.core_data.model.rental.RentalResponse;
import com.snowboard_rental_crm.core_module.equipment.repository.MaskRepository;
import com.snowboard_rental_crm.core_module.equipment.repository.SnowboardRepository;
import com.snowboard_rental_crm.core_module.rental.entity.RentalEntity;
import com.snowboard_rental_crm.core_module.rental.repository.RentalRepository;
import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import com.snowboard_rental_crm.shared_data.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.snowboard_rental_crm.core_data.constant.CoreConstants.RENTAL;
import static com.snowboard_rental_crm.shared_data.constant.SharedExceptionConstants.NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final SnowboardRepository snowboardRepository;
    private final MaskRepository maskRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public RentalResponse createRental(RentalRequest request) {
        RentalEntity rental = rentalRepository.save(buildRentalEntity(request));

        snowboardRepository.findById(request.getSnowboardId())
                .ifPresent(maskEntity -> maskEntity.setRental(true));

        maskRepository.findById(request.getSnowboardId())
                .ifPresent(snowboardEntity -> snowboardEntity.setRental(true));

        return modelMapper.map(rental, RentalResponse.class);
    }

    @Transactional
    public void endRental(Long rentalId, Long userId) {
        RentalEntity rental = getRentalById(rentalId).setActive(false);

        snowboardRepository.findById(rental.getSnowboardId())
                .ifPresent(snowboard ->
                        snowboard.setRental(false).setUpdatedBy(userId).setUpdatedUserType(UserType.ADMIN));

        maskRepository.findById(rental.getMaskId())
                .ifPresent(mask -> mask.setRental(false).setUpdatedBy(userId).setUpdatedUserType(UserType.ADMIN));
    }

    private RentalEntity getRentalById(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(
                        () -> new BadRequestException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, RENTAL))
                );
    }

    private RentalEntity buildRentalEntity(RentalRequest request) {
        return RentalEntity.builder()
                .snowboardId(request.getSnowboardId())
                .maskId(request.getMaskId())
                .documentInfo(request.getDocumentInfo())
                .collateralValue(countCollateralValue(request))
                .active(true)
                .createdBy(request.getUserId())
                .updatedBy(request.getUserId())
                .createdUserType(request.getUserType())
                .updatedUserType(request.getUserType())
                .build();
    }

    private BigDecimal countCollateralValue(RentalRequest request) {
        BigDecimal collateralValueMask = maskRepository.findMaskEntityById(request.getMaskId())
                .getCollateralValue();
        BigDecimal collateralValueSnowboard = snowboardRepository.findSnowboardEntityById(request.getMaskId())
                .getCollateralValue();

        return collateralValueMask.add(collateralValueSnowboard);
    }

}
