package kafka.utility;

import kafka.model.Tweet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TweetFilter {

    /**
     * Filters a list of tweets by their locations.
     *
     * @param tweets    the list of tweets to be filtered.
     * @param locations the locations.
     * @return the list of tweets filtered my locations.
     */
    public static List<Tweet> filterByLocations(List<Tweet> tweets, List<String> locations) {
        if (locations.isEmpty()) {
            return tweets;
        }
        return tweets.stream()
                .filter(t -> locations.stream()
                        .anyMatch(loc -> t.getLocation().equals(loc)))
                .collect(Collectors.toList());
    }

    /**
     * Filters a list of tweets by their tags.
     *
     * @param tweets the list of tweets to be filtered.
     * @param tags   the tags.
     * @return the list of tweets filtered my tags.
     */
    public static List<Tweet> filterByTags(List<Tweet> tweets, List<String> tags) {
        if (!tags.isEmpty())
            return tweets.stream()
                    .filter(t -> tags.stream()
                            .anyMatch(tag -> t.getTags().stream()
                                    .anyMatch(ttag -> ttag.equals(tag))))
                    .collect(Collectors.toList());
        return tweets;
    }

    /**
     * Filters a list of tweets by their mentions.
     *
     * @param tweets   the list of tweets to be filtered.
     * @param mentions the mentions.
     * @return the list of tweets filtered my mentions.
     */
    public static List<Tweet> filterByMentions(List<Tweet> tweets, List<String> mentions) {
        if (!mentions.isEmpty())
            return tweets.stream()
                    .filter(t -> mentions.stream()
                            .anyMatch(mention -> t.getMentions().stream()
                                    .anyMatch(tmention -> tmention.equals(mention))))
                    .collect(Collectors.toList());
        return tweets;
    }


    public static List<Tweet> sort1(List<Tweet> list1, List<Tweet> list2) {
        list1.addAll(list2);

        list1.sort(Comparator.comparingLong((Tweet t) -> Long.parseLong(t.getTimestamp())));
        return list1;
    }

    public static List<Tweet> sort(List<Tweet> list1, List<Tweet> list2) {
        List<Tweet> t1;
        List<Tweet> t2;
        List<Tweet> result = new ArrayList<>();

        //Check that the lists are not empty
        if (list1.isEmpty() && list2.isEmpty())
            return result;
        if (list1.isEmpty())
            return list2;
        if (list2.isEmpty())
            return list1;

        if (list1.size() > list2.size()) {
            t1 = list1;
            t2 = list2;
        } else {
            t2 = list1;
            t1 = list2;
        }

        Long upperBound;
        Long currentValue = Long.parseLong(t2.get(0).getTimestamp());

        for (Tweet tweet : t1) {
            upperBound = Long.parseLong(tweet.getTimestamp());

            if (!t2.isEmpty() && upperBound > currentValue) {
                result.add(t2.get(0));
                t2.remove(0);
                currentValue = Long.parseLong(t2.get(0).getTimestamp());
            }

            result.add(tweet);

        }

        if (!t2.isEmpty()) {
            result.addAll(t2);
            t2.clear();
        }

        return result;
    }
}
