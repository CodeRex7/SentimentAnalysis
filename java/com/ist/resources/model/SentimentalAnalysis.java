package com.ist.model;

import com.ist.resources.utils.CommonDbVariable;
import com.ist.resources.utils.CommonUtils;
import java.io.Serializable;
import java.lang.String;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author SOUMYAGOURAB.S
 * 
 */
public class SentimentalAnalysis {
   Connection con = (Connection) CommonUtils.database_connect();
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
 
 
public void populateSentiment()
    {       
             try {       
            String query = "SELECT id_campaign,id_campaign_wave,id_response," +
                    "GROUP_CONCAT(IF(txt_question_response='', null, txt_question_response) SEPARATOR '.') AS question_response " +
                    "FROM cmp_response_detail GROUP BY id_campaign,id_campaign_wave,id_response;";
            PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                String id_campaign = rs.getString("id_campaign");
                String id_campaign_wave = rs.getString("id_campaign_wave");
                String id_response = rs.getString("id_response");
                String txt_question_response = rs.getString("question_response");
                System.out.println(txt_question_response);
                SentimentalAnalysis.init();
                int num_sentiment_score;
                if(txt_question_response != null)
                    num_sentiment_score = SentimentalAnalysis.findSentiment(txt_question_response);
                else
                    num_sentiment_score = 2;
                String query1= "UPDATE cmp_response"
                        + " SET num_sentiment_score = " + num_sentiment_score 
                        + " where id_campaign = "+ id_campaign +" AND id_campaign_wave= "+ id_campaign_wave
                        + " AND id_response= "+ id_response +";";
                PreparedStatement st1 = (PreparedStatement) con.prepareStatement(query1);
                int i = st1.executeUpdate(); 
                st1.close();
            }
            rs.close();
            st.close();
            //con.close();
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        
    }
}
