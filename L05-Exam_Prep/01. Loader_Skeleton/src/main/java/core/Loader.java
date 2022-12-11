package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Loader implements Buffer {
    private Map<Integer, Entity> entitiesWithIds;

    public Loader() {
        this.entitiesWithIds = new HashMap<>();
    }

    @Override
    public void add(Entity entity) {
        if (this.entitiesWithIds.containsKey(entity.getId())) {
            throw new IllegalArgumentException();
        }

        this.entitiesWithIds.put(entity.getId(), entity);
    }

    @Override
    public Entity extract(int id) {
        if (!this.entitiesWithIds.containsKey(id)) {
            return null;
        }

        return this.entitiesWithIds.remove(id);
    }

    @Override
    public Entity find(Entity entity) {
        if (!this.entitiesWithIds.containsKey(entity.getId())) {
            return null;
        }

        return this.entitiesWithIds.get(entity.getId());
    }

    @Override
    public boolean contains(Entity entity) {
        return this.entitiesWithIds.containsKey(entity.getId());
    }

    @Override
    public int entitiesCount() {
        return this.entitiesWithIds.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        if (!contains(oldEntity)) {
            throw new IllegalStateException("Entity not found");
        }

        this.entitiesWithIds.remove(oldEntity.getId());
        this.entitiesWithIds.put(newEntity.getId(), newEntity);

    }

    @Override
    public void swap(Entity first, Entity second) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Entity[] toArray() {
        return new Entity[0];
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        return null;
    }

    @Override
    public List<Entity> getAll() {
        return null;
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {

    }

    @Override
    public void removeSold() {

    }

    @Override
    public Iterator<Entity> iterator() {
        return null;
    }
}
