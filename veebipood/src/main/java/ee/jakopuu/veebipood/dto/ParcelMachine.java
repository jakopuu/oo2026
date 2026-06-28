package ee.jakopuu.veebipood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParcelMachine {
    @JsonProperty("ZIP")
    private String zip;
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("TYPE")
    private String type;
    @JsonProperty("A0_NAME")
    private String a0_name;
    @JsonProperty("A1_NAME")
    private String a1_name;
    @JsonProperty("X_COORDINATE")
    private String x_coordinate;
    @JsonProperty("Y_COORDINATE")
    private String y_coordinate;
    @JsonProperty("SERVICE_HOURS")
    private String service_hours;
}
