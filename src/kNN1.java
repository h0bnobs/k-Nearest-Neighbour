import java.util.*;
import java.io.*;

public class kNN1 {
    public static void main(String[] args) throws FileNotFoundException {
        int DATA_SIZE = 200;
        int FEATURE_SIZE = 61;

        double[][] train = new double[DATA_SIZE][FEATURE_SIZE];
        double[][] test = new double[DATA_SIZE][FEATURE_SIZE];
        int[] trainLabels = new int[DATA_SIZE];
        int[] testLabels = new int[DATA_SIZE];
        int[] predictedLabels = new int[DATA_SIZE];

        // Reading training data
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

        // Reading test data and labels
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

        //kNN classification
        int correct = 0;
        double minDistance = 0.0;
        double distance = 0.0;
        double accuracy = 0.0;
        for (int i = 0; i < DATA_SIZE; i++) {
            minDistance = Double.MAX_VALUE;
            int k = 1;

            for (int j = 0; j < DATA_SIZE; j++) {
                //compute the distance
                distance = computeEuclideanDistance(test[i], train[j]);

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
                correct++;
            }
        }
        writeToOutput(test, predictedLabels);

        //accuracy
        accuracy = (double) correct / DATA_SIZE * 100;
        System.out.println("accuracy: " + accuracy + "%");
    }

    //calculates the euclidean distance between the test and train data
    //https://quicklatex.com/cache3/e1/ql_e0fda218012efc6a18af8803026e3ce1_l3.svg
    private static double computeEuclideanDistance(double[] test, double[] train) {
        double distance = 0;
//        for (int i = 0; i < test.length; i++) {
//            double one = Math.pow(test[i] - train[i], 2);
//            double two = Math.pow(train[i] - test[i], 2);
//            distance = distance + Math.sqrt(one + two);
//        }
//        System.out.println("distance 1: " + distance);
//        distance = 0;

        for (int i = 0; i < test.length; i++) {
            distance = distance + Math.pow(test[i] - train[i], 2);
        }

        return Math.sqrt(distance);
    }

    //writes predicitons to output1.txt
    private static void writeToOutput(double[][] data, int[] predictions)
            throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("output1.txt");
        //int count = 0;
        for (int i = 0; i < data.length; i++) {
            printWriter.print(predictions[i] + " ");
            //count++; 
        }
        //printWriter.print("\n" + count);
        printWriter.close();
    }
}
