# -*- coding: utf-8 -*-
# Qinzhi Peng

import numpy as np
import argparse
import matplotlib.pyplot as plt
parser = argparse.ArgumentParser()
parser.add_argument('formatted_train_input')
parser.add_argument('formatted_validation_input')
parser.add_argument('formatted_test_input')
parser.add_argument('dict_input')
parser.add_argument('train_out')
parser.add_argument('test_out')
parser.add_argument('metrics_out')
parser.add_argument('num_epoch', type=int)

class LoadData():
    def __init__(self, infile):
      self.infile = infile
      
    def load_data(self):
        labels = []
        features_set = []
        with open(self.infile, 'r') as file:
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
    def __init__(self, dict_input, num_epoch, learning_rate):
      self.dict_input = LoadData(dict_input).load_dict()
      self.num_epoch = num_epoch
      self.learning_rate = learning_rate
      
    def sigmoid(self, weight, features):
        phi = np.array(np.exp(np.dot(weight, features)) / (1 + np.exp(np.dot(weight, features))))
        
        return phi
      
    def grad(self, label, features, weight, vector_num):   
        grad_onepoint = - (np.dot(features, (label - self.sigmoid(weight, features))) / vector_num)
        
        return grad_onepoint
       
    def SGD(self, labels, features_set, weight):
        vector_num = np.array(features_set.shape[0])
        nnl = 0

        for i in range(vector_num):

            label = np.array(labels[i])
            features_bias = np.append(1, features_set[i])

            grad_onepoint = self.grad(label, features_bias, weight, vector_num)

            weight = np.array(weight - self.learning_rate * grad_onepoint)     
            nnl +=  (- label * np.dot(weight, features_bias)  + np.log(1 + np.exp(np.dot(weight, features_bias)))) / vector_num   
             
        return weight, nnl
    
    def train(self, labels, features_set):
        weight = np.zeros(features_set[0].shape[0] + 1, float)
        nnls = np.array([])
        for i in range(self.num_epoch):
            weight, nnl = self.SGD(labels, features_set, weight)
            nnls = np.append(nnls, nnl)    
        return weight, nnls
    
    
               
    
def main(args):
    
    model_1 = LogisticRegression(args.dict_input, args.num_epoch, 0.001)
    model_2 = LogisticRegression(args.dict_input, args.num_epoch, 0.01)
    model_3 = LogisticRegression(args.dict_input, args.num_epoch, 0.1)
    
    train_labels, train_features_set = LoadData(args.formatted_train_input).load_data()
    
    # train
    trained_weight_1, trained_nnls_1 = model_1.train(train_labels, train_features_set)
    trained_weight_2, trained_nnls_2 = model_2.train(train_labels, train_features_set)
    trained_weight_3, trained_nnls_3 = model_3.train(train_labels, train_features_set)
    
    # plot

    epoch = np.arange(args.num_epoch)
    plt.title('Model 1 training NNL with different learning rate')
    
    plt.plot(epoch, trained_nnls_1, '-b', label = 'alpha = 0.001')
    plt.plot(epoch, trained_nnls_2, ':r', label = 'alpha = 0.01')
    plt.plot(epoch, trained_nnls_3, '--g', label = 'alpha = 0.1')
    
    plt.xlabel('epochs')
    plt.ylabel('Negative Log Likelihood')
    plt.xlim((0, args.num_epoch))
    plt.ylim((0, 1))
    plt.xticks(np.arange(0, args.num_epoch, args.num_epoch / 10))
    plt.yticks(np.arange(0, 1, 0.1))
    plt.legend()
    plt.show()
    
    
if __name__ == '__main__':
    args = parser.parse_args()
    main(args)
