package com.example;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class RecommendationEngine {
    public static void main(String[] args) {
        try {
            // Load data from CSV
            DataModel model = new FileDataModel(new File("src/main/resources/user_preferences.csv"));

            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);

            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            List<RecommendedItem> recommendations = recommender.recommend(1, 3);


            System.out.println("Recommended Products for User 1:");
            for (RecommendedItem item : recommendations) {
                System.out.println("Item: " + item.getItemID() + " | Score: " + item.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}