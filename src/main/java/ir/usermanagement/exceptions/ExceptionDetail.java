package ir.usermanagement.exceptions;

import lombok.Data;

@Data
public class ExceptionDetail {

    private final Integer code;

    private final String detail;

}
