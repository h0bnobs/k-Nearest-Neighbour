Performs classification using k-nearest neighbour (kNN) on a real-world dataset.
There are 400 patterns from 40 subjects (roughly similar numbers of alcoholics and controls). The 400 pattern is divided into 2 sets:
Training set: 200 patterns (given in train_data.txt file) and 
Test set: 200 patterns (given in test_data.txt file)
Each row in the file consists of 61 feature values representing either an alcoholic or control subject data. The class labels for the training and test 
patterns i.e. either alcoholic or control are given as 0 and 1, respectively.

kNN1.java loads all data, computes Euclidean distance measures of the test and train data to obtain the predicted lables (using k=1) and the classification accuracy.
kNN2.java explores different methods to improve classification accuracy. The two methods that worked were: using Manhattan distance instead of Euclidean distance and standardising the data before performing kNN. 
The methods that i tried and did not work were:
Feature selection - only selecting first 50% or last 50% of features for example, 
Different value for k - with a sorted list, 
Minkowski distance, 
Cosine Similarity.
