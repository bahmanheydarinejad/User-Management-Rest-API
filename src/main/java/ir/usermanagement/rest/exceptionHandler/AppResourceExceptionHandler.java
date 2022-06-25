package ir.usermanagement.rest.exceptionHandler;

import ir.usermanagement.exceptions.AppException;
import ir.usermanagement.mappers.AppMapper;
import ir.usermanagement.models.repositories.AppMessageRepository;
import ir.usermanagement.models.repositories.entities.AppMessage;
import ir.usermanagement.rest.models.AppBaseResponse;
import ir.usermanagement.rest.models.AppMessageResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class AppResourceExceptionHandler extends ResponseEntityExceptionHandler {

    private final AppMessageRepository appMessageRepository;

    private final AppMapper appMapper;

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<AppBaseResponse> handleApiException(AppException e, WebRequest request) {
        String acceptLanguage = !StringUtils.hasText(request.getHeader("Accept-Language")) ? "en-en" : request.getHeader("Accept-Language");

        Map<Integer, AppMessage> appMessages = appMessageRepository.findAll().stream().collect(Collectors.toMap(AppMessage::getCode, Function.identity()));

        List<AppMessageResponseObject> messages = e.getExceptionsDetails().stream().map(detail -> {
            AppMessage appMessage = appMessages.getOrDefault(detail.getCode(), new AppMessage(500001, 500, Collections.EMPTY_LIST));
            String description = appMessage.getDescriptionByLanguage(acceptLanguage);
            return appMapper.toAppMessageResponseObject(appMessage, description, detail.getDetail());
        }).collect(Collectors.toList());

        Integer httpStatusCode = appMessages.values().stream().map(detail -> detail.getHttpStatusCode()).max((e1, e2) -> e1 >= e2 ? 1 : -1).orElse(500);

        return ResponseEntity.status(httpStatusCode).body(appMapper.toBadRequestResponse(messages, ""));
    }

}
