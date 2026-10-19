import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Compares lazy card creation with an eagerly preinitialized pool. */
public final class Main {
    private static final int CARD_COUNT = Rank.values().length * Suit.values().length;

    private Main() {
    }

    public static void main(String[] args) {
        String mode = args.length == 0 ? "lazy" : args[0].toLowerCase(Locale.ROOT);
        if ("lazy".equals(mode)) {
            runLazy();
        } else if ("eager".equals(mode)) {
            runEager();
        } else {
            throw new IllegalArgumentException("Choose either 'lazy' or 'eager'.");
        }
    }

    private static void runLazy() {
        List<Card> cards = new ArrayList<>(CARD_COUNT);
        long start = System.currentTimeMillis();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(Card.get(rank, suit));
            }
        }
        long elapsed = System.currentTimeMillis() - start;

        printLazyChecks(cards);
        System.out.println("Mode: lazy");
        System.out.println("Cards obtained: " + cards.size());
        System.out.println("Lazy obtain-all time: " + elapsed + " ms");
    }

    private static void runEager() {
        long initializationStart = System.currentTimeMillis();
        EagerCard.initialize();
        long initializationElapsed = System.currentTimeMillis() - initializationStart;

        List<EagerCard> cards = new ArrayList<>(CARD_COUNT);
        long lookupStart = System.currentTimeMillis();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(EagerCard.get(rank, suit));
            }
        }
        long lookupElapsed = System.currentTimeMillis() - lookupStart;

        printEagerChecks(cards);
        System.out.println("Mode: eager");
        System.out.println("Cards obtained: " + cards.size());
        System.out.println("Eager pre-initialization time: " + initializationElapsed + " ms");
        System.out.println("Eager obtain-all lookup time: " + lookupElapsed + " ms");
        System.out.println("Eager total including initialization: "
                + (initializationElapsed + lookupElapsed) + " ms");
    }

    private static void printLazyChecks(List<Card> cards) {
        Card first = Card.get(Rank.ACE, Suit.SPADES);
        Card second = Card.get(Rank.ACE, Suit.SPADES);
        boolean sameInstance = first == second;
        boolean allUnique = cards.stream().distinct().count() == CARD_COUNT;

        System.out.println("Repeated lookup returns same object: " + sameInstance);
        System.out.println(CARD_COUNT + " distinct rank/suit combinations: " + allUnique);
    }

    private static void printEagerChecks(List<EagerCard> cards) {
        boolean sameInstance = EagerCard.get(Rank.ACE, Suit.SPADES)
                == EagerCard.get(Rank.ACE, Suit.SPADES);
        boolean allUnique = cards.stream().distinct().count() == CARD_COUNT;

        System.out.println("Repeated lookup returns same object: " + sameInstance);
        System.out.println(CARD_COUNT + " distinct rank/suit combinations: " + allUnique);
    }
}
