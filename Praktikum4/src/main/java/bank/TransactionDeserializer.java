package bank;
import com.google.gson.*;
import java.lang.reflect.Type;

public class TransactionDeserializer implements JsonDeserializer<Transaction> {
    @Override
    public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String className = jsonObject.getAsJsonPrimitive("CLASSNAME").getAsString();

        JsonElement instanceJson = jsonObject.get("INSTANCE");

        switch (className) {
            case "IncomingTransfer":
                return context.deserialize(instanceJson, IncomingTransfer.class);
            case "OutgoingTransfer":
                return context.deserialize(instanceJson, OutgoingTransfer.class);
            case "Payment":
                return context.deserialize(instanceJson, Payment.class);
            default:
                throw new JsonParseException("Unknown class: " + className);
        }
    }
}
