package org.baebe.coffeetrading.commons.types.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    PAY_COMPLETED("결제완료"),
    ORDER_CHECKED("주문확인"),
    ORDER_REJECTED("주문취소"),
    RECEIVE_WAITED("전달대기"),
    RECEIVE_COMPLETED("전달완료"),
    RECEIVE_FAILED("미수취");

    private final String value;
}
