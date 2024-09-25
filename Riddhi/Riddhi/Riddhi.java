import java.time.LocalDate;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Collectors;

/*
https://www.youtube.com/watch?v=0ada8fAMpVs

Riddhi Dutta gives a fairly thorough introduction to Java8 lambdas at the above URL.
You should watch the video more than once.

During class, I'll discuss:
    Enumerated Types by looking at the HotelQuality.java file.
    Refactoring code by re-writing TODO 1 in Hotels.java.
    Solve familiar Java problems (TODO 2-4) in Hotels.java.
    Pass a function as a parameter by doing TODO 5 and TODO 6 in Hotels.java.
    Implementing a functional interface by looking at the Hotel.java file.
    Solve TODO 7 and TODO 8 in Riddhi.java.
    And then you'll solve the remaining TODOs in this Riddhi.java file.
*/
public class Riddhi {

    static Random rand = new Random();
    static int year = LocalDate.now().getYear();

    public static void main(String[] args) {
        System.out.println("\n\nHello, Lambdas!\n");

        Hotels hotels = new Hotels();
        System.out.printf("Hotels:\n%s\n", hotels);

        // TODO 7 - Sort the hotels by price.
        List<Hotel> listOfHotels = hotels.get();
        // You may have used Java8 when sorting objects.
        // When you see an arrow -> in Java, you're defining (not calling) a function.
        listOfHotels.sort((h1, h2) -> h2.compareTo(h1));  // why not     h1.compareTo(h2)

        /*
         The reason that we use h2.compareTo(h1) is because we can to sort in descending order by
         price. h1.compare(h2) would give us ascending order by price
         */

        System.out.printf("\nList<Hotel> (sorted by price):\n%s\n", listOfHotels);

        // This statement will also remove this hotel from hotels.get() as the object reference is
        // the same for both.
        Hotel removedHotel = listOfHotels.remove(listOfHotels.size() - 1);

        System.out.printf("\nHotels (sorted by price):\n%s\n", hotels);
        // TODO 8 - What happened to the 2025 hotel?
        /*
            The 2025 hotel was deleted from listOfHotels as we called listOfHotels.remove().
            the remove() method returns the hotel that we removed.
         */
        System.out.println("The hotel that was removed from the listOfHotels is: " + removedHotel);

        // TODO 9 - What are the 4 star hotels?
        // (a) In 1724, we'd write a for-i loop and return a list of hotels with quality == 4
        // (b) then we learned to write an enhanced-for loop and return hotels with quality == 4
        // (c) now we know we can use an enumerated type so quality == HotelQuality.FOUR_STAR
        // (d) Post-Java8, we'd use a lambda
        List<Hotel> fourStarHotels = hotels.filterBy((Hotel h) -> h.getQuality().equals(HotelQuality.FOUR_STAR));

        /*
            We can do this easily using java streams as well.
         */
        List<Hotel> fourStarHotelsFunctional = hotels.get()
                .stream()
                .filter(hotel -> hotel.getQuality() == HotelQuality.FOUR_STAR)
                .collect(Collectors.toList());

        System.out.printf("\nThe four star hotels:\n%s\n", fourStarHotels);
        System.out.printf("\nThe four star hotels (FUNCTIONAL):\n%s\n", fourStarHotelsFunctional);

        //------------------------- Lambdas
        // When you see an arrow -> in Java, you're using a lambda.
        // A lambda gives us a way to pass a function as a parameter to some other function.
        // When you see an arrow -> in Java, you're defining (not calling) a function.

        List<Hotel> costLessThan100 = hotels.filterBy((Hotel h) -> h.getPrice() < 100);
        System.out.printf("\nHotels less than $100: %s\n", costLessThan100);

        // TODO 10 - Which hotels are more than 10 years old?
        List<Hotel> moreThanTenYearsOld = hotels.get()
                .stream()
                .filter(hotel -> (year - hotel.getRenovationYear()) > 10)
                .collect(Collectors.toList());
        System.out.printf("\nHotels more than 10 years old: %s\n", moreThanTenYearsOld);

        // TODO 11 - How many hotels are priced in 111..333?
        List<Hotel> between111And333 = hotels.get().stream().filter(hotel -> hotel.getPrice() >= 111 && 333 >= hotel.getPrice())
                .collect(Collectors.toList());
        System.out.printf("\nHotels prices in 111...333 : %s\n", between111And333);

        // TODO 12 - Which hotels cost more than 300 but are not 5-star hotels?
        List<Hotel> moreThan300ButAreNotFiveStars = hotels.get()
                .stream()
                .filter(hotel -> hotel.getPrice() > 300)
                // I know I could combine both filter lambdas into one lambda
                // but I think this is better for readability
                .filter(hotel -> hotel.getQuality() != HotelQuality.FIVE_STAR)
                .collect(Collectors.toList());
        System.out.printf("\nHotels prices more than 300 but not 5 star : %s\n",
                moreThan300ButAreNotFiveStars);

        // Java also has streams!


        // TODO 13 - What is the cheapest price of 5-star hotel(s) that are no more than 3 years old?
        List<Hotel> topYoungHotels = hotels.filterBy(h -> h.getQuality() == HotelQuality.FIVE_STAR && h.getRenovationYear() > 2021);
        double price = topYoungHotels.stream().map(Hotel::getPrice).max(Double::compare).get();
        System.out.printf("\n$%,.2f is the cheapest price of a 5-star hotel renovated after 2021\n", price);

        // TODO 14 - Output the price and year of each hotel.  Can I solve this using a predicate?
        //No you won't really need a predicate for this. Instead we can map each hotel object into
        //a string that has the price and year of each hotel. I could also use a forEach loop
        System.out.println("\nPrice and Year of Each Hotel\n");
        hotels.get()
                .forEach(hotel ->
                        System.out.printf(
                                "%s: (price: %.2f, year: %d)\n",
                                hotel.getName(), hotel.getPrice(), hotel.getRenovationYear()
                        )
                );


        // TODO 15 - Output the hotel renovation years a decade from now.
        System.out.println("\nHotel renovation years a decade from now (without map(...))\n");
        hotels.get()
                // forEach takes a consumer instead of a predicate (basically just returns void)
                .forEach(hotel ->
                System.out.printf("%s: (price: %.2f, year: %d)\n",
                hotel.getName(), hotel.getPrice(), hotel.getRenovationYear() + 10)
        );

        System.out.println("\nHotel renovation years a decade from now (with map(...))\n");
        // I could also do this as well
        List<Hotel> hotelsWithADecadeAdded = hotels.get().stream()
                .map(hotel ->
                        new Hotel(hotel.getName(), hotel.getPrice(),
                                hotel.getRenovationYear() + 10, hotel.getQuality()))
                .collect(Collectors.toList());

        // filter, map, and reduce
        // filter      a predicate that separates the records in a stream
        // map         applies a lambda to attributes of a record
        // reduce      combines a bunch of intermediate records into a single value (sum, count, toList)

        // TODO 16 - Create a new list of hotels by giving all hotels a 20% discount.
        List<Hotel> discountAllPrices = hotels.get().stream()
                .map(h ->
                    new Hotel(h.getPrice() * 0.80, h.getRenovationYear(), h.getQuality()))
                .collect(Collectors.toList());
        System.out.printf("\n20%% discount to all %d hotels: \t %s\n", discountAllPrices.size(),
                discountAllPrices);

        // TODO 17 - Create a new list of hotels by giving a 20% discount to the hotels renovated in an even year.

        // TODO 18 - What will it cost to get a room in all the hotels for one day?
        double sumPrice = hotels.get().stream().mapToDouble(h -> h.getPrice()).sum();
        System.out.printf("\nIt costs $%,.2f to rent all the hotel rooms\n", sumPrice);

        // TODO 19 - What is the average age (to 3 decimal places) of all the hotels?
        OptionalDouble averageAgeOptional = hotels.get().stream()
                .mapToDouble(h -> h.getRenovationYear())
                .average();
        // Check if the Optional is empty. If it is, then give 0 as the average
        double averageAge = averageAgeOptional.isPresent() ? averageAgeOptional.getAsDouble() : 0;

        System.out.printf("\nThe average age (to 3 decimal places) of all the hotels is: %.3f",
                averageAge);

        // TODO 20 - Subtract 33% from the price of all 5 star hotels,
        //    add 100 to the age of all 4-star hotels,
        //    and raise the quality of all 3-star hotels by a random 0 or 1 or 2.
        List<Hotel> modifiedHotels = hotels.get().stream()
                .map(h -> switch (h.getQuality()) {
                    case FIVE_STAR ->
                            new Hotel(h.getName(), h.getPrice() * 0.67, h.getRenovationYear(), h.getQuality());

                    case FOUR_STAR ->
                            new Hotel(h.getName(), h.getPrice(), h.getRenovationYear() + 100, h.getQuality());

                    case THREE_STAR -> new Hotel(h.getName(), h.getPrice(), h.getRenovationYear(),
                            HotelQuality.qualityFactory(
                                    h.getQuality().getNumStars() + rand.nextInt(0, 3)));
                    default -> h;
                }).collect(Collectors.toList());

        System.out.println("\nHotels Before Modification");
        hotels.get().forEach(h -> System.out.println(h));

        System.out.println("\nHotels After Modification");
        modifiedHotels.forEach(h -> System.out.println(h));

        // TODO 21 - Soon, we'll have an in-class 30 minute mini-test on lambdas
        //     that will add [0..20] points to your Hwk03 score.
        // Noted

    }
}
