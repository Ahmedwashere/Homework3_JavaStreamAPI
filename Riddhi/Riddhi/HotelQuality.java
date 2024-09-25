
public enum HotelQuality {
    THREE_STAR(3),
    FOUR_STAR(4),
    FIVE_STAR(5);

    public final int numStars;

    HotelQuality(int numStars) {
        this.numStars = numStars;
    }

    int getNumStars() {
        return numStars;
    }

    static HotelQuality qualityFactory(int num){
        return switch(num){
            case 3 -> HotelQuality.THREE_STAR;
            case 4 -> HotelQuality.FOUR_STAR;
            case 5 -> HotelQuality.FIVE_STAR;
            default -> throw new IllegalArgumentException(
                    String.format("The value %d is not a valid number of stars for a hotel", num));
        };
    }

    public static void main(String[] args) {
        System.out.println("\n\nHello, HotelQuality.java!\n");

        HotelQuality h5 = HotelQuality.FIVE_STAR;
        int numStars = h5.numStars;
        System.out.printf("\n%s has %d stars\n\n", h5, numStars);

        // convert case sensitive String of enum value to an enum type
        HotelQuality h4 = HotelQuality.valueOf("FOUR_STAR");
        
        if (h5.numStars > h4.numStars) {  // can't do h5 > h4
            System.out.printf("%s hotels are better than %d-star hotels\n", h5, h4.numStars);
        }

        // Use values() to get an array of all HotelQuality's enumerated values
        System.out.println("\nAll the hotel qualities and their number of stars:");
        for (HotelQuality hq : values()) {
            System.out.printf("%s \t %d stars\n", hq, hq.numStars);
        }

        HotelQuality h = HotelQuality.values()[0];
        System.out.printf("\n%s is the hotel quality at index 0\n", h);
    }
}
