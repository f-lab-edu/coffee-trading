package org.baebe.coffeetrading.api.store.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.baebe.coffeetrading.api.store.dto.request.AddStoreRequest;
import org.baebe.coffeetrading.api.store.dto.request.ModifyStoreRequest;
import org.baebe.coffeetrading.api.store.dto.request.StoreRequest;
import org.baebe.coffeetrading.api.store.dto.response.StoreResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.store.service.StoresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class StoreBusinessTest {

    @InjectMocks
    private StoreBusiness storeBusiness;

    @Mock
    private StoresService storesService;

    private StoresEntity testStore;
    private AddStoreRequest addStoreRequest;
    private ModifyStoreRequest modifyStoreRequest;
    private StoreRequest searchRequest;

    @BeforeEach
    void setUp() {
        testStore = StoresEntity.builder()
            .title("테스트 카페")
            .address("서울시 강남구")
            .roadNameAddress("서울시 강남구 테헤란로 123")
            .telephone("02-1234-5678")
            .description("테스트용 카페입니다.")
            .status(StoreStatus.OPEN)
            .storeType(StoreTypes.PRIVATE_STORE)
            .build();

        addStoreRequest = AddStoreRequest.builder()
            .title("새로운 카페")
            .address("서울시 서초구")
            .roadNameAddress("서울시 서초구 서초대로 456")
            .telephone("02-2345-6789")
            .description("새로 오픈한 카페입니다.")
            .status(StoreStatus.OPEN)
            .storeType(StoreTypes.PRIVATE_STORE)
            .build();

        modifyStoreRequest = ModifyStoreRequest.builder()
            .storeId("1")
            .title("수정된 카페")
            .address("서울시 서초구")
            .roadNameAddress("서울시 서초구 서초대로 456")
            .telephone("02-2345-6789")
            .description("수정된 카페입니다.")
            .status(StoreStatus.OPEN)
            .storeType(StoreTypes.PRIVATE_STORE)
            .build();

        searchRequest = StoreRequest.builder()
            .title("카페")
            .address("서울")
            .storeType("CAFE")
            .status("OPEN")
            .page(0)
            .size(10)
            .sortBy("createdAt")
            .sortDirection("DESC")
            .build();
    }

    @Test
    @DisplayName("매장 검색 - 성공")
    void getStores_Success() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<StoresEntity> storePage = new PageImpl<>(List.of(testStore), pageable, 1);
        when(storesService.searchStores(any(), any(), any(), any(), any())).thenReturn(storePage);

        // when
        Page<StoreResponse> result = storeBusiness.getStores(searchRequest);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getTitle()).isEqualTo(testStore.getTitle());
        assertThat(result.getContent().getFirst().getAddress()).isEqualTo(testStore.getAddress());
    }

    @Test
    @DisplayName("매장 등록 - 성공")
    void addStore_Success() {
        // given
        when(storesService.existsByTitleAndAddress(any(), any())).thenReturn(false);
        when(storesService.saveStore(any())).thenReturn(testStore);

        // when
        StoreResponse result = storeBusiness.addStore(addStoreRequest);

        // then
        assertThat(result.getTitle()).isEqualTo(testStore.getTitle());
        assertThat(result.getAddress()).isEqualTo(testStore.getAddress());
    }

    @Test
    @DisplayName("매장 등록 - 실패 (중복된 매장)")
    void addStore_Fail_DuplicateStore() {
        // given
        when(storesService.existsByTitleAndAddress(any(), any())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> storeBusiness.addStore(addStoreRequest))
            .isInstanceOf(CoreException.class)
            .hasFieldOrPropertyWithValue("errorTypes", ErrorTypes.DUPLICATE_STORE);
    }

    @Test
    @DisplayName("매장 수정 - 성공")
    void modifyStore_Success() {
        // given
        when(storesService.getStoreById(any())).thenReturn(testStore);
        when(storesService.saveStore(any())).thenReturn(testStore);

        // when
        StoreResponse result = storeBusiness.modifyStore(modifyStoreRequest);

        // then
        assertThat(result.getTitle()).isEqualTo(testStore.getTitle());
        assertThat(result.getAddress()).isEqualTo(testStore.getAddress());
    }

    @Test
    @DisplayName("매장 삭제 - 성공")
    void deleteStore_Success() {
        // given
        when(storesService.getStoreById(any())).thenReturn(testStore);

        // when & then
        storeBusiness.deleteStore(1L);
    }
} 