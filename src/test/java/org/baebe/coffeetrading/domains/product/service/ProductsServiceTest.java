package org.baebe.coffeetrading.domains.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;
import org.baebe.coffeetrading.domains.product.repository.ProductsRepository;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ProductsServiceTest {

    @Autowired
    private ProductsRepository productsRepository;

    private ProductsService productsService;

    private StoresEntity store;
    private ProductsEntity product;

    @BeforeEach
    void setUp() {
        productsService = new ProductsService(productsRepository);

        store = StoresEntity.builder()
            .title("테스트 매장")
            .address("서울시 강남구")
            .roadNameAddress("서울시 강남구 테헤란로")
            .telephone("02-1234-5678")
            .description("테스트 매장입니다.")
            .status(StoreStatus.valueOf("OPEN"))
            .storeType(StoreTypes.valueOf("PRIVATE_STORE"))
            .build();

        product = ProductsEntity.builder()
            .store(store)
            .name("아메리카노")
            .description("기본 아메리카노")
            .price(4000)
            .status(ProductStatus.valueOf("FOR_SALE"))
            .build();
    }

    @Test
    @DisplayName("가게의 전체 상품 조회 - 성공")
    void getProductByStoreId_Success() {
        // given
        productsRepository.save(product);

        // when
        List<ProductsEntity> products = productsService.getProductByStoreId(store.getId());

        // then
        assertThat(products).hasSize(1);
        assertThat(products.getFirst().getName()).isEqualTo("아메리카노");
    }
} 