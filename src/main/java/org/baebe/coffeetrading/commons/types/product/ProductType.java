package org.baebe.coffeetrading.commons.types.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    COFFEE("커피"),
    DECAF("디카페인"),
    COLD_BREW("콜드브루"),
    COLD_BREW_DECAF("콜드브루 디카페일"),
    SMOOTHIE_FRAPPE("스무디&프라페"),
    ADE_JUICE("에이드&주스"),
    DRINK("일반음료"),
    TEA("티"),
    MD_GOODS("MD 상품"),
    DESSERT("디저트");

    private final String value;
}
