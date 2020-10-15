package main.cards;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import main.modifiers.Modifier;

public abstract class Unit extends Card {

    int health;

    int attack;

    boolean attackReady;
    
    List<Modifier> modifiers;

    public Unit() {};

    public Unit(String entityId, int health, int attack, boolean attackReady, int entityType, List<Modifier> modifiers) {
        super(entityId, entityType);
        this.health = health;
        this.attack = attack;
        this.attackReady = attackReady;
        this.modifiers = modifiers;
    }

    public void readyUp() {
        this.attackReady = true;
    }

	public int initiateAttack(Unit attacked) {
        if(!isValidAttack(attacked)) {
            return -1;
        }

        //Original attack
        int damage = this.dealDamage(this, attacked);
        this.attackReady = false;
        //Retaliate
        //Can creatures retaliate against entities?
        System.out.println(attacked.getEntityId() + " retaliating");
        this.dealDamage(attacked, this);

        return damage;
    }

    private boolean isValidAttack(Unit attacked) {
        if(!attackReady) {
            System.out.println("Entity is not ready to attack");
            return false;
        } else if(!this.isAlive() || !attacked.isAlive()) {
            System.out.println("Dead entities can't be interacted with");
            return false;
        } else if(this.entityId.equals(attacked.getEntityId())) {
            System.out.println("Entity can't attack itself");
            return false;
        }

        return true;
    }

    private int dealDamage(Unit attacker, Unit attacked) {
        int totalDamage = attacker.getAttack();

        for(Modifier modifier : attacked.getModifiers()) {
            totalDamage = modifier.applyModifier(totalDamage);
        }
        
        if(totalDamage < 0) {
            totalDamage = 0;
        }

        attacked.setHealth(attacked.getHealth() - totalDamage);

        System.out.println(attacker.entityId + " dealt " + totalDamage + " damage to " + attacked.getEntityId() + ", " + attacked.entityId + " health: " + attacked.getHealth());

        return totalDamage;
    }

    @JsonIgnore
    public boolean isAlive() {
        if(this.health > 0) {
            return true;
        }
        
        return false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isAttackReady() {
        return attackReady;
    }

    public void setAttackReady(boolean attackReady) {
        this.attackReady = attackReady;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }
}