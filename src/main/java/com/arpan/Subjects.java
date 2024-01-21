package com.arpan;

public enum Subjects {
    GENERAL_KNOWLEDGE(9), BOOKS(10), FILM(11), MUSIC(12),
    TELEVISION(14), MUSICALS_AND_THEATERS(13), VIDEO_GAMES(15),
    BOARD_GAMES(16), SCIENCE_AND_NATURE(17), COMPUTERS(18),
    MATHEMATICS(19), MYTHOLOGY(20), SPORTS(21), GEOGRAPHY(22),
    HISTORY(23), POLITICS(24), ART(25), CELEBRITIES(26),
    ANIMALS(27), VEHICLES(28), COMICS(29), GADGETS(30),
    JAPANESE_ANIME_AND_MANGA(31), CARTOON_AND_ANIMATIONS(32);

    private final int value;

    Subjects(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        String[] words = name().split("_");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            formattedName.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }

        return formattedName.toString().trim();
    }
}
