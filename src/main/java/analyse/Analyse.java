package analyse;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Analyse {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().master("local").appName("Analyse").getOrCreate();
        spark.sparkContext().setLogLevel("OFF");
         Dataset<Row>  df =spark.read().format("csv").option("headers","true").load("data/*.csv");
        Dataset<Row> show = df.dropDuplicates("_c0").orderBy("_c2");
        Dataset<Row> changeNames = show.withColumnRenamed("_c3","titre_repo")
                .withColumnRenamed("_c4","type").withColumnRenamed("_c1","id_repo")
                .withColumnRenamed("_c0","id_event");

        changeNames.createOrReplaceTempView("Events");
        Dataset<Row> sqlDF = spark.sql("SELECT id_repo, titre_repo, type, COUNT (id_event) FROM Events GROUP BY titre_repo, type, id_repo HAVING COUNT(titre_repo)>0 ORDER BY 4 DESC ");
        sqlDF.show(false);
        System.out.println("Nombre d'événements récupérées sur 1h: "+sqlDF.count());
        //System.out.println("Nombre d'événements récupérées sur 1h: "+changeNames.count());
    }
}
