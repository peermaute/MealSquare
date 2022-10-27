package dev.peermaute.mealsquare.meals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to create filter criteria more easily.
 */
@Getter
@Setter
@AllArgsConstructor
public class Filter {

    private String name;

    private String carbBase;

    private String ingredient;

    private String doesNotContain;

    private String tag;

    private int maxPrepTime;
}
