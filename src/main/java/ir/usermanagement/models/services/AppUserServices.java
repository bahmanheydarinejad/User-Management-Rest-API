package ir.usermanagement.models.services;

import ir.usermanagement.exceptions.AppException;
import ir.usermanagement.mappers.AppMapper;
import ir.usermanagement.models.repositories.AppUserRepository;
import ir.usermanagement.models.repositories.entities.AppUser;
import ir.usermanagement.models.services.dtoes.AppUserDTO;
import ir.usermanagement.models.services.dtoes.ChangeAppUserPasswordRequestDTO;
import ir.usermanagement.models.services.dtoes.CreateAppUserRequestDTO;
import ir.usermanagement.models.services.dtoes.UpdateAppUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserServices {

    private final AppUserRepository appUserRepository;

    private final AppMapper appMapper;

    public AppUserDTO create(CreateAppUserRequestDTO dto) {

        if (!StringUtils.hasText(dto.getUsername())) {
            throw new AppException().addException(400006);
        }

        if (appUserRepository.existsByUsernameEqualsIgnoreCase(dto.getUsername())) {
            throw new AppException().addException(400001);
        }

        return appMapper.toAppUserDTO(appUserRepository.save(appMapper.toCreateAppUser(dto, new BCryptPasswordEncoder())));
    }

    public AppUserDTO read(String username) {
        return appMapper.toAppUserDTO(appUserRepository.findFirstByUsernameEqualsIgnoreCase(username).orElseThrow(() -> new AppException().addException(400002)));
    }

    @Transactional
    public AppUserDTO update(UpdateAppUserRequestDTO dto) {
        return appMapper.toAppUserDTO(appUserRepository.save(appMapper.toUpdateUser(appUserRepository.findFirstByUsernameEqualsIgnoreCase(dto.getUsername()).orElseThrow(() -> new AppException().addException(400002)), dto)));
    }

    @Transactional
    public void deleteById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new AppException().addException(400002));
        appUserRepository.delete(appUser);
    }

    @Transactional
    public void deleteByUserName(String username) {
        AppUser appUser = appUserRepository.findFirstByUsernameEqualsIgnoreCase(username).orElseThrow(() -> new AppException().addException(400002));
        appUserRepository.delete(appUser);
    }

    public Page<AppUserDTO> list(Pageable pageable) {
        return appUserRepository.findAll(pageable).map(appUser -> appMapper.toAppUserDTO(appUser));
    }

    @Transactional
    public void changePassword(ChangeAppUserPasswordRequestDTO dto) {
        AppException apiException = new AppException();

        if (!StringUtils.hasText(dto.getOldPassword())) {
            apiException.addException(400007);
        }

        if (!StringUtils.hasText(dto.getNewPassword())) {
            apiException.addException(400008);
        }

        if (!StringUtils.hasText(dto.getConfirmPassword())) {
            apiException.addException(400009);
        }

        AppUser appUser = appUserRepository.findFirstByUsernameEqualsIgnoreCase(dto.getUsername()).orElseThrow(() -> apiException.addException(400002));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean matchOldWithPersist = passwordEncoder.matches(dto.getOldPassword(), appUser.getPassword());
        if (!matchOldWithPersist) {
            // old password is not match
            apiException.addException(400003);
        }

        if (!dto.getNewPassword().contentEquals(dto.getConfirmPassword())) {
            // confirm password is not match
            apiException.addException(400004);
        }

        boolean matchNewWithPersist = passwordEncoder.matches(dto.getNewPassword(), appUser.getPassword());
        if (matchOldWithPersist && matchNewWithPersist) {
            // old and new password should not be same
            apiException.addException(400005);
        }

        if (!CollectionUtils.isEmpty(apiException.getExceptionsDetails()))
            throw apiException;

        appUserRepository.save(appMapper.toChangeUserPassword(appUser, dto, passwordEncoder));
    }

}
