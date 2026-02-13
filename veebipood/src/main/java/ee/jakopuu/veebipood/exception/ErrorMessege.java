package ee.jakopuu.veebipood.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessege {
    private String messega;
    private Date timestamp;
    private int status;
}
