package core;

import models.Doodle;

public class DoodleSearchImpl implements DoodleSearch {
    @Override
    public void addDoodle(Doodle doodle) {

    }

    @Override
    public void removeDoodle(String doodleId) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(Doodle doodle) {
        return false;
    }

    @Override
    public Doodle getDoodle(String id) {
        return null;
    }

    @Override
    public double getTotalRevenueFromDoodleAds() {
        return 0;
    }

    @Override
    public void visitDoodle(String title) {

    }

    @Override
    public Iterable<Doodle> searchDoodles(String searchQuery) {
        return null;
    }

    @Override
    public Iterable<Doodle> getDoodleAds() {
        return null;
    }

    @Override
    public Iterable<Doodle> getTop3DoodlesByRevenueThenByVisits() {
        return null;
    }
}
