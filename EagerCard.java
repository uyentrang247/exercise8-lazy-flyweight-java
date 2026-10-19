import java.util.EnumMap;
import java.util.Map;

/** Preinitialized flyweight pool used as the eager comparison approach. */
final class EagerCard {
    private static final Map<Rank, Map<Suit, EagerCard>> CARDS = createCards();

    private final Rank rank;
    private final Suit suit;

    private EagerCard(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    private static Map<Rank, Map<Suit, EagerCard>> createCards() {
        Map<Rank, Map<Suit, EagerCard>> cards = new EnumMap<>(Rank.class);
        for (Rank rank : Rank.values()) {
            Map<Suit, EagerCard> bySuit = new EnumMap<>(Suit.class);
            for (Suit suit : Suit.values()) {
                bySuit.put(suit, new EagerCard(rank, suit));
            }
            cards.put(rank, bySuit);
        }
        return cards;
    }

    static void initialize() {
        CARDS.size();
    }

    static EagerCard get(Rank rank, Suit suit) {
        return CARDS.get(rank).get(suit);
    }
}
