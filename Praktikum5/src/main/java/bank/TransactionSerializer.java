package bank;

import com.google.gson.*;
import java.lang.reflect.Type;



public class TransactionSerializer implements JsonSerializer<Transaction> {
    @Override
    public JsonElement serialize(Transaction transaction, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("CLASSNAME", transaction.getClass().getSimpleName());
        obj.add("INSTANCE", context.serialize(transaction));


        return obj;
    }

}

