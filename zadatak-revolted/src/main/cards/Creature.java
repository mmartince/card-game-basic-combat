package main.cards;

import java.util.List;

import main.modifiers.Modifier;

public class Creature extends Unit {

    public Creature() {}

    public Creature(String entityId, int health, int attack, boolean attackReady, int entityType, List<Modifier> modifiers) {
        super(entityId, health, attack, attackReady, entityType, modifiers);
    }

    @Override
    public int action(Card target) {
        if(target.isAvatar()) {
            System.out.println(this.getClass().getSimpleName() + " can't attack " + target.getClass().getSimpleName());
            return -1;
        }

        return this.initiateAttack((Unit) target);
    }
}