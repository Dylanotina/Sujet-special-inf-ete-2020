import com.google.gson.*;
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
        SparkConf sparkConf = new SparkConf().setAppName("StreamingJob").setMaster("local");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));

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
                List<Event> newList = new ArrayList<>();
                try {
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonElements.size(); i++) {
                        JsonElement element = jsonElements.get(i);
                        Event newEvent = gson.fromJson(element, Event.class);
                        //System.out.println(newEvent);
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
            }
        });

        ssc.start();
        ssc.awaitTerminationOrTimeout(5000);
        ssc.close();
    }
}

