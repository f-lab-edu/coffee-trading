package org.baebe.coffeetrading.domains.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import org.baebe.coffeetrading.api.config.QuerydslConfig;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.common.BaseEntity;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductImagesEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;
import org.baebe.coffeetrading.domains.product.repository.ProductImagesRepository;
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
@Import({QuerydslConfig.class, ProductImagesService.class})
class ProductImagesServiceTest {

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductImagesService productImagesService;

    @Autowired
    private StoresRepository storesRepository;

    private StoresEntity store;
    private ProductsEntity product;
    private ProductImagesEntity productImage;
    private static final String FILE_PATH = "/Users/bae-be/Public/Personal/Project/coffee-trading/images/";

    @BeforeEach
    void setUp() {
        productImagesService = new ProductImagesService(productImagesRepository);

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

        productImage = ProductImagesEntity.of(
            product,
            "americano.jpg",
            "encrypted_americano.jpg",
            FILE_PATH
        );
    }

    @Test
    @DisplayName("상품 이미지 조회 - 성공")
    void getProductImagesByProductId_Success() {
        // given
        storesRepository.save(store);
        ProductsEntity productsEntity = productsRepository.save(product);
        productImagesRepository.save(productImage);

        // when
        List<ProductImagesEntity> result = productImagesService.getProductImagesByProductId(productsEntity.getId());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getOriginalFileName()).isEqualTo("americano.jpg");
        assertThat(result.getFirst().getEncryptedFileName()).isEqualTo("encrypted_americano.jpg");
        assertThat(result.getFirst().getFilePath()).isEqualTo("/Users/bae-be/Public/Personal/Project/coffee-trading/images/");
    }
}