# -*- coding: utf-8 -*-
# Qinzhi Peng
import numpy as np
import argparse
import random

parser = argparse.ArgumentParser()
parser.add_argument('train_infile')
parser.add_argument('test_infile')
parser.add_argument('split_index', type=int, default=0)
parser.add_argument('train_outfile')
parser.add_argument('test_outfile')
parser.add_argument('metrics_outfile')


def load_data(infile, split_index):
    dataset = np.loadtxt(infile, dtype=str, delimiter='\t', skiprows=1)             
    for i in range(len(dataset)):                 # convert attributes into 0 or 1
        for j in range(len(dataset[0]) - 1):
            if ('n' in dataset[i,j]):
                dataset[i,j] = 0
            else:
                dataset[i,j] = 1
    attribute = dataset[:, split_index]
    true_label = dataset[:, -1]
    return dataset, attribute, true_label


def divide_data(dataset, attribute):
    set0 = dataset[np.where(attribute == '0')]    # attributes are always binary
    set1 = dataset[np.where(attribute == '1')]
    return set0, set1


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
    count2 = sum(label != label1)
    if (count1 > count2):                             # the class y that appears most often in dataset D
        return label1
    elif (count1 < count2):    
        return label2
    else:
        return random.choice([label1, label2])              

        
def train(dataset, attribute):
    train_set0, train_set1 = divide_data(dataset, attribute)
    vote0 = majority_vote(train_set0)
    vote1 = majority_vote(train_set1)
    return vote0, vote1


def hypothesis(vote0, vote1, attribute):
    predict_label = []
    for value in attribute:
        if (value == '0'):
            predict_label.append(vote0)
        else:
            predict_label.append(vote1)
    predict_label = np.array(predict_label)
    return predict_label
  

def metrics(predict_label, true_label):
    error_num = sum(predict_label != true_label)
    error = error_num / len(true_label)
    return error


def main(args):
    #train
    train_dataset, train_attr, train_true = load_data(args.train_infile, args.split_index)
    vote0, vote1 = train(train_dataset, train_attr)
    train_predict = hypothesis(vote0, vote1, train_attr)
    np.savetxt(args.train_outfile, train_predict, fmt='%s', delimiter='\n')
       
    #predict
    test_dataset, test_attr, test_true = load_data(args.test_infile, args.split_index)
    test_predict = hypothesis(vote0, vote1, test_attr)
    np.savetxt(args.test_outfile, test_predict, fmt='%s', delimiter='\n')
    
    #metrics
    train_error = metrics(train_predict, train_true)
    test_error = metrics(test_predict, test_true)
    with open(args.metrics_outfile,'w') as f:   
        f.writelines("error(train): {}\n".format(train_error))
        f.writelines("error(test): {}".format(test_error))


if __name__ == '__main__':
    args = parser.parse_args()
    main(args)