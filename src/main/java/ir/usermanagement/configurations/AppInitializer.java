package ir.usermanagement.configurations;

import ir.usermanagement.models.entities.AppMessage;
import ir.usermanagement.models.entities.AppMessageDescription;
import ir.usermanagement.models.repositories.AppMessageDescriptionRepository;
import ir.usermanagement.models.repositories.AppMessageRepository;
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

        AppMessage m1 = appMessageRepository.save(new AppMessage(400001, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(m1, "en-en", "The username already exist"));
        appMessageDescriptionRepository.save(new AppMessageDescription(m1, "fa-ir", "این نام کاربری موجود میباشد"));
        AppMessage m2 = appMessageRepository.save(new AppMessage(400002, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(m2, "en-en", "User not found"));
        appMessageDescriptionRepository.save(new AppMessageDescription(m2, "fa-ir", "کاربر یافت نشد"));
        AppMessage m3 = appMessageRepository.save(new AppMessage(400003, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(m3, "en-en", "Old password is not match"));
        appMessageDescriptionRepository.save(new AppMessageDescription(m3, "fa-ir", "رمز عبور قدیمی مطابقت ندارد"));
        AppMessage m4 = appMessageRepository.save(new AppMessage(400004, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(m4, "en-en", "Confirm password in not match"));
        appMessageDescriptionRepository.save(new AppMessageDescription(m4, "fa-ir", "رمز عبور و تکرار آن مطابقت ندارند"));
        AppMessage m5 = appMessageRepository.save(new AppMessage(400005, 400, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(m5, "en-en", "Old and new password are same"));
        appMessageDescriptionRepository.save(new AppMessageDescription(m5, "fa-ir", "رمز عبور قدیمی و جدید یکسان هستند"));
        AppMessage m6 = appMessageRepository.save(new AppMessage(500001, 500, null));
        appMessageDescriptionRepository.save(new AppMessageDescription(m6, "en-en", "Contact support please"));
        appMessageDescriptionRepository.save(new AppMessageDescription(m6, "fa-ir", "با پشتیبانی تماس بگیرید"));
    }
}
