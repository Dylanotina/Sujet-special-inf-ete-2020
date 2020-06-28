package dao;


import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import streaming.*;

import static com.mongodb.client.model.Updates.inc;

public class CRUD {
    private MongoCollection<Document> collection;

    public CRUD() {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://didi:Didibasketnba17@portofolio-cluster-tugso.mongodb.net/Sujet_special?retryWrites=true&w=majority");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("Sujet_special");
         collection = database.getCollection("Events");
    }

    public void Create(Event event){
        Bson filters = Filters.eq("repo_id",event.getRepo().getId());
        Bson filtersIdEvent = Filters.eq("id",event.getId());
        if (collection.countDocuments(filters)!= 0 && (collection.countDocuments(filtersIdEvent) == 0)){
        collection.updateOne(filters,inc("count",1));
    }else if (collection.countDocuments(filtersIdEvent) == 0){
        Document doc = new Document("titre",event.getRepo().getName()).append("id",event.getId()).append("type",event.getType())
                .append("repo_id",event.getRepo().getId())
                .append("count",1);
        collection.insertOne(doc);
    }
    }

    public void ReadAll(){
        FindIterable<Document> iter = collection.find().sort(Sorts.ascending("count"));
        for(Document doc :iter){
            System.out.println(doc);
        }

    }

    public void update(){

    }

    public void delete(){
    FindIterable<Document> iterable = collection.find();
    for (Document doc : iterable){
        collection.deleteOne(doc);
    }
    }

}
