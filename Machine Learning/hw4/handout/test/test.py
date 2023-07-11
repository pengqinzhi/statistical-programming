# -*- coding: utf-8 -*-
# Qinzhi Peng

import numpy as np
import random

class LoadData():
    def __init__(self, infile):
      self.infile = infile
      
    def load_data(self):
        labels = []
        features_set = []
        with open(self.infile,'r') as file:
            dataset = file.readlines()
        for line in dataset:
            line = line.strip('\n').split("\t")
            labels.append(int(float(line[0])))
            features_set.append(np.array(line[1:], float))
        labels = np.array(labels)
        features_set = np.array(features_set)
        return labels, features_set
    
    def load_dict(self):
        word_dict = {}
        with open(self.infile,'r') as file:
            new_dict = file.readlines() 
        for line in new_dict:
            line = line.strip('\n')
            key, value = line.split(" ")
            word_dict[key] = int(value)
            
        return word_dict
    
class LogisticRegression():
    def __init__(self, infile, dict_input, num_epoch):
      self.labels, self.features_set  = LoadData(infile).load_data()
      self.dict_input = LoadData(dict_input).load_dict()
      self.num_epoch = num_epoch
      self.learning_rate = 0.01
      
    def sigmoid(self, weight, features):
        phi = np.around(np.exp(np.dot(weight, features)) / (1 + np.exp(np.dot(weight, features))), decimals=6)
        
        return phi
      
    def grad(self, label, features, weight):
        
        grad_onepoint = np.zeros(features.shape[0], float)
        for i in range(features.shape[0]):
            grad_onepoint[i] = np.around(- (features[i] * (label - self.sigmoid(weight, features))) / self.features_set.shape[0], decimals=6)
        
        return grad_onepoint
        
    def SGD(self, weight):
        iter_count = 0
        shuffle = np.arange(self.features_set.shape[0])
        random.shuffle(shuffle)
        
        for j in range(len(self.features_set)):
            label = self.labels[shuffle[j]]
            features_bias = np.append(1, self.features_set[shuffle[j]])
            grad_onepoint = self.grad(label, features_bias, weight)
            weight -= np.around(self.learning_rate * grad_onepoint, decimals=6)
        
        return weight
            
    def train(self):
        weight = np.zeros(self.features_set.shape[0] + 1, float)
        for i in range(self.num_epoch):
            weight = self.SGD(weight)
        
        return weight
    
    def predict(self, weight, outfile):
        print(self.labels.shape[0])
        labels_predict = np.zeros(len(self.labels), int)
        with open(outfile, 'w') as f:
            for i in range(len(self.labels)):
                features_bias = np.append(1, self.features_set[i])
                prob = self.sigmoid(weight, features_bias)
                if prob >= 0.5:
                    labels_predict[i] = 1
                f.write('{}\n'.format(labels_predict[i]))
        return labels_predict
        
    def metrics(self, weight):
        labels_predict = self.predict(weight)
        error_num = sum(labels_predict != self.labels)
        error = error_num / len(self.labels)
        
        return error

infile = 'test_output.txt'
dict1 = 'test_dict.txt' 
# outfile = 'test_output'
     
# dict2 = 'test_word2vec.txt'
model = LogisticRegression(infile, dict1, 5)
trained_weight = model.train()
print(trained_weight)

