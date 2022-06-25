package ir.usermanagement.exceptions;

import lombok.Data;

@Data
public class ApiExceptionDetail {

    private final Integer code;

    private final String detail;

}
