package main.factories;

import java.util.List;

import main.cards.Avatar;
import main.cards.Creature;
import main.cards.Unit;
import main.excpetions.UnknownEntityTypeException;
import main.modifiers.Modifier;

public class CardFactory {

    public Unit creatuUnit(String entityId, int health, int attack, boolean attackReady, int entityType, List<Modifier> modifierList) {
        if(entityType == 1) {
            return new Creature(entityId, health, attack, attackReady, entityType, modifierList);
        } else if(entityType == 2) {
            return new Avatar(entityId, health, attack, attackReady, entityType, modifierList);
        }

        throw new UnknownEntityTypeException("Encountered an unknown entityType: " + entityType);
    }
}