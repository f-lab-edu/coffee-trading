package org.baebe.coffeetrading.api.user.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.baebe.coffeetrading.api.user.dto.request.MobileAuthenticationRequest;
import org.baebe.coffeetrading.api.user.dto.request.ModifyPasswordRequest;
import org.baebe.coffeetrading.api.user.dto.request.MyStoresRequest;
import org.baebe.coffeetrading.api.user.dto.request.RegisterRequest;
import org.baebe.coffeetrading.api.user.dto.response.NaverProfileResponse;
import org.baebe.coffeetrading.api.user.dto.response.RegisterResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.commons.types.user.GenderTypes;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.baebe.coffeetrading.commons.types.user.UserStatus;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.store.service.StoresService;
import org.baebe.coffeetrading.domains.user.entity.UserStoreGroupsEntity;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;
import org.baebe.coffeetrading.domains.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
class UserBusinessTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StoresService storesService;

    @InjectMocks
    private UserBusiness userBusiness;

    private UsersEntity testUser;
    private StoresEntity testStore;
    private UserStoreGroupsEntity testStoreGroup;

    @BeforeEach
    void setUp() {
        testUser = UsersEntity.builder()
            .email("test@test.com")
            .password("test1234^^")
            .userName("테스트")
            .phone("010-1234-5678")
            .birthDay("20000607")
            .gender(GenderTypes.MAN)
            .nickname("testUser123")
            .status(UserStatus.ENABLED)
            .accountType(AccountTypes.WEB)
            .userType(UserRole.USER)
            .build();

        testStore = StoresEntity.builder()
            .title("별다방")
            .address("서울시 강남구")
            .roadNameAddress(null)
            .telephone("02-333-5678")
            .description("안녕하세요. 별다방 입니다.")
            .storeType(StoreTypes.FRANCHISE_STORE)
            .build();

        testStoreGroup = UserStoreGroupsEntity.builder()
            .name("나만의 가게 리스트 1")
            .user(testUser)
            .build();
    }

    @Nested
    @DisplayName("일반 회원가입 테스트")
    class RegisterTest {
        private RegisterRequest registerRequest;

        @BeforeEach
        void setUp() {
            registerRequest = RegisterRequest.builder()
                .email("test@test.com")
                .password("test1234^^")
                .nickname("testUser123")
                .accountType(AccountTypes.WEB)
                .userType(UserRole.USER)
                .build();
        }

        @Test
        @DisplayName("회원가입 성공")
        void registerSuccess() {
            // given
            when(userService.existsByEmail(any())).thenReturn(false);
            when(userService.existsByNickname(any())).thenReturn(false);
            when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
            when(userService.saveUser(any())).thenReturn(testUser);

            // when
            RegisterResponse response = userBusiness.registerHandle(registerRequest);

            // then
            assertNotNull(response);
            assertEquals(testUser.getEmail(), response.getEmail());
            assertEquals(testUser.getNickname(), response.getNickname());
            verify(userService).saveUser(any());
        }

        @Test
        @DisplayName("이메일 중복으로 인한 회원가입 실패")
        void registerFailByDuplicateEmail() {
            // given
            when(userService.existsByEmail(any())).thenReturn(true);

            // when & then
            assertThrows(CoreException.class, () -> {
                userBusiness.registerHandle(registerRequest);
            });
        }

        @Test
        @DisplayName("닉네임 중복으로 인한 회원가입 실패")
        void registerFailByDuplicateNickname() {
            // given
            when(userService.existsByEmail(any())).thenReturn(false);
            when(userService.existsByNickname(any())).thenReturn(true);

            // when & then
            assertThrows(CoreException.class, () -> {
                userBusiness.registerHandle(registerRequest);
            });
        }
    }

    @Nested
    @DisplayName("소셜 회원가입 테스트")
    class OAuth2RegisterTest {
        private NaverProfileResponse naverProfileResponse;

        @BeforeEach
        void setUp() {
            NaverProfileResponse.NaverProfile response = NaverProfileResponse.NaverProfile
                .builder()
                .email("test@test.com")
                .name("테스트")
                .mobile("010-1234-5678")
                .gender("M")
                .nickname("testUser123")
                .build();

            naverProfileResponse = new NaverProfileResponse();
            naverProfileResponse.setResponse(response);
        }

        @Nested
        @DisplayName("네이버 회원가입 테스트")
        class NaverRegisterTest {

            @Test
            @DisplayName("네이버 회원가입 성공")
            void naverRegisterSuccess() {
                // given
                when(userService.existsByEmail(any())).thenReturn(false);
                when(userService.existsByNickname(any())).thenReturn(false);
                when(userService.saveUser(any())).thenReturn(testUser);

                // when
                userBusiness.oauth2RegisterHandle(naverProfileResponse);

                // then
                verify(userService).saveUser(any());
            }

            @Test
            @DisplayName("이메일 중복으로 인한 네이버 회원가입 실패")
            void naverRegisterFailByDuplicateEmail() {
                // given
                when(userService.existsByEmail(any())).thenReturn(true);

                // when & then
                assertThrows(CoreException.class, () -> {
                    userBusiness.oauth2RegisterHandle(naverProfileResponse);
                });
            }

            @Test
            @DisplayName("닉네임 중복으로 인한 네이버 회원가입 실패")
            void naverRegisterFailByDuplicateNickname() {
                // given
                when(userService.existsByEmail(any())).thenReturn(false);
                when(userService.existsByNickname(any())).thenReturn(true);

                // when & then
                assertThrows(CoreException.class, () -> {
                    userBusiness.oauth2RegisterHandle(naverProfileResponse);
                });
            }
        }
    }

    @Nested
    @DisplayName("본인인증 테스트")
    class MobileAuthenticationTest {
        private MobileAuthenticationRequest request;
        private String userId;

        @BeforeEach
        void setUp() {
            userId = "1";
            request = MobileAuthenticationRequest.builder()
                .userName("테스트")
                .birthday("20000607")
                .phone("010-1234-5678")
                .gender(GenderTypes.MAN)
                .build();
        }

        @Test
        @DisplayName("본인인증 성공")
        void authenticationSuccess() {
            // given
            when(userService.getByUserId(any())).thenReturn(testUser);
            when(userService.getByUsernameAndBirthdayAndPhone(any(), any(), any()))
                .thenReturn(List.of());

            // when
            userBusiness.authenticationByMobile(userId, request);

            // then
            verify(userService).saveUser(any());
        }

        @Test
        @DisplayName("이미 인증된 사용자로 인한 실패")
        void authenticationFailByDuplicateUser() {
            // given
            when(userService.getByUsernameAndBirthdayAndPhone(any(), any(), any()))
                .thenReturn(List.of(testUser));

            // when & then
            assertThrows(CoreException.class, () -> {
                userBusiness.authenticationByMobile(userId, request);
            });
        }
    }

    @Nested
    @DisplayName("비밀번호 변경 테스트")
    class ModifyPasswordTest {
        private ModifyPasswordRequest request;
        private String userId;

        @BeforeEach
        void setUp() {
            userId = "1";
            request = ModifyPasswordRequest.builder()
                .oldPassword("test1234^^")
                .newPassword("newPassword123^^")
                .build();
        }

        @Test
        @DisplayName("비밀번호 변경 성공")
        void modifyPasswordSuccess() {
            // given
            when(userService.getByUserId(any())).thenReturn(testUser);
            when(passwordEncoder.matches(any(), any()))
                .thenReturn(true)
                .thenReturn(false);
            when(passwordEncoder.encode(any())).thenReturn("encodedNewPassword");

            // when
            userBusiness.modifyPassword(userId, request);

            // then
            verify(userService).saveUser(any());
        }

        @Test
        @DisplayName("기존 비밀번호 불일치로 인한 실패")
        void modifyPasswordFailByWrongOldPassword() {
            // given
            when(userService.getByUserId(any())).thenReturn(testUser);
            when(passwordEncoder.matches(any(), any())).thenReturn(false);

            // when & then
            assertThrows(CoreException.class, () -> {
                userBusiness.modifyPassword(userId, request);
            });
        }

        @Test
        @DisplayName("이전 비밀번호와 동일한 비밀번호로 변경 시도로 인한 실패")
        void modifyPasswordFailBySamePassword() {
            // given
            when(userService.getByUserId(any())).thenReturn(testUser);
            when(passwordEncoder.matches(any(), any())).thenReturn(true);

            // when
            request.setNewPassword("test1234^^");

            // then
            assertThrows(CoreException.class, () -> {
                userBusiness.modifyPassword(userId, request);
            });
        }
    }

    @Nested
    @DisplayName("나만의 가게 목록 관리 테스트")
    class MyStoreListTest {
        private String userId;
        private String storeListName;
        private MyStoresRequest myStoresRequest;

        @BeforeEach
        void setUp() {
            userId = "1";
            storeListName = "나만의 가게 리스트 1";
            myStoresRequest = MyStoresRequest.builder()
                .userStoreGroupId("1")
                .storeId("1")
                .build();
        }

        @Test
        @DisplayName("나만의 가게 목록 추가 성공")
        void addMyStoreListSuccess() {
            // given
            when(userService.getByUserId(any())).thenReturn(testUser);
            when(userService.exitsByUserStoreGroupName(any(), any())).thenReturn(false);

            // when
            userBusiness.addMyStoreList(userId, storeListName);

            // then
            verify(userService).saveUserStoreList(any());
        }

        @Test
        @DisplayName("나만의 가게 목록 이름 중복으로 인한 실패")
        void addMyStoreListFailByDuplicateName() {
            // given
            when(userService.getByUserId(any())).thenReturn(testUser);
            when(userService.exitsByUserStoreGroupName(any(), any())).thenReturn(true);

            // when & then
            assertThrows(CoreException.class, () -> {
                userBusiness.addMyStoreList(userId, storeListName);
            });
        }

        @Test
        @DisplayName("나만의 가게 목록에 가게 추가 성공")
        void addStoreToMyStoreListSuccess() {
            // given
            when(userService.getUserStoreGroupsById(any())).thenReturn(testStoreGroup);
            when(storesService.getStoreById(any())).thenReturn(testStore);
            when(userService.getUserStoresByUserStoreGroupIdAndStoreId(any(), any()))
                .thenReturn(Optional.empty());

            // when
            userBusiness.myStoreHandle(myStoresRequest);

            // then
            verify(userService).saveUserStore(any());
        }

        @Test
        @DisplayName("존재하지 않는 가게 목록 삭제 시도로 인한 실패")
        void removeMyStoreListFailByNotFound() {
            // given
            doThrow(new EmptyResultDataAccessException(1))
                .when(userService).deleteUserStoreList(any());

            // when & then
            assertThrows(CoreException.class, () -> {
                userBusiness.removeMyStoreList(myStoresRequest.getUserStoreGroupId());
            });
        }
    }
}