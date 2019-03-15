package kafka.rest;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static spark.Spark.*;

/**
 * Rest interface of TwitterRest
 */
public class TwitterRest {

    private static Logger logger = LoggerFactory.getLogger(TwitterRest.class);
    private static Gson gson = new Gson();

    public static void main(String[] args) {

        path("/api", () -> {
            before("/*", (q, a) -> logger.info("Received api call"));

            TweetRoute.configureRoutes();
            UserRoute.configureRoutes();
            SubscriptionRoute.configureRoutes();
        });
    }

}
