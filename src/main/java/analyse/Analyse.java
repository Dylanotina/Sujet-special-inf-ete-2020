package analyse;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Analyse {
    public static void main(String[] args) {
        //SparkConf sparkConf = new SparkConf().setAppName("streaming.StreamingJob").setMaster("local[2]").set("spark.cores.max","2");
        SparkSession spark = SparkSession.builder().master("local").appName("Analyse").getOrCreate();
         Dataset<Row>  df =spark.read().format("csv").option("headers","true").load("/Users/aboudramanetraore/IdeaProjects/Sujet-special-inf-ete-2020/data.csv/*.csv");
        df.createOrReplaceTempView("Events");
       //df.show();
        Dataset<Row> sqlDF = spark.sql("SELECT _c1, _c3, _c4, COUNT (_c0) FROM Events GROUP BY _c3, _c4, _c1 HAVING COUNT(_c3)>0");
        sqlDF.show();

    }
}
