package launcher;

import analyse.Analyse;
import streaming.StreamingJob;

public class Launcher {
    public static void main(String[] args) throws Exception {
        StreamingJob.main(args);
        Analyse.main(args);
    }
}
