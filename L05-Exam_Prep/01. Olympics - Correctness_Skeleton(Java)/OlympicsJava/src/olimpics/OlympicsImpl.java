package olimpics;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OlympicsImpl implements Olympics {

    private Map<Integer, Competitor> competitorMapWithIds;
    private Map<Integer, Competition> competitionMapWithIds;

    public OlympicsImpl() {
        this.competitorMapWithIds = new HashMap<>();
        this.competitionMapWithIds = new HashMap<>();
    }


    @Override
    public void addCompetitor(int id, String name) {
        if (this.competitorMapWithIds.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        Competitor competitor = new Competitor(id, name);
        this.competitorMapWithIds.put(id, competitor);
    }

    @Override
    public void addCompetition(int id, String name, int score) {
        if (this.competitionMapWithIds.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        Competition competition = new Competition(name, id, score);
        this.competitionMapWithIds.put(id, competition);
    }

    @Override
    public void compete(int competitorId, int competitionId) {
        if (!this.competitorMapWithIds.containsKey(competitorId) || !this.competitionMapWithIds.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }

        Competition competition = this.competitionMapWithIds.get(competitionId);
        Competitor competitor = this.competitorMapWithIds.get(competitorId);
        competitor.setTotalScore(competitor.getTotalScore() + (long) competition.getScore());
        competition.addCompetitor(competitor);
        this.competitionMapWithIds.put(competitionId, competition);
    }

    @Override
    public void disqualify(int competitionId, int competitorId) {
        if (!this.competitorMapWithIds.containsKey(competitorId) || !this.competitionMapWithIds.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }

        Competitor competitor = this.competitorMapWithIds.get(competitorId);
        Competition competition = this.competitionMapWithIds.get(competitionId);
        boolean isCompetitorInTheCurrentCompetition = competition.getCompetitors()
                .stream()
                .anyMatch(c -> c.getId() == competitor.getId());

        if (!isCompetitorInTheCurrentCompetition) {
            throw new IllegalArgumentException();
        }

        competition.getCompetitors().remove(competitor);
        competitor.setTotalScore(competitor.getTotalScore() - competition.getScore());
        
    }

    @Override
    public Iterable<Competitor> findCompetitorsInRange(long min, long max) {
        return this.competitorMapWithIds.values()
                .stream()
                .filter(c -> c.getTotalScore() > min && c.getTotalScore() <= max)
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Competitor> getByName(String name) {
        Collection<Competitor> competitors = this.competitorMapWithIds.values();
        if (competitors.stream().noneMatch(c -> c.getName().equals(name))) {
            throw new IllegalArgumentException();
        }

        return competitors.stream()
                .filter(c -> c.getName().equals(name))
                .sorted(Comparator.comparingInt(Competitor::getId))
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Competitor> searchWithNameLength(int minLength, int maxLength) {
        return this.competitorMapWithIds.values()
                .stream()
                .filter(c -> c.getName().length() >= minLength && c.getName().length() <= maxLength)
                .sorted(Comparator.comparingInt(Competitor::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean contains(int competitionId, Competitor comp) {
        if (!this.competitionMapWithIds.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }
        return this.competitionMapWithIds.get(competitionId).getCompetitors().stream().anyMatch(com -> com.getId() == com.getId());
    }

    @Override
    public int competitionsCount() {
        return this.competitionMapWithIds.values().size();
    }

    @Override
    public int competitorsCount() {
        return this.competitorMapWithIds.values().size();
    }

    @Override
    public Competition getCompetition(int id) {
        if (!this.competitionMapWithIds.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return this.competitionMapWithIds.get(id);
    }
}
