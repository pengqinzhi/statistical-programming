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
    def __init__(self, dict_input, num_epoch):
      self.dict_input = LoadData(dict_input).load_dict()
      self.num_epoch = num_epoch
      self.learning_rate = 0.01
      
    def sigmoid(self, weight, features):
        phi = np.array(np.exp(np.dot(weight, features)) / (1 + np.exp(np.dot(weight, features))))
        
        return phi
      
    def grad(self, label, features, weight, vector_num):   
        grad_onepoint = - (np.dot(features, (label - self.sigmoid(weight, features))) / vector_num)
        
        return grad_onepoint
       
    def SGD(self, labels, features_set, weight, labels2, features_set2):
        vector_num = np.array(features_set.shape[0])
        vector_num2 = np.array(features_set2.shape[0])
        nnl = 0
        nnl2  = 0

        for i in range(vector_num):

            label = np.array(labels[i])
            features_bias = np.append(1, features_set[i])

            grad_onepoint = self.grad(label, features_bias, weight, vector_num)

            weight = np.array(weight - self.learning_rate * grad_onepoint)
                  
            nnl +=  (- label * np.dot(weight, features_bias)  + np.log(1 + np.exp(np.dot(weight, features_bias)))) / vector_num
            if i < vector_num2:
                label2 = np.array(labels2[i])
                features_bias2 = np.append(1, features_set2[i])
                nnl2 += (- label2 * np.dot(weight, features_bias2.T)  + np.log(1 + np.exp(np.dot(weight, features_bias2.T)))) / vector_num2
             
        return weight, nnl, nnl2
            
    def train(self, labels, features_set, labels2, features_set2):
        weight = np.zeros(features_set[0].shape[0] + 1, float)
        nnls = np.array([])
        nnls2 = np.array([])
        for i in range(self.num_epoch):
            weight, nnl, nnl2 = self.SGD(labels, features_set, weight, labels2, features_set2)
            nnls = np.append(nnls, nnl)   
            nnls2 = np.append(nnls2, nnl2)  
        return weight, nnls, nnls2
    
    def predict(self, labels, features_set, weight, outfile):

        labels_predict = np.zeros(labels.shape[0], int)
        if outfile != None:
            with open(outfile, 'w') as f:
                for i in range(labels.shape[0]):
                    features_bias = np.append(1, features_set[i])
                    prob = self.sigmoid(weight, features_bias)
                    if prob >= 0.5:
                        labels_predict[i] = 1              
                    f.write('{}\n'.format(labels_predict[i]))
                    
        return labels_predict
        
    def metrics(self, labels_predict, label_true):
        error_num = sum(labels_predict != label_true)
        error = error_num / label_true.shape[0]
        
        return error
    
               
    
def main(args):
    
    model = LogisticRegression(args.dict_input, args.num_epoch)
    train_labels, train_features_set = LoadData(args.formatted_train_input).load_data()
    valid_labels, valid_features_set = LoadData(args.formatted_validation_input).load_data()
    
    # train
    trained_weight, trained_nnls, valid_nnls = model.train(train_labels, train_features_set, valid_labels, valid_features_set)
    
    # plot

    epoch = np.arange(args.num_epoch)
    plt.title('training NNL vs validation NNL')
    
    plt.plot(epoch, trained_nnls, '-b', label = 'training NNL')
    plt.plot(epoch, valid_nnls, ':r', label = 'validation NNL')
    
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
