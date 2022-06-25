package ir.usermanagement.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ApiException extends AppException {

    private List<ApiExceptionDetail> exceptionsDetails;

    public ApiException() {
        exceptionsDetails = new ArrayList<>();
    }

    public ApiException addException(Integer code) {
        return this.addException(code, null);
    }

    public ApiException addException(Integer code, String messageDetail) {
        if (Objects.isNull(exceptionsDetails))
            exceptionsDetails = new ArrayList<>();
        exceptionsDetails.add(new ApiExceptionDetail(code, messageDetail));
        return this;
    }
}
