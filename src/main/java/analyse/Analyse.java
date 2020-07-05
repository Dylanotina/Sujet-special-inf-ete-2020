package analyse;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Analyse {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().master("local").appName("Analyse").getOrCreate();
        spark.sparkContext().setLogLevel("OFF");
         Dataset<Row>  df =spark.read().format("csv").option("headers","true").load("/Users/aboudramanetraore/IdeaProjects/Sujet-special-inf-ete-2020/data/*.csv");
        Dataset<Row> show = df.dropDuplicates("_c0").orderBy("_c2");
        show.createOrReplaceTempView("Events");
        System.out.println(show.count());
        Dataset<Row> sqlDF = spark.sql("SELECT _c1, _c3, _c4, COUNT (_c0) FROM Events GROUP BY _c3, _c4, _c1 HAVING COUNT(_c3)>0 ORDER BY 4 DESC ");
        sqlDF.show(50);

    }
}
