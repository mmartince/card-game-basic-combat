package main.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Card {

    String entityId;

    int entityType;

    public Card() {};

    public Card(String entityId, int entityType) {
        this.entityId = entityId;
        this.entityType = entityType;
    }

    public abstract int action(Card target);

    @JsonIgnore
    public boolean isCreature() {
        if(this.entityType == 1) {
            return true;
        }

        return false;
    }

    @JsonIgnore
    public boolean isAvatar() {
        if(this.entityType == 2) {
            return true;
        }

        return false;
    }

    @JsonIgnore
    public boolean isUnit() {
        if(this.isCreature() || this.isAvatar()) {
            return true;
        }

        return false;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }
}