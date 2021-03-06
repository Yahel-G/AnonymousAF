package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static Vector<Thread> Threads = new Vector<>();
    public static TimeService timeService = null;
    public static Vector<APIService> APIServices = null;
    public static Vector<Customer> customersArray = null;
    public static Vector<InventoryService> inventoryServices = null;
    public static Vector<SellingService> sellingServices = null;
    public static Vector<LogisticsService> logisticsServices = null;
    public static Vector<ResourceService> resourceServices = null;
    public static Inventory inventory = null;
    public static ResourcesHolder resourcesHolder = null;
    public static MoneyRegister moneyRegister = null;//MoneyRegister.getInstance();


    public static void main(String[] args) {
        GsonParser();

    }

    private static void GsonParser(){
        JsonParser Parser = new JsonParser();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("input.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = Parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonArray initialInventory = rootObject.getAsJsonArray("initialInventory");
        BookInventoryInfo[] booksInventory = new BookInventoryInfo[initialInventory.size()];
        int index = 0;
        for (JsonElement bookInfo: initialInventory){
            booksInventory[index] = new BookInventoryInfo(bookInfo.getAsJsonObject().getAsJsonPrimitive("bookTitle").getAsString(),
                    bookInfo.getAsJsonObject().getAsJsonPrimitive("amount").getAsInt(),
                    bookInfo.getAsJsonObject().getAsJsonPrimitive("price").getAsInt());
            index++;
        }
        index = 0;
        JsonArray initialResources = rootObject.getAsJsonArray("initialResources");
        DeliveryVehicle[] vehicles = null;
        for (JsonElement item: initialResources){ // in case the input consists of several arrays of vehicles
            JsonArray vehiclesInput = item.getAsJsonObject().getAsJsonArray("vehicles");
            vehicles = new DeliveryVehicle[vehiclesInput.size()];
            for(JsonElement aVhicle: vehiclesInput){
                vehicles[index] = new DeliveryVehicle(aVhicle.getAsJsonObject().getAsJsonPrimitive("license").getAsInt(),
                        aVhicle.getAsJsonObject().getAsJsonPrimitive("speed").getAsInt());
                index++;
            }
        }
        JsonObject servicesInput = rootObject.getAsJsonObject("services");
        JsonObject timeInput = servicesInput.getAsJsonObject("time");
        int timeSpeed = timeInput.get("speed").getAsInt();
        int timeDuration = timeInput.get("duration").getAsInt();
        int numOfSelling = servicesInput.get("selling").getAsInt();
        int numOfInventory = servicesInput.get("inventoryService").getAsInt();
        int numOfLogistics = servicesInput.get("logistics").getAsInt();
        int numOfResources = servicesInput.get("resourcesService").getAsInt();

        JsonArray customersInput = servicesInput.getAsJsonArray("customers");
        HashMap<Customer, List<OrderPair>> customers = new HashMap<>();
        for(JsonElement customer: customersInput){
            Customer tempCust = new Customer(customer.getAsJsonObject().getAsJsonPrimitive("id").getAsInt(),
                    customer.getAsJsonObject().getAsJsonPrimitive("name").getAsString(),
                    customer.getAsJsonObject().getAsJsonPrimitive("address").getAsString(),
                    customer.getAsJsonObject().getAsJsonPrimitive("distance").getAsInt(),
                    customer.getAsJsonObject().getAsJsonObject("creditCard").getAsJsonObject().get("number").getAsInt(),
                    customer.getAsJsonObject().getAsJsonObject("creditCard").getAsJsonObject().get("amount").getAsInt());
            List<OrderPair> tempSchedule = null;
            JsonArray schedules = customer.getAsJsonObject().getAsJsonArray("orderSchedule");
            for(JsonElement schedule: schedules){
                tempSchedule.add(new OrderPair(schedule.getAsJsonObject().get("tick").getAsInt(),
                        schedule.getAsJsonObject().get("bookTitle").getAsString()));
            }
            customers.put(tempCust, tempSchedule);
        }

        inventory.getInstance().load(booksInventory);
        resourcesHolder.getInstance().load(vehicles);
        TimeService Timer = new TimeService(timeSpeed,timeDuration); //BigBen?
        int i;
        for(i = 0 ; i < numOfSelling ; i++){
            sellingServices.add(new SellingService());
        }
        for(i = 0 ; i < numOfInventory ; i++){
            inventoryServices.add(new InventoryService());
        }
        for(i = 0 ; i < numOfLogistics ; i++){
            logisticsServices.add(new LogisticsService());
        }
        for(i = 0 ; i < numOfResources ; i++){
            resourceServices.add(new ResourceService());
        }


    } // end GsonParser
}
