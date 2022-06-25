package ir.usermanagement.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class AppException extends RuntimeException {

    private List<ExceptionDetail> exceptionsDetails;

    public AppException() {
        exceptionsDetails = new ArrayList<>();
    }

    public AppException addException(Integer code) {
        return this.addException(code, null);
    }

    public AppException addException(Integer code, String messageDetail) {
        if (Objects.isNull(exceptionsDetails))
            exceptionsDetails = new ArrayList<>();
        exceptionsDetails.add(new ExceptionDetail(code, messageDetail));
        return this;
    }
}
