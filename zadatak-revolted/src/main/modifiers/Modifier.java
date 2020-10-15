package main.modifiers;

public class Modifier {

    private int modifierType;

    private int value;

    public Modifier() {};

    public Modifier(int modifierType, int value) {
        this.modifierType = modifierType;
        this.value = value;
    }

    public int applyModifier(int totalDamage) {
        switch(this.modifierType) {
            case 1:
                //Armour is permanent?
                return totalDamage - value;
            case 2:
                return totalDamage + value;
        }

        return totalDamage;
    }

    public int getModifierType() {
        return modifierType;
    }

    public void setModifierType(int modifierType) {
        this.modifierType = modifierType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}