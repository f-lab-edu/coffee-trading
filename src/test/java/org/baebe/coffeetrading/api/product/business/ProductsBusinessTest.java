package org.baebe.coffeetrading.api.product.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.baebe.coffeetrading.api.product.dto.request.AddProductRequest;
import org.baebe.coffeetrading.api.product.dto.request.DeleteProductsRequest;
import org.baebe.coffeetrading.api.product.dto.request.ModifyProductRequest;
import org.baebe.coffeetrading.api.product.dto.request.AddProductImageRequest;
import org.baebe.coffeetrading.api.product.dto.request.AddProductOptionRequest;
import org.baebe.coffeetrading.api.product.dto.request.DeleteProductImageRequest;
import org.baebe.coffeetrading.api.product.dto.request.ModifyProductOptionRequest;
import org.baebe.coffeetrading.api.product.dto.response.ProductOptionResponse;
import org.baebe.coffeetrading.api.product.dto.response.ProductResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.common.BaseEntity;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductImagesEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductOptionsEntity;
import org.baebe.coffeetrading.domains.product.service.ProductImagesService;
import org.baebe.coffeetrading.domains.product.service.ProductOptionsService;
import org.baebe.coffeetrading.domains.product.service.ProductsService;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.store.service.StoresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductsBusinessTest {

    @InjectMocks
    private ProductBusiness productsBusiness;

    @Mock
    private ProductsService productsService;
    @Mock
    private ProductImagesService productImagesService;
    @Mock
    private ProductOptionsService productOptionsService;

    @Mock
    private StoresService storesService;

    private ProductsEntity product;
    private AddProductRequest addProductRequest;
    private ModifyProductRequest modifyProductRequest;
    private DeleteProductsRequest deleteProductsRequest;
    private StoresEntity store;
    private ProductOptionsEntity productOption1;
    private ProductOptionsEntity productOption2;
    private ProductImagesEntity productImage;
    private List<ProductOptionsEntity> productOptionsEntityList = new ArrayList<>();

    @BeforeEach
    void setUp() {

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

        productOption1 = ProductOptionsEntity.builder()
            .product(product)
            .name("샷 추가")
            .price(500)
            .build();

        productOption2 = ProductOptionsEntity.builder()
            .product(product)
            .name("샷 추가")
            .price(500)
            .build();

        productOptionsEntityList.addAll(Arrays.asList(productOption1, productOption2));

        try {
            Field idField = BaseEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(product, 1L);
            idField.set(store, 1L);
            idField.set(productOption1, 1L);
            idField.set(productOption2, 2L);

            // createdAt 설정
            Field createdAtField = BaseTimeEntity.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(product, LocalDateTime.now());

            // updatedAt 설정
            Field updatedAtField = BaseTimeEntity.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(product, LocalDateTime.now());

        } catch (Exception e) {
            throw new RuntimeException("Failed to set ID", e);
        }

        productImage = ProductImagesEntity.of(
            product,
            "americano.jpg",
            "encrypted_americano.jpg",
            "/images/products/"
        );

        addProductRequest = AddProductRequest.builder()
            .storeId("1")
            .name("아메리카노")
            .description("기본 아메리카노")
            .price(4000)
            .build();

        modifyProductRequest = ModifyProductRequest.builder()
            .productId("1")
            .name("아메리카노")
            .description("기본 아메리카노")
            .price(4000)
            .build();

        deleteProductsRequest = DeleteProductsRequest.builder()
            .productId("1")
            .build();
    }

    @Test
    @DisplayName("상품 목록 조회 - 성공")
    void getProducts_Success() {
        // given
        when(productsService.getProductByStoreId(1L)).thenReturn(List.of(product));
        when(productImagesService.getProductImagesByProductId(1L)).thenReturn(List.of());

        // when
        List<ProductResponse> result = productsBusiness.getAllProduct("1");

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("아메리카노");
        assertThat(result.getFirst().getDescription()).isEqualTo("기본 아메리카노");
        assertThat(result.getFirst().getPrice()).isEqualTo(4000);
    }

    @Test
    @DisplayName("상품 등록 - 성공")
    void addProduct_Success() {
        // given
        when(storesService.getStoreById(1L)).thenReturn(store);
        when(productsService.saveProduct(any())).thenReturn(product);

        // when
        ProductResponse result = productsBusiness.addProduct(addProductRequest);

        // then
        assertThat(result.getName()).isEqualTo("아메리카노");
        assertThat(result.getDescription()).isEqualTo("기본 아메리카노");
        assertThat(result.getPrice()).isEqualTo(4000);
    }

    @Test
    @DisplayName("상품 등록 - 실패 (존재하지 않는 매장)")
    void addProduct_Fail_StoreNotFound() {
        // given
        when(storesService.getStoreById(1L)).thenThrow(new CoreException(STORE_NOT_FOUND));

        // when & then
        assertThatThrownBy(() -> productsBusiness.addProduct(addProductRequest))
            .isInstanceOf(CoreException.class)
            .hasFieldOrPropertyWithValue("errorTypes", STORE_NOT_FOUND);
    }

    @Test
    @DisplayName("상품 수정 - 성공")
    void modifyProduct_Success() {
        // given
        when(productsService.getProductById(Long.valueOf("1"))).thenReturn(product);
        when(productsService.saveProduct(any())).thenReturn(product);

        // when
        ProductResponse result = productsBusiness.modifyProduct(modifyProductRequest);

        // then
        assertThat(result.getName()).isEqualTo("아메리카노");
        assertThat(result.getDescription()).isEqualTo("기본 아메리카노");
        assertThat(result.getPrice()).isEqualTo(4000);
    }

    @Test
    @DisplayName("상품 수정 - 실패 (존재하지 않는 상품)")
    void modifyProduct_Fail_NotFound() {
        // given
        when(productsService.getProductById(1L)).thenThrow(new CoreException(PRODUCT_NOT_FOUND));

        // when & then
        assertThatThrownBy(() -> productsBusiness.modifyProduct(modifyProductRequest))
            .isInstanceOf(CoreException.class)
            .hasFieldOrPropertyWithValue("errorTypes", PRODUCT_NOT_FOUND);
    }

    @Test
    @DisplayName("상품 삭제 - 성공")
    void deleteProduct_Success() {
        // given
        when(productsService.getProductById(1L)).thenReturn(product);

        // when & then
        productsBusiness.deleteProduct(deleteProductsRequest);

        // then
        verify(productImagesService).deleteProductImages(1L);
        verify(productOptionsService).deleteProductOptionsByProductId(1L);
        verify(productsService).deleteProduct(product);
    }

    @Test
    @DisplayName("상품 옵션 조회 - 성공")
    void getProductOption_Success() {
        // given
        when(productOptionsService.getProductOptionsByProductId(1L)).thenReturn(productOptionsEntityList);

        // when
        List<ProductOptionResponse> result = productsBusiness.getProductOptions("1");

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.getFirst().getName()).isEqualTo("샷 추가");
        assertThat(result.getFirst().getPrice()).isEqualTo(500);
    }

    @Test
    @DisplayName("상품 옵션 등록 - 성공")
    void addProductOption_Success() {
        // given
        AddProductOptionRequest request = AddProductOptionRequest.builder()
            .productId("1")
            .name("샷 추가")
            .price(500)
            .build();

        when(productsService.getProductById(1L)).thenReturn(product);
        when(productOptionsService.saveProductOptions(any())).thenReturn(productOption1);

        // when
        ProductOptionResponse result = productsBusiness.addProductOption(request);

        // then
        assertThat(result.getName()).isEqualTo("샷 추가");
        assertThat(result.getPrice()).isEqualTo(500);
    }

    @Test
    @DisplayName("상품 옵션 등록 - 실패 (중복된 옵션)")
    void addProductOption_Fail_DuplicateOption() {
        // given
        AddProductOptionRequest request = AddProductOptionRequest.builder()
            .productId("1")
            .name("샷 추가")
            .price(500)
            .build();

        when(productOptionsService.existsByProductIdAndName(1L, "샷 추가")).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> productsBusiness.addProductOption(request))
            .isInstanceOf(CoreException.class)
            .hasFieldOrPropertyWithValue("errorTypes", DUPLICATE_PRODUCT_OPTION);
    }

    @Test
    @DisplayName("상품 옵션 수정 - 성공")
    void modifyProductOption_Success() {
        // given
        ModifyProductOptionRequest request = ModifyProductOptionRequest.builder()
            .productOptionsId("1")
            .name("샷 빼기")
            .price(0)
            .build();

        when(productOptionsService.getProductOptionsById(1L)).thenReturn(productOption1);
        when(productOptionsService.saveProductOptions(any())).thenReturn(productOption1);

        // when
        ProductOptionResponse result = productsBusiness.modifyProductOption(request);

        // then
        assertThat(result.getName()).isEqualTo("샷 빼기");
        assertThat(result.getPrice()).isEqualTo(0);
    }
} 