import java.util.EnumMap;
import java.util.Map;

/** Immutable card flyweight: one shared instance for each rank/suit pair. */
public final class Card {
    private static final Map<Rank, Map<Suit, Card>> CACHE = new EnumMap<>(Rank.class);

    static {
        for (Rank rank : Rank.values()) CACHE.put(rank, new EnumMap<>(Suit.class));
    }

    private final Rank rank;
    private final Suit suit;

    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static synchronized Card get(Rank rank, Suit suit) {
        Map<Suit, Card> bySuit = CACHE.get(rank);
        Card card = bySuit.get(suit);
        if (card == null) {
            card = new Card(rank, suit);
            bySuit.put(suit, card);
        }
        return card;
    }

    public Rank getRank() { return rank; }
    public Suit getSuit() { return suit; }

    @Override public String toString() { return rank + " of " + suit; }
}
