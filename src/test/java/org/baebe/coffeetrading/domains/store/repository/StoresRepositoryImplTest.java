package org.baebe.coffeetrading.domains.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.baebe.coffeetrading.api.config.QuerydslConfig;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
class StoresRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StoresRepository storesRepository;

    private JPAQueryFactory queryFactory;
    private StoresRepositoryImpl storesRepositoryImpl;
    private StoresEntity testStore;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(entityManager);
        storesRepositoryImpl = new StoresRepositoryImpl(queryFactory);

        testStore = StoresEntity.builder()
            .title("테스트 카페")
            .address("서울시 강남구")
            .roadNameAddress("서울시 강남구 테헤란로 123")
            .telephone("02-1234-5678")
            .description("테스트용 카페입니다.")
            .status(StoreStatus.OPEN)
            .storeType(StoreTypes.PRIVATE_STORE)
            .build();

        storesRepository.save(testStore);

        pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Test
    @DisplayName("제목으로 가게 검색")
    void getStoresBytitleAndAddressAndStoreTypeAndStatus_SearchByTitle() {
        // when
        Page<StoresEntity> result = storesRepositoryImpl.getStoresBytitleAndAddressAndStoreTypeAndStatus(
            "테스트", null, null, null, pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getTitle()).isEqualTo(testStore.getTitle());
    }

    @Test
    @DisplayName("주소로 가게 검색")
    void getStoresBytitleAndAddressAndStoreTypeAndStatus_SearchByAddress() {
        // when
        Page<StoresEntity> result = storesRepositoryImpl.getStoresBytitleAndAddressAndStoreTypeAndStatus(
            null, "강남구", null, null, pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getAddress()).isEqualTo(testStore.getAddress());
    }

    @Test
    @DisplayName("가게 타입으로 검색")
    void getStoresBytitleAndAddressAndStoreTypeAndStatus_SearchByStoreType() {
        // when
        Page<StoresEntity> result = storesRepositoryImpl.getStoresBytitleAndAddressAndStoreTypeAndStatus(
            null, null, "PRIVATE_STORE", null, pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getStoreType()).isEqualTo(testStore.getStoreType());
    }

    @Test
    @DisplayName("상태로 가게 검색")
    void getStoresBytitleAndAddressAndStoreTypeAndStatus_SearchByStatus() {
        // when
        Page<StoresEntity> result = storesRepositoryImpl.getStoresBytitleAndAddressAndStoreTypeAndStatus(
            null, null, null, "OPEN", pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getStatus()).isEqualTo(testStore.getStatus());
    }

    @Test
    @DisplayName("여러 조건으로 가게 검색")
    void getStoresBytitleAndAddressAndStoreTypeAndStatus_SearchByMultipleConditions() {
        // when
        Page<StoresEntity> result = storesRepositoryImpl.getStoresBytitleAndAddressAndStoreTypeAndStatus(
            "테스트", "강남구", "PRIVATE_STORE", "OPEN", pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getTitle()).isEqualTo(testStore.getTitle());
        assertThat(result.getContent().getFirst().getAddress()).isEqualTo(testStore.getAddress());
        assertThat(result.getContent().getFirst().getStoreType()).isEqualTo(testStore.getStoreType());
        assertThat(result.getContent().getFirst().getStatus()).isEqualTo(testStore.getStatus());
    }

    @Test
    @DisplayName("페이징 처리")
    void getStoresBytitleAndAddressAndStoreTypeAndStatus_Paging() {
        // when
        Page<StoresEntity> result = storesRepositoryImpl.getStoresBytitleAndAddressAndStoreTypeAndStatus(
            null, null, null, null, pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getSize()).isEqualTo(10);
    }
} 