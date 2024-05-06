package app.helipay.se.service;

import app.helipay.se.domain.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.ToDoubleFunction;

@Slf4j
@Service
public class DiscountService implements ToDoubleFunction<List<ProductEntity>> {

    @Override
    public double applyAsDouble(List<ProductEntity> productEntities) {
        return 0;
    }
}
