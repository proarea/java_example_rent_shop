package com.snowboard_rental_crm.core_module.equipment.service;

import com.snowboard_rental_crm.core_data.enumiration.PhotoType;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskRequest;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskResponse;
import com.snowboard_rental_crm.core_data.model.equipment.mask.MaskSearchRequest;
import com.snowboard_rental_crm.core_module.equipment.entity.MaskEntity;
import com.snowboard_rental_crm.core_module.equipment.repository.MaskRepository;
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

import static com.snowboard_rental_crm.core_data.constant.CoreConstants.MASK;
import static com.snowboard_rental_crm.shared_data.constant.SharedExceptionConstants.ALREADY_EXIST_EXCEPTION_MESSAGE;
import static com.snowboard_rental_crm.shared_data.constant.SharedExceptionConstants.DELETE_EXCEPTION_MESSAGE;
import static com.snowboard_rental_crm.shared_data.constant.SharedExceptionConstants.NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class MaskService {

    private final MaskRepository maskRepository;
    private final RentalRepository rentalRepository;
    private final MediaService mediaService;
    private final ModelMapper modelMapper;

    public MaskResponse addMask(MaskRequest request, MultipartFile photo) {
        validateMaskRequest(request);

        MaskEntity mask = maskRepository.save(buildMaskEntity(request));

        String url = mediaService.uploadPhoto(photo, PhotoType.MASK, mask.getId());
        mask.setPhotoUrl(url);

        return modelMapper.map(mask, MaskResponse.class);
    }

    @Transactional
    public void deleteMask(Long maskId, Long userId) {
        validateRentalMask(maskId);

        MaskEntity maskEntity = maskRepository.findById(maskId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, maskId)));

        maskEntity.setDeleted(true)
                .setUpdatedBy(userId)
                .setUpdatedUserType(UserType.ADMIN);
    }

    public List<MaskResponse> getMasks(MaskSearchRequest request) {
        List<MaskEntity> maskEntities =
                maskRepository.findAllByFilter(
                        request.getName(),
                        request.getLensTint(),
                        request.getAntiFog()
                );

        return maskEntities.stream()
                .map(snowboard -> modelMapper.map(snowboard, MaskResponse.class)).toList();
    }

    private MaskEntity buildMaskEntity(MaskRequest request) {
        return MaskEntity.builder()
                .name(request.getName())
                .collateralValue(request.getCollateralValue())
                .antiFog(request.isAntiFog())
                .lensTint(request.getLensTint())
                .createdBy(request.getUserId())
                .createdUserType(request.getUserType())
                .updatedBy(request.getUserId())
                .updatedUserType(request.getUserType())
                .build();
    }

    private void validateMaskRequest(MaskRequest request) {
        boolean isMaskExist = maskRepository.existsMaskEntitiesByNameIgnoreCase(request.getName());

        if (isMaskExist) {
            throw new BadRequestException(
                    String.format(ALREADY_EXIST_EXCEPTION_MESSAGE,
                            MASK,
                            request.getName()
                    )
            );
        }
    }

    public void validateRentalMask(Long maskId) {
        boolean isActiveRental = rentalRepository.existsByMaskIdAndActiveIsTrue(maskId);

        if (isActiveRental) {
            throw new BadRequestException(String.format(DELETE_EXCEPTION_MESSAGE, maskId));
        }
    }
}
