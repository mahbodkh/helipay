package app.helipay.um.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationValidator {
    private static final int MAX_PAGE_SIZE = 100;
    private static final int DEFAULT_PAGE_SIZE = 20;

    public static Pageable validatePaginationOrThrow(int page, int size) {
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0 || size > MAX_PAGE_SIZE) {
            size = DEFAULT_PAGE_SIZE;
            //      throw new BadRequestException("page size (" + size + ") must be between 1 and " + MAX_PAGE_SIZE);
        }
        return PageRequest.of(page - 1, size);
    }
}