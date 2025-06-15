package org.baebe.coffeetrading.domains.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.baebe.coffeetrading.api.config.QuerydslConfig;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class ProductsRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JPAQueryFactory queryFactory;

    private ProductsRepositoryImpl productsRepositoryImpl;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(entityManager);
        productsRepositoryImpl = new ProductsRepositoryImpl(queryFactory);

        // 테스트 데이터 생성
        StoresEntity store = StoresEntity.builder()
            .title("테스트 매장")
            .address("서울시 강남구")
            .roadNameAddress("서울시 강남구 테헤란로")
            .telephone("02-1234-5678")
            .description("테스트 매장입니다.")
            .status(StoreStatus.valueOf("OPEN"))
            .storeType(StoreTypes.valueOf("PRIVATE_STORE"))
            .build();
        entityManager.persist(store);

        ProductsEntity product1 = ProductsEntity.builder()
            .store(store)
            .name("아메리카노")
            .description("기본 아메리카노")
            .price(4000)
            .status(ProductStatus.valueOf("FOR_SALE"))
            .build();
        entityManager.persist(product1);

        ProductsEntity product2 = ProductsEntity.builder()
            .store(store)
            .name("카페라떼")
            .description("기본 카페라떼")
            .price(4500)
            .status(ProductStatus.valueOf("FOR_SALE"))
            .build();
        entityManager.persist(product2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("가게의 전체 메뉴 가져오기 - 성공")
    void getAllProductsByStoreId_Success() {
        // given
        Long storeId = 1L;

        // when
        List<ProductsEntity> products = productsRepositoryImpl.getAllProductsByStoreId(storeId);

        // then
        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("아메리카노");
        assertThat(products.get(1).getName()).isEqualTo("카페라떼");
    }
} 