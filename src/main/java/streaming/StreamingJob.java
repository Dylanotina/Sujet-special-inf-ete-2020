package streaming;

import com.google.gson.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.*;

public class StreamingJob {
    public static void main(String[] args) throws Exception {

        //  Creation du contexte pour le stream

        SparkConf sparkConf = new SparkConf().setAppName("streaming.StreamingJob").setMaster("local[2]").set("spark.cores.max","2");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(20));
        SparkSession spark = SparkSession.builder().getOrCreate();

        //  Creation de la connexion à la base de données et ajout des adresses http pour l'api Github


        String[] adresses = new String[11];
        for(int i = 0; i<11; i++){
            String addAdresse = "https://api.github.com/events?page="+i;
          adresses[i] = addAdresse;
        }


        //  Reception des données et création d'un tableau d'elements JSON

        JavaReceiverInputDStream<String> test  = ssc.receiverStream(new DataReceiver(adresses));
        JavaDStream<String> newString = test.map(s -> "["+s+"]");
       JavaDStream<JsonArray> newJsonArray = newString.map(new Function<String, JsonArray>() {
            @Override
            public JsonArray call(String s) throws Exception {
                try{
                    JsonParser parser = new JsonParser();
                    JsonElement jsonElement = parser.parse(s);
                    return jsonElement.getAsJsonArray();

                }catch (Exception e){
                    throw new Exception(e);
                }

            }
        });


       //   On convertit le tableau d'elements JSON en Liste d'Events

        JavaDStream<Event> newStreamEvent = newJsonArray.flatMap(new FlatMapFunction<JsonArray, Event>() {
            @Override
            public Iterator<Event> call(JsonArray jsonElements) throws Exception {
                Set<Event> newList = new HashSet<>();
                try {
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonElements.size(); i++) {
                        JsonElement element = jsonElements.get(i);
                        Event newEvent = gson.fromJson(element, Event.class);
                        newList.add(newEvent);
                    }

                } catch (Exception e) {
                    throw new Exception(e);
                }

                return newList.iterator();
            }
        });


        //  On filtre les résultats pour avoir que les "PushEvent"

        String comparaison = "PushEvent";
        JavaDStream<Event> FiteredStreamEvent = newStreamEvent.filter(event -> Objects.equals(comparaison,event.getType()));


        JavaDStream<EventForDF> FinalStream = FiteredStreamEvent.flatMap(new FlatMapFunction<Event, EventForDF>() {
            @Override
            public Iterator<EventForDF> call(Event event) throws Exception {
                Set<EventForDF> newList = new HashSet<>();
                EventForDF newEvent = new EventForDF(event.getId(),event.getType(),event.getRepo().getName(),event.getRepo().getId(),event.getActor().getLogin());
                newList.add(newEvent);
                return newList.iterator();
            }
        });

        //  A partir de chaque Event, on va les créer et les ajouter dans la base de données
        String file ="data";



        FinalStream.foreachRDD(eventJavaRDD -> {
       if (!eventJavaRDD.isEmpty()){
           Dataset<Row> EventDF = spark.createDataFrame(eventJavaRDD,EventForDF.class);
           EventDF.write().mode(SaveMode.Append).format("csv").save(file);
       }
        });


        ssc.start();
        ssc.awaitTerminationOrTimeout(600000);




    }
}

