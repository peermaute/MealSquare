package dev.peermaute.mealsquare;

import lombok.Data;

/**
 * Class to create filter criteria more easily.
 */
@Data
public class Filter {

    private String name;

    private String carbBase;

    private String ingredient;

    private String doesNotContain;

    private String tag;

    private int maxPrepTime;
}
