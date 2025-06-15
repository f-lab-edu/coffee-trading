package org.baebe.coffeetrading.domains.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.store.repository.StoresRepository;
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
class StoresServiceTest {

    @InjectMocks
    private StoresService storesService;

    @Mock
    private StoresRepository storesRepository;

    private StoresEntity testStore;
    private Pageable pageable;

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

        pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Test
    @DisplayName("매장 검색 - 성공")
    void searchStores_Success() {
        // given
        Page<StoresEntity> storePage = new PageImpl<>(List.of(testStore), pageable, 1);
        when(storesRepository.getStoresBytitleAndAddressAndStoreTypeAndStatus(any(), any(), any(), any(), any()))
            .thenReturn(storePage);

        // when
        Page<StoresEntity> result = storesService.searchStores("카페", "서울", "CAFE", "OPEN", pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getTitle()).isEqualTo(testStore.getTitle());
        assertThat(result.getContent().getFirst().getAddress()).isEqualTo(testStore.getAddress());
    }

    @Test
    @DisplayName("매장 조회 - 성공")
    void getStoreById_Success() {
        // given
        when(storesRepository.findById(any())).thenReturn(Optional.of(testStore));

        // when
        StoresEntity result = storesService.getStoreById(1L);

        // then
        assertThat(result.getTitle()).isEqualTo(testStore.getTitle());
        assertThat(result.getAddress()).isEqualTo(testStore.getAddress());
    }

    @Test
    @DisplayName("매장 조회 - 실패 (존재하지 않는 매장)")
    void getStoreById_Fail_NotFound() {
        // given
        when(storesRepository.findById(any())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> storesService.getStoreById(1L))
            .isInstanceOf(CoreException.class)
            .hasFieldOrPropertyWithValue("errorTypes", ErrorTypes.STORE_NOT_FOUND);
    }

    @Test
    @DisplayName("매장 중복 확인 - 성공 (중복 없음)")
    void existsByTitleAndAddress_Success_NotExists() {
        // given
        when(storesRepository.existsByTitleIgnoreCaseAndAddressIgnoreCase(any(), any())).thenReturn(false);

        // when
        boolean result = storesService.existsByTitleAndAddress("새로운 카페", "서울시 서초구");

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("매장 중복 확인 - 성공 (중복 있음)")
    void existsByTitleAndAddress_Success_Exists() {
        // given
        when(storesRepository.existsByTitleIgnoreCaseAndAddressIgnoreCase(any(), any())).thenReturn(true);

        // when
        boolean result = storesService.existsByTitleAndAddress("테스트 카페", "서울시 강남구");

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("매장 저장 - 성공")
    void saveStore_Success() {
        // given
        when(storesRepository.save(any())).thenReturn(testStore);

        // when
        StoresEntity result = storesService.saveStore(testStore);

        // then
        assertThat(result.getTitle()).isEqualTo(testStore.getTitle());
        assertThat(result.getAddress()).isEqualTo(testStore.getAddress());
    }

    @Test
    @DisplayName("전체 매장 조회 - 성공")
    void getAllStores_Success() {
        // given
        when(storesRepository.findAll()).thenReturn(List.of(testStore));

        // when
        List<StoresEntity> result = storesService.getAllStores();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo(testStore.getTitle());
        assertThat(result.getFirst().getAddress()).isEqualTo(testStore.getAddress());
    }
} 