public class Hotel implements Comparable<Hotel>{
    private static int NUM_HOTELS = 1;

    private String name;
    private double price;
    private int renovationYear;
    private HotelQuality quality;
    

    public Hotel(String name, double price, int renovationYear, HotelQuality quality) {
        this.name = name;
        this.price = price;
        this.renovationYear = renovationYear;
        this.quality = quality;
    }

    public Hotel(double price, int renovationYear, HotelQuality quality) {
        this.name = "Hotel-" + NUM_HOTELS++;
        this.price = price;
        this.renovationYear = renovationYear;
        this.quality = quality;
    }

    @Override
    public int compareTo(Hotel other) {
        // return price - other.price;
    
        if (price > other.price) {
            return 1;
        } else if (price == other.price) {
            return 0;
        } else {
            return -1;
        }
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getRenovationYear() {
        return renovationYear;
    }

    public HotelQuality getQuality() {
        return quality;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuality(HotelQuality quality) {
        this.quality = quality;
    }

    public void setRenovationYear(int renovationYear) {
        this.renovationYear = renovationYear;
    }


    @Override
    public String toString() {
        return String.format("%s|$%,.2f|%d|%s", name, price, renovationYear, quality);
    }

}
