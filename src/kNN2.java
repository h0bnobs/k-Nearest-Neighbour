import java.util.*;
import java.io.*;

public class kNN2 {
    public static void main(String[] args) throws FileNotFoundException {
        int DATA_SIZE = 200;
        int FEATURE_SIZE = 61;
        //10 = 48%
        //20 = 51.5%
        //30 = 44.5%
        //40 = 48.5%
        //50 = 50%
        //60 = 47.5%
        //FEATURE_SIZE = 61;

        //feature selection
        //cosine similarity
        //different value for k - sorted list
        //minkowski distance

        double[][] train = new double[DATA_SIZE][FEATURE_SIZE];
        double[][] test = new double[DATA_SIZE][FEATURE_SIZE];
        int[] trainLabels = new int[DATA_SIZE];
        int[] testLabels = new int[DATA_SIZE];
        int[] predictedLabels = new int[DATA_SIZE];

        //reading training data
        Scanner trainScanner = new Scanner(new File("train_data.txt"));
        Scanner trainLabelScanner = new Scanner(new File("train_label.txt"));
        for (int i = 0; i < DATA_SIZE; i++) {
            if (trainLabelScanner.hasNextInt()) {
                trainLabels[i] = trainLabelScanner.nextInt();
            }

            for (int j = 0; j < FEATURE_SIZE; j++) {
                if (trainScanner.hasNextDouble()) {
                    train[i][j] = trainScanner.nextDouble();
                }
            }
        }
        trainScanner.close();
        trainLabelScanner.close();

        //reading test data and labels
        Scanner testScanner = new Scanner(new File("test_data.txt"));
        Scanner testLabelScanner = new Scanner(new File("test_label.txt"));
        for (int i = 0; i < DATA_SIZE; i++) {
            if (testLabelScanner.hasNextInt()) {
                testLabels[i] = testLabelScanner.nextInt();
            }

            for (int j = 0; j < FEATURE_SIZE; j++) {
                if (testScanner.hasNextDouble()) {
                    test[i][j] = testScanner.nextDouble();
                }
            }
        }
        testScanner.close();
        testLabelScanner.close();

        //System.out.println(Arrays.deepToString(train));
        //System.out.println("####################################");

        //before i standardise the data i need the mean and standard deviation for each feature
        double[] mean = new double[FEATURE_SIZE];
        double[] standardDev = new double[FEATURE_SIZE];
        for (int j = 0; j < FEATURE_SIZE; j++) {
            double sum = 0.0;
            //this calculates the sum of all values in the j-th column
            for (int i = 0; i < DATA_SIZE; i++) {
                sum = sum + train[i][j];
            }
            //the mean of each feature is the sum of all features / DATA_SIZE
            mean[j] = sum / DATA_SIZE;
            double squared = 0.0;
            //this calculates the sum of squared differences from the mean for the j-th column
            for (int i = 0; i < DATA_SIZE; i++) {
                squared = squared + Math.pow(train[i][j] - mean[j], 2);
            }
            //the standard deviation is the sqrt of the average of squared differences
            standardDev[j] = Math.sqrt(squared / DATA_SIZE);
        }

        //now standardise the train data
        for (int i = 0; i < DATA_SIZE; i++) {
            for (int j = 0; j < FEATURE_SIZE; j++) {
                train[i][j] = (train[i][j] - mean[j]) / standardDev[j];
            }
        }

        //now standardise the test data
        for (int i = 0; i < DATA_SIZE; i++) {
            for (int j = 0; j < FEATURE_SIZE; j++) {
                test[i][j] = (test[i][j] - mean[j]) / standardDev[j];
            }
        }

        //System.out.println(Arrays.deepToString(train));

        //kNN
        int correctPredictions = 0;
        for (int i = 0; i < DATA_SIZE; i++) {
            double minDistance = Double.MAX_VALUE;
            int k = -1;

            for (int j = 0; j < DATA_SIZE; j++) {
                //compute the distance
                //double distance = computeEuclideanDistance(test[i], train[j]);
                double distance = computeManhattanDistance(test[i], train[j]);
                //double distance = computeMinkowskiDistance(test[i], train[j], 1);

                //double similar = computeCosineSimilarity(test[i], train[j]);
                //double distance = 1 - similar;

                //check if the current distance is smaller than the minimum distance
                if (distance < minDistance) {
                    minDistance = distance;
                    k = j;
                }
            }
            //assign the label of the nearest neighbor as the predicted label
            predictedLabels[i] = trainLabels[k];

            //check if the prediction is correct
            if (predictedLabels[i] == testLabels[i]) {
                correctPredictions++;
            }
        }
        writeToOutput(test, predictedLabels);

        //accuracy
        double accuracy = (double) correctPredictions / DATA_SIZE * 100;
        System.out.println("accuracy: " + accuracy + "%");
    }

    //computes the manhattan distance
    private static double computeManhattanDistance(double[] test, double[] train) {
        double distance = 0.0;
        for (int i = 0; i < test.length; i++) {
            distance = distance + Math.abs(test[i] - train[i]);
        }
        return distance;
    }

    //write predicitons to output1.txt
    private static void writeToOutput(double[][] data, int[] predictions)
            throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("output2.txt");
        //int count = 0;
        for (int i = 0; i < data.length; i++) {
            printWriter.print(predictions[i] + " ");
            //count++;
        }
        //printWriter.print("\n" + count);
        printWriter.close();
    }
}
