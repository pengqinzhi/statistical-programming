# -*- coding: utf-8 -*-
# Qinzhi Peng

import numpy as np
import argparse
import datetime
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
      
    def sigmoid(self, weights, features):
        phi = np.array(np.exp(np.dot(weights, features.T)) / (1 + np.exp(np.dot(weights.T, features.T))))
        return phi
      
    def grad(self, labels, features, weights, vector_num):   
        grad_onepoint = - (np.dot(features, (labels - self.sigmoid(weights, features)).T) / vector_num)
        return grad_onepoint
        
    def SGD(self, labels, features_bias_set, weights):
        vector_num = np.array(features_bias_set.shape[0])
        for i in range(vector_num):
            label = np.array(labels[i])
            grad_onepoint = self.grad(label, features_bias_set[i], weights, vector_num)
            weights = np.array(weights - self.learning_rate * grad_onepoint)
            
        return weights
            
    def train(self, labels, features_set):
        vector_num = np.array(features_set.shape[0])
        feature_num = np.array(features_set[0].shape[0])
        
        weights = np.zeros(feature_num + 1, float)    
        weights_set = np.zeros((self.num_epoch, feature_num + 1), float)
        
        bias_set = np.ones((1, vector_num), float)  
        features_bias_set = np.concatenate((bias_set.T, features_set), axis = 1) 
        
        for i in range(self.num_epoch):
            weights = self.SGD(labels, features_bias_set, weights) 
            weights_set[i, :] = weights
            
        return weights_set
    
    def predict(self, labels, features_set, weights, outfile = None):

        labels_predict = np.zeros(labels.shape[0], int)
        if outfile != None:
            with open(outfile, 'w') as f:
                for i in range(labels.shape[0]):
                    features_bias = np.append(1, features_set[i])
                    prob = self.sigmoid(weights, features_bias)
                    if prob >= 0.5:
                        labels_predict[i] = 1              
                    f.write('{}\n'.format(labels_predict[i]))
                    
        return labels_predict
        
    def metrics(self, labels_predict, label_true):       
        error_num = sum(labels_predict != label_true)
        error = error_num / label_true.shape[0]
        
        return error
    
    def plot(self, labels, features_set, weights_set, style, plot_label):
        vector_num = np.array(features_set.shape[0])
        feature_num = np.array(features_set[0].shape[0])
        bias_set = np.ones((1, vector_num), float)  
        features_bias_set = np.concatenate((bias_set.T, features_set), axis = 1)
        
        epoch = np.arange(self.num_epoch)
        nnls = - np.dot(labels, np.dot(weights_set, features_bias_set.T).T) / vector_num + np.mean(np.log2(1 + np.exp(np.dot(weights_set, features_bias_set.T))), axis=1)   
        plt.plot(epoch, nnls, style, label = plot_label)
        
        return nnls
            
            
def main(args):
    
    
    
    train_labels, train_features_set = LoadData(args.formatted_train_input).load_data()
    valid_labels, valid_features_set = LoadData(args.formatted_validation_input).load_data()
    test_labels, test_features_set = LoadData(args.formatted_test_input).load_data()
    
    # train
    model = LogisticRegression(args.dict_input, args.num_epoch, 0.01)
    trained_weights_set = model.train(train_labels, train_features_set)
    trained_weights = trained_weights_set[-1]
    
    # # predict
    # train_predict = model.predict(train_labels, train_features_set, trained_weights, args.train_out)
    # test_predict = model.predict(test_labels, test_features_set, trained_weights, args.test_out)
    
    # # metrics
    # error_train = model.metrics(train_predict, train_labels)
    # error_test = model.metrics(test_predict, test_labels)
    # with open(args.metrics_out, 'w') as f:
    #     f.writelines("error(train): {:.6f}\n".format(error_train))
    #     f.writelines("error(test): {:.6f}".format(error_test))
    
    # plot
    plt.title('training NNL vs validation NNL')
    train_nnls = model.plot(train_labels, train_features_set, trained_weights_set, '-b', label = 'training NNL')
    valid_nnls = model.plot(valid_labels, valid_features_set, trained_weights_set, ':r', label = 'validation NNL')
    
    plt.xlabel('epochs')
    plt.ylabel('Negative Log Likelihood')
    plt.xlim((0, args.num_epoch))
    plt.ylim((0, 1))
    plt.xticks(np.arange(0, args.num_epoch, args.num_epoch / 10))
    plt.yticks(np.arange(0, 1, 0.1))
    plt.legend()
    plt.show()
    
    
    # # plot
    # model2 = LogisticRegression(args.dict_input, args.num_epoch, 0.0001)
    # model3 = LogisticRegression(args.dict_input, args.num_epoch, 0.1)
    
    # trained_weights_set2 = model2.train(train_labels, train_features_set)
    # trained_weights_set3 = model3.train(train_labels, train_features_set)
    
    # plt.title('training NNL with different learning rate')
    # train_nnls2 = model2.plot(valid_labels, valid_features_set, trained_weights_set2, style = ':r', plot_label = 'alpha = 0.0001')
    # train_nnls = model.plot(train_labels, train_features_set, trained_weights_set, style = '-b', plot_label = 'alpha = 0.01')
    # train_nnls3 = model3.plot(valid_labels, valid_features_set, trained_weights_set3, style = '--g', plot_label = 'alpha = 0.1')
    
    # plt.xlabel('epochs')
    # plt.ylabel('Negative Log Likelihood')
    # plt.xlim((0, args.num_epoch))
    # plt.ylim((0.5, 1))
    # plt.xticks(np.arange(0, args.num_epoch, args.num_epoch / 10))
    # plt.yticks(np.arange(0.5, 1, 0.1))
    # plt.legend()
    # plt.show()
    
    
if __name__ == '__main__':
    start = datetime.datetime.now()
    args = parser.parse_args()
    main(args)
    end = datetime.datetime.now()
    print((end-start))