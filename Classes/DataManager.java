package Classes;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import Classes.Accounts.Account;
import Classes.Clients.Client;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

import Classes.Clients.IndividualClient;
import Classes.Clients.StudentClient;
import Classes.Clients.CorporateClient;
import Classes.Clients.VIPClient;

public class DataManager
{
    private static final String FILE_PATH = "clients.json";
    private static Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Client.class, (JsonDeserializer<Client>) (json, type, ctx) -> {
        String clientType = json.getAsJsonObject().get("type").getAsString();
        switch (clientType) {
            case "IndividualClient": return ctx.deserialize(json, IndividualClient.class);
            case "StudentClient":    return ctx.deserialize(json, StudentClient.class);
            case "CorporateClient":  return ctx.deserialize(json, CorporateClient.class);
            case "VIPClient":        return ctx.deserialize(json, VIPClient.class);
            default: throw new JsonParseException("Unknown client type: " + clientType);
        }
    }).create();

    public static void saveClients(ArrayList<Client> clients){
        try(Writer writer = new FileWriter(FILE_PATH)){
            gson.toJson(clients, writer);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public static ArrayList<Client> loadClients(){
        try(Reader reader = new FileReader(FILE_PATH)){
            Type clientListType = new TypeToken<ArrayList<Client>>(){}.getType();
            return gson.fromJson(reader, clientListType);
        } 
        catch(IOException e){
            return new ArrayList<>();
        }
    }

    public static void linkAccountsToOwners(ArrayList<Client> clients){
        for(Client c : clients){
            for(Account acc : c.accounts){
                acc.owner = c;
            }
        }
    }
}