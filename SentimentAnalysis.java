import java.util.ArrayList;
 
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.Properties;
import org.ejml.simple.SimpleMatrix;
/**
 *
 * @author SoumyaGourab
 */
 
 
 
 
public class SentimentAnalysis {
static StanfordCoreNLP pipeline;
 
public static void init() {
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
    pipeline = new StanfordCoreNLP(props);
}
 
public static int findSentiment(String tweet) {
 
    int mainSentiment = 0;
    if (tweet != null && tweet.length() > 0) {
        int longest = 0;
        Annotation annotation = pipeline.process(tweet);
        for (CoreMap sentence : annotation
                .get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence
                    .get(SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);          
            String partText = sentence.toString();
            if (partText.length() > longest) {
                mainSentiment = sentiment;
                longest = partText.length();
            }
        }
    }
    return mainSentiment;
    }
 
 
public static void main(String[] args) 
    {
        ArrayList<String> tweets = new ArrayList<String>();
        tweets.add("my name is Soumya Gourab. I travelled in air india. The food was really nice but the staff were not at all polite. I would never fly in this airplane anymore.");
        SentimentAnalysis.init();
        for(String tweet : tweets) {
            System.out.println(tweet + " : " + SentimentAnalysis.findSentiment(tweet));
        }
    }
}