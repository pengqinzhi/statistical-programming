# -*- coding: utf-8 -*-
# Qinzhi Peng
import numpy as np
import argparse
import random

parser = argparse.ArgumentParser()
parser.add_argument('infile')
parser.add_argument('outfile')
      
def majority_vote(dataset):
    count1 = 0
    count2 = 0
    label = dataset[:, -1]
    label1 = dataset[0, -1]
    for value in label:                          # class label is always binary
        if (value != label1):
            label2 = value
            break
    count1 = sum(label == label1)
    count2 = sum(label == label2)
    if (count1 > count2):                             # the class y that appears most often in dataset D
        return label1
    elif (count1 < count2):    
        return label2
    else:
        return random.choice([label1, label2])              

    
def binary_entropy(label):
    count = 0
    label1 = label[0]
    label_num = len(label)
    if label_num <= 1:
        return 0
    for value in label:
        if (value != label1):
            count += 1
    probability = count / label_num
    return - probability * np.log2(probability) - (1 - probability) * np.log2(1 - probability)
    

def inspection(dataset):
    true_label = dataset[:, -1]
    entropy = binary_entropy(true_label)
    vote = majority_vote(dataset)
    error_num = sum(true_label != vote)
    error = error_num / len(dataset)
    return entropy, error

        
if __name__ == '__main__':
    args = parser.parse_args()
    dataset = np.loadtxt(args.infile, dtype=str, delimiter='\t', skiprows=1)
    entropy, error = inspection(dataset)
    with open(args.outfile,'w') as f:   
        f.writelines("entropy: {}\n".format(entropy))
        f.writelines("error: {}".format(error))