package org.baebe.coffeetrading.domains.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.baebe.coffeetrading.api.config.QuerydslConfig;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.common.BaseEntity;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductOptionsEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;
import org.baebe.coffeetrading.domains.product.repository.ProductOptionsRepository;
import org.baebe.coffeetrading.domains.product.repository.ProductsRepository;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.store.repository.StoresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
@Import({QuerydslConfig.class, ProductOptionsService.class})
class ProductOptionsServiceTest {
    @Autowired
    private ProductOptionsService productOptionsService;
    @Autowired
    private ProductOptionsRepository productOptionsRepository;
    @Autowired
    private ProductsRepository productsRepository;

    private ProductsEntity product;
    private ProductOptionsEntity productOption;

    @BeforeEach
    void setUp() {
        product = ProductsEntity.builder()
            .name("아메리카노")
            .description("기본 아메리카노")
            .price(4000)
            .status(ProductStatus.valueOf("FOR_SALE"))
            .build();

        productOption = ProductOptionsEntity.builder()
            .product(product)
            .name("샷 추가")
            .description("1샷 추가입니다.")
            .price(500)
            .status(ProductStatus.valueOf("FOR_SALE"))
            .build();
    }

    @Test
    @DisplayName("상품 옵션 벌크 삭제 - 성공")
    void deleteProductOptionsByProductId_Success() {
        // given
        ProductsEntity productsEntity = productsRepository.save(product);
        productOptionsRepository.save(productOption);

        // when
        productOptionsService.deleteProductOptionsByProductId(productsEntity.getId());

        // then
        assertThat(productOptionsRepository.findAll()).isEmpty();
    }
}