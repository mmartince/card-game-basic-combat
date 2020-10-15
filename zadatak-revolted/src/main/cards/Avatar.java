package main.cards;

import java.util.List;

import main.modifiers.Modifier;

public class Avatar extends Unit {

    public Avatar() {};
    
    public Avatar(String entityId, int health, int attack, boolean attackReady, int entityType, List<Modifier> modifiers) {
        super(entityId, health, attack, attackReady, entityType, modifiers);
    }

    @Override
    public int action(Card target) {
        return this.initiateAttack((Unit) target);
    }
}