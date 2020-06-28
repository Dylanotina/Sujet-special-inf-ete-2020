package streaming;

import com.google.gson.*;
import dao.CRUD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;


import java.util.*;

public class StreamingJob {
    public static void main(String[] args) throws Exception {
        SparkConf sparkConf = new SparkConf().setAppName("streaming.StreamingJob").setMaster("local");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(5));

        CRUD crud = new CRUD();
        String[] adresses = new String[11];


        for(int i = 0; i<11; i++){
            String addAdresse = "https://api.github.com/events?page="+i;
          adresses[i] = addAdresse;
        }

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

        String comparaison = "PushEvent";
        JavaDStream<Event> FiteredStreamEvent = newStreamEvent.filter(event -> Objects.equals(comparaison,event.getType()));


        FiteredStreamEvent.foreachRDD(eventJavaRDD -> {
            if(!eventJavaRDD.isEmpty()){
                eventJavaRDD.collect().forEach(System.out::println);
                eventJavaRDD.collect().forEach(crud::Create);

            }
        });



        ssc.start();
        ssc.awaitTerminationOrTimeout(3600000);
        ssc.close();
    }
}

