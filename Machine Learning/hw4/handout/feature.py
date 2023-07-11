# -*- coding: utf-8 -*-
# Qinzhi Peng

import numpy as np
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('train_input')
parser.add_argument('validation_input')
parser.add_argument('test_input')
parser.add_argument('dict_input')
parser.add_argument('formatted_train_out')
parser.add_argument('formatted_validation_out')
parser.add_argument('formatted_test_out')
parser.add_argument('feature_flag', type=int)
parser.add_argument('feature_dictionary_input')

class LoadData():
    def __init__(self, infile):
      self.infile = infile
      
    def load_data(self):
        labels = []
        words_set = []
        with open(self.infile,'r') as file:
            dataset = file.readlines()
        for line in dataset:
            line = line.strip('\n').split("\t")
            labels.append(int(line[0]))
            words_set.append(line[1].split(" "))  
            
        return labels, words_set
            
    def load_dict(self):
        word_dict = {}
        with open(self.infile,'r') as file:
            new_dict = file.readlines() 
        for line in new_dict:
            line = line.strip('\n')
            key, value = line.split(" ")
            word_dict[key] = int(value)
            
        return word_dict
    
    def load_word2vec(self):
        word2vec = {}
        with open(self.infile,'r') as file:
            new_dict = file.readlines()
        for line in new_dict:
            line = line.strip('\n').split("\t")
            key = line[0]
            value = np.array(line[1:], float)
            word2vec[key] = value
            
        return word2vec
        
    
class Model_1:
    def  __init__ (self, infile, word_dict, outfile):
        self.labels, self.words_set  = LoadData(infile).load_data()
        self.word_dict = LoadData(word_dict).load_dict()
        self.outfile = outfile
   
    def feature_extraction(self):
        features_set = []
        for words in self.words_set:  
            features = np.zeros(len(self.word_dict), int)
            for word in words:
                if (word in self.word_dict.keys()):
                    value = self.word_dict[word]
                    features[value] = 1
            features_set.append(features)
            
        return features_set   

    def formatted_output(self):
        features_set = self.feature_extraction()
        with open(self.outfile, 'w') as f: 
            for i in range(len(features_set)):
                output = '{}'.format(self.labels[i])
                features = features_set[i]
                for j in range(len(features)):
                    output += '\t{}'.format(features[j])
                output += '\n'
                f.write(output)      

        
class Model_2:
    def  __init__ (self, infile, word2vec, outfile):
        self.labels, self.words_set  = LoadData(infile).load_data()
        self.word2vec = LoadData(word2vec).load_word2vec()
        self.outfile = outfile
        
    def words_trim(self):
        trimmed_words_set = []
        for words in self.words_set:  
            trimmed_words = []
            for word in words:
                if (word in self.word2vec.keys()):
                    trimmed_words.append(word)
            trimmed_words_set.append(trimmed_words)
            
        return trimmed_words_set
    
    def feature_extraction(self):
        features_set = []
        trimmed_words_set = self.words_trim()
        for words in trimmed_words_set:              
            features = np.zeros(len(next(iter(self.word2vec.values()))), float)       
            for word in words:
                features += self.word2vec[word] 
            features = features / len(words)  ##np.round( , 10)
            features_set.append(features)
          
        return features_set
    
    def formatted_output(self):
        features_set = self.feature_extraction()
        with open(self.outfile, 'w') as f: 
            for i in range(len(features_set)):
                output = '{:.6f}'.format(self.labels[i])
                features = features_set[i]
                for j in range(len(features)):
                    output += '\t{:.6f}'.format(features[j])
                output += '\n'
                f.write(output)
             

def main(args):
    if args.feature_flag == 1:
        model_train = Model_1(args.train_input, args.dict_input, args.formatted_train_out)
        model_valid = Model_1(args.validation_input, args.dict_input, args.formatted_validation_out)
        model_test = Model_1(args.test_input, args.dict_input, args.formatted_test_out)
    
        model_train.formatted_output()
        model_valid.formatted_output()
        model_test.formatted_output()
        
    else:
        model_train = Model_2(args.train_input, args.feature_dictionary_input, args.formatted_train_out)
        model_valid = Model_2(args.validation_input, args.feature_dictionary_input, args.formatted_validation_out)
        model_test = Model_2(args.test_input, args.feature_dictionary_input, args.formatted_test_out)

        model_train.formatted_output()
        model_valid.formatted_output()
        model_test.formatted_output()
        

if __name__ == '__main__':
    args = parser.parse_args()
    main(args)