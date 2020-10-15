package main.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import main.cards.Card;
import main.modifiers.Modifier;
import main.factories.CardFactory;

public class ItemDeserializer extends StdDeserializer<Card> {

    private static final long serialVersionUID = 7550063094545991363L;

    public ItemDeserializer() {
        this(null); 
    } 
 
    public ItemDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Card deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        String entityId = node.get("entityId").asText();
        int health = node.get("health").intValue();
        int attack = node.get("attack").intValue();
        boolean attackReady = node.get("attackReady").booleanValue();
        int entityType = node.get("entityType").intValue();
        ArrayNode modifiersArray = (ArrayNode) node.get("modifiers");

        List<Modifier> modifierList = new ArrayList<>();

        for (JsonNode modifiersNode : modifiersArray) {
            int modifierType = modifiersNode.get("modifierType").intValue();
            int value = modifiersNode.get("value").intValue();

            modifierList.add(new Modifier(modifierType, value));
        }

        CardFactory cardfactory = new CardFactory();

        return cardfactory.creatuUnit(entityId, health, attack, attackReady, entityType, modifierList);
    }
}