package core;

import models.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RePlayerImpl implements RePlayer {
    private Map<String, Track> tracksById;
    private Map<String, List<Track>> albumNamesWithTracks;

    public RePlayerImpl() {
        this.tracksById = new HashMap<>();
        this.albumNamesWithTracks = new HashMap<>();
    }

    @Override
    public void addTrack(Track track, String album) {
        this.tracksById.put(track.getId(), track);
        List<Track> tracks = new ArrayList<>();
        tracks.add(track);
        this.albumNamesWithTracks.putIfAbsent(album, tracks);
    }

    @Override
    public void removeTrack(String trackTitle, String albumName) {

    }

    @Override
    public boolean contains(Track track) {
        return this.tracksById.containsKey(track.getId());
    }

    @Override
    public int size() {
        return this.tracksById.size();
    }

    @Override
    public Track getTrack(String title, String albumName) {
        return null;
    }

    @Override
    public Iterable<Track> getAlbum(String albumName) {
        return null;
    }

    @Override
    public void addToQueue(String trackName, String albumName) {

    }

    @Override
    public Track play() {
        return null;
    }

    @Override
    public Iterable<Track> getTracksInDurationRangeOrderedByDurationThenByPlaysDescending(int lowerBound, int upperBound) {
        return null;
    }

    @Override
    public Iterable<Track> getTracksOrderedByAlbumNameThenByPlaysDescendingThenByDurationDescending() {
        return null;
    }

    @Override
    public Map<String, List<Track>> getDiscography(String artistName) {
        return null;
    }
}
