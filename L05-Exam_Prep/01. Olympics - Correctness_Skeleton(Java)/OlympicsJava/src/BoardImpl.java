import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardImpl implements Board {
    private Map<String, Card> cardMapWithNames;
    public BoardImpl() {
        this.cardMapWithNames = new HashMap<>();
    }

    @Override
    public void draw(Card card) {
        if (this.cardMapWithNames.containsKey(card.getName())) {
            throw new IllegalArgumentException();
        }

        this.cardMapWithNames.put(card.getName(), card);
    }

    @Override
    public Boolean contains(String name) {
        if (!this.cardMapWithNames.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        return this.cardMapWithNames.containsKey(name);
    }

    @Override
    public int count() {
        return this.cardMapWithNames.values().size();
    }

    @Override
    public void play(String attackerCardName, String attackedCardName) {
        if (!this.cardMapWithNames.containsKey(attackerCardName) || !this.cardMapWithNames.containsKey(attackedCardName)) {
            throw new IllegalArgumentException();
        }
        Card attacker = this.cardMapWithNames.get(attackerCardName);
        Card defender = this.cardMapWithNames.get(attackedCardName);

        if (attacker.getLevel() != defender.getLevel()) {
            throw new IllegalArgumentException();
        }

        if (defender.getHealth() <= 0) {
            return;
        }
        int damage = attacker.getDamage();
        defender.setHealth(defender.getHealth() - damage);

        if (defender.getHealth() <= 0) {
            attacker.setScore(attacker.getScore() + defender.getLevel());
        }
    }

    @Override
    public void remove(String name) {
        if (!this.cardMapWithNames.containsKey(name)) {
            throw new IllegalArgumentException();
        }

        this.cardMapWithNames.remove(name);
    }

    @Override
    public void removeDeath() {
        List<Card> cards = this.cardMapWithNames.values().stream().filter(card -> card.getHealth() <= 0).collect(Collectors.toList());
        for (Card card : cards) {
            this.cardMapWithNames.remove(card.getName());
        }
    }

    @Override
    public Iterable<Card> getBestInRange(int start, int end) {
        return this.cardMapWithNames.values()
                .stream()
                .filter(card -> card.getScore() >= start && card.getScore() <= end)
                .sorted((c1, c2) -> Integer.compare(c2.getLevel(), c1.getLevel()))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Card> listCardsByPrefix(String prefix) {
        return this.cardMapWithNames.values()
                .stream()
                .filter(card -> card.getName().startsWith(prefix))
                .sorted((c1, c2) -> {
                    String c1Name = c1.getName();
                    String c2Name = c2.getName();
                    int c1NameASCII = 0;
                    int c2NameASCII = 0;

                    for (int i = 0; i < c1Name.length(); i++) {
                        int ascii = c1Name.charAt(i);
                        c1NameASCII += ascii;
                    }

                    for (int i = 0; i < c2Name.length(); i++) {
                        int ascii = c2Name.charAt(i);
                        c2NameASCII += ascii;
                    }

                    if (c1NameASCII == c2NameASCII) {
                        return Integer.compare(c1.getLevel(), c2.getLevel());
                    }

                    return Integer.compare(c1NameASCII, c2NameASCII);
                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Card> searchByLevel(int level) {
        return this.cardMapWithNames.values().stream()
                .filter(card ->
                        card.getLevel() == level
                ).sorted((c1, c2) -> Integer.compare(c2.getScore(), c1.getScore())).collect(Collectors.toList());
    }

    @Override
    public void heal(int health) {
        Card card = this.cardMapWithNames
                .values()
                .stream()
                .min(Comparator.comparingInt(Card::getHealth))
                .orElseThrow(IllegalArgumentException::new);

        card.setHealth(card.getHealth() + health);
        this.cardMapWithNames.put(card.getName(), card);
    }
}
