package app.helipay.se.service.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

@Setter
@Getter
public class PaginationParams {
    @Min(value = 0, message = "page value must greater than or equal 0")
    Integer page = 0;
    @Min(value = 1, message = "size value must greater than 1")
    Integer size = 10;
}