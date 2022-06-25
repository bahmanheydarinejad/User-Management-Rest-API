package ir.usermanagement.configurations;

import ir.usermanagement.models.repositories.AppMessageDescriptionRepository;
import ir.usermanagement.models.repositories.AppMessageRepository;
import ir.usermanagement.models.repositories.entities.AppMessage;
import ir.usermanagement.models.repositories.entities.AppMessageDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppInitializer implements InitializingBean {

    private final Environment environment;
    private final AppMessageRepository appMessageRepository;
    private final AppMessageDescriptionRepository appMessageDescriptionRepository;

    @Override
    public void afterPropertiesSet() {
        log.info("AppInitializer::afterPropertiesSet");
        if (environment.getProperty("spring.jpa.hibernate.ddl-auto", String.class, "").equalsIgnoreCase("create")) {
            init();
        }
    }

    private void init() {
        log.info("AppInitializer::init");

        AppMessage b1 = appMessageRepository.save(new AppMessage(400001, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b1, "en-en", "The username already exist"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b1, "fa-ir", "این نام کاربری موجود میباشد"));

        AppMessage b2 = appMessageRepository.save(new AppMessage(400002, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b2, "en-en", "User not found"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b2, "fa-ir", "کاربر یافت نشد"));

        AppMessage b3 = appMessageRepository.save(new AppMessage(400003, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b3, "en-en", "Old password is not match"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b3, "fa-ir", "رمز عبور قبلی مطابقت ندارد"));

        AppMessage b4 = appMessageRepository.save(new AppMessage(400004, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b4, "en-en", "Confirm password in not match"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b4, "fa-ir", "رمز عبور و تکرار آن مطابقت ندارند"));

        AppMessage b5 = appMessageRepository.save(new AppMessage(400005, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b5, "en-en", "Old and new password are same"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b5, "fa-ir", "رمز عبور قبلی و جدید یکسان هستند"));

        AppMessage b6 = appMessageRepository.save(new AppMessage(400006, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b6, "en-en", "Username required"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b6, "fa-ir", "نام کاربری الزامی می باشد"));

        AppMessage b7 = appMessageRepository.save(new AppMessage(400007, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b7, "en-en", "Old password is required"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b7, "fa-ir", "رمز عبور قبلی لازم الزامی می باشد"));

        AppMessage b8 = appMessageRepository.save(new AppMessage(400008, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b8, "en-en", "New password is required"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b8, "fa-ir", "رمز عبور جدید الزامی می باشد"));

        AppMessage b9 = appMessageRepository.save(new AppMessage(400009, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(b9, "en-en", "Confirm password is required"));
        appMessageDescriptionRepository.save(new AppMessageDescription(b9, "fa-ir", "تکرار رمز عبور جدید الزامی می باشد"));

        AppMessage s1 = appMessageRepository.save(new AppMessage(500001, 500, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(s1, "en-en", "Contact support please"));
        appMessageDescriptionRepository.save(new AppMessageDescription(s1, "fa-ir", "با پشتیبانی تماس بگیرید"));
    }
}
