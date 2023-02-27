package com.snowboard_rental_crm.core_module.equipment.service;

import com.snowboard_rental_crm.core_data.enumiration.PhotoType;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardRequest;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardResponse;
import com.snowboard_rental_crm.core_data.model.equipment.snowboard.SnowboardSearchRequest;
import com.snowboard_rental_crm.core_module.equipment.entity.SnowboardEntity;
import com.snowboard_rental_crm.core_module.equipment.repository.SnowboardRepository;
import com.snowboard_rental_crm.core_module.media.service.MediaService;
import com.snowboard_rental_crm.core_module.rental.repository.RentalRepository;
import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import com.snowboard_rental_crm.shared_data.exception.BadRequestException;
import com.snowboard_rental_crm.shared_data.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.snowboard_rental_crm.core_data.constant.CoreConstants.SNOWBOARD;
import static com.snowboard_rental_crm.shared_data.constant.SharedExceptionConstants.ALREADY_EXIST_EXCEPTION_MESSAGE;
import static com.snowboard_rental_crm.shared_data.constant.SharedExceptionConstants.DELETE_EXCEPTION_MESSAGE;
import static com.snowboard_rental_crm.shared_data.constant.SharedExceptionConstants.NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class SnowboardService {

    private final SnowboardRepository snowboardRepository;
    private final RentalRepository rentalRepository;
    private final MediaService mediaService;
    private final ModelMapper modelMapper;

    public SnowboardResponse addSnowboard(SnowboardRequest request, MultipartFile photo) {
        validateSnowboardRequest(request);

        SnowboardEntity snowboard = snowboardRepository.save(buildSnowboardEntity(request));

        String url = mediaService.uploadPhoto(photo, PhotoType.SNOWBOARD, snowboard.getId());
        snowboard.setPhotoUrl(url);

        return modelMapper.map(snowboard, SnowboardResponse.class);

    }

    @Transactional
    public void deleteSnowboard(Long snowboardId, Long userId) {
        validateRentalSnowboard(snowboardId);

        SnowboardEntity snowboardEntity = snowboardRepository.findById(snowboardId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, snowboardId)));

        snowboardEntity
                .setDeleted(true)
                .setUpdatedBy(userId)
                .setUpdatedUserType(UserType.ADMIN);
    }

    public List<SnowboardResponse> getSnowboards(SnowboardSearchRequest request) {
        List<SnowboardEntity> snowboardEntities =
                snowboardRepository.findAllByFilter(
                        request.getName(),
                        request.getEquipmentClass(),
                        request.getSnowboardType(),
                        request.getSnowboardStyle()
                );
        return snowboardEntities.stream()
                .map(snowboard -> modelMapper.map(snowboard, SnowboardResponse.class)).toList();

    }

    private SnowboardEntity buildSnowboardEntity(SnowboardRequest request) {
        return SnowboardEntity.builder()
                .name(request.getName())
                .snowboardStyle(request.getSnowboardStyle())
                .collateralValue(request.getCollateralValue())
                .size(request.getSize())
                .snowboardType(request.getSnowboardType())
                .equipmentClass(request.getEquipmentClass())
                .createdBy(request.getUserId())
                .createdUserType(UserType.ADMIN)
                .updatedUserType(UserType.ADMIN)
                .updatedBy(request.getUserId())
                .build();
    }

    private void validateSnowboardRequest(SnowboardRequest request) {
        boolean isSnowboardExist = snowboardRepository.existsSnowboardEntitiesByNameIgnoreCase(request.getName());

        if (isSnowboardExist) {
            throw new BadRequestException(
                    String.format(ALREADY_EXIST_EXCEPTION_MESSAGE,
                            SNOWBOARD,
                            request.getName()
                    )
            );
        }
    }

    private void validateRentalSnowboard(Long snowboardId) {
        boolean isActiveRental = rentalRepository.existsBySnowboardIdAndActiveIsTrue(snowboardId);

        if (isActiveRental) {
            throw new BadRequestException(String.format(DELETE_EXCEPTION_MESSAGE, snowboardId));
        }
    }
}
