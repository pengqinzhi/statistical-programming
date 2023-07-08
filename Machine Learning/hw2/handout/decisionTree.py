
# -*- coding: utf-8 -*-
# Qinzhi Peng
import numpy as np
import argparse
from collections import Counter


parser = argparse.ArgumentParser()
parser.add_argument('train_infile')
parser.add_argument('test_infile')
parser.add_argument('max_depth', type=int, default=0)
parser.add_argument('train_outfile')
parser.add_argument('test_outfile')
parser.add_argument('metrics_outfile')


def load_data(infile):
    dataset = np.loadtxt(infile, dtype=str, delimiter='\t', skiprows = 1)
    if (dataset[1, 0] != '1' or dataset[1, 0] != '0'):                               # convert attributes into 0 or 1
        for i in range(len(dataset)):
            for j in range(len(dataset[0]) - 1):
                if ('n' in dataset[i,j]):
                    dataset[i,j] = 0
                else:
                    dataset[i,j] = 1
    true_label = dataset[:, -1]
    total_attrs = list(range(len(dataset[0])-1))
    return dataset, total_attrs, true_label


def divide_data(dataset, attribute):
    set0 = dataset[dataset[:, attribute]  == '0']    # subset where attribute = 0
    set1 = dataset[dataset[:, attribute]  == '1']
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
    count2 = sum(label == label2)
    if (count1 > count2):                             # the class y that appears most often in dataset D
        return label1
    elif (count1 < count2):    
        return label2
    else:
        return [label1, label2].sort()   

    
def binary_entropy(label):
    count = 0
    label1 = label[0]
    label_num = len(label)
    if label_num <= 1:
        return 0
    for value in label:
        if (value != label1):
            count += 1
    prob = count / label_num
    if (prob == 0):
        return - (1 - prob) * np.log2(1 - prob)
    elif (prob == 1):
        - prob * np.log2(prob)
    else:
        return - prob * np.log2(prob) - (1 - prob) * np.log2(1 - prob)
        
    
def info_gain(attribute, dataset):
    y_entropy = binary_entropy(dataset[:, -1]) 
    if (len(Counter(dataset[:, attribute])) == 1 ):
        return y_entropy
    else:    
        set0, set1 = divide_data(dataset, attribute)
        set0_label = set0[:, -1]
        set1_label = set1[:, -1]

        set0_entropy = binary_entropy(set0_label)
        set1_entropy = binary_entropy(set1_label)
        mutual_info = y_entropy - (set0_entropy * len(set0) + set1_entropy * len(set1)) / len(dataset)
        return mutual_info    


def select_attr(total_attrs, used_attrs, dataset):

    max_mutual_info = 0
    for i in range(len(total_attrs)):
        if (i not in used_attrs):

            mutual_info = info_gain(total_attrs[i], dataset)
            if (mutual_info > max_mutual_info):
                max_mutual_info = mutual_info
                split_attr = i
    #print(max_mutual_info)
   
    return split_attr, max_mutual_info

         
class Node:
    def __init__(self, split_attr = None, used_attrs = None, dataset = None, depth = 0,result = None, left = None, right = None):
        self.split_attr = split_attr
        self.used_attrs = used_attrs
        self.dataset = dataset
        self.depth = depth
        self.left = left
        self.right = right
        self.result = result             
    
def build_tree(dataset, max_depth, total_attrs, used_attrs, current_level): 
   
    if (max_depth == 0):
        node = Node(None,used_attrs, dataset, current_level)
        node.result = majority_vote(dataset)
        return node
    
    if not len(dataset):
        node = Node(None,used_attrs,  dataset, current_level)
        node.result = -1
        return node
    
    if (len(used_attrs) == len(total_attrs)) or (len(Counter(dataset[:, -1])) == 1 ):
        
        node = Node(None,used_attrs, dataset, current_level)
        if (len(Counter(dataset[:, -1])) == 1 ):
            node.result  = dataset[0, -1]
        else:
            node.result = majority_vote(dataset)
        return node
    

    
    split_attr, max_mutual_info = select_attr(total_attrs, used_attrs, dataset)  
         
    if (current_level <= max_depth and max_mutual_info > 0):

        
        node = Node(split_attr,used_attrs,  dataset, depth = current_level) 
        node.used_attrs.append(split_attr)
        used_attrs = node.used_attrs
        new_set0, new_set1 = divide_data(dataset, split_attr)
        
        print(node.used_attrs)
        current_level += 1

        node.left = build_tree(new_set0, max_depth, total_attrs, node.used_attrs, current_level)
        node.right = build_tree(new_set1, max_depth, total_attrs, node.used_attrs, current_level)
        return node
    else:     
        node = Node(None,used_attrs,  dataset, current_level)
        node.result = majority_vote(dataset)

        return node
          
                                                                                                                                              
def classify_line(node, data_line, predict_label):
    
    if (node.left == None and node.right == None):   
        predict_label.append(node.result)
        return node.result
    else:
            predict_label.append(node.result)
            return node.right.result
        else:
       
            if (data_line[node.split_attr] == node.left.dataset[0, node.split_attr]):
                classify_line(node.left, data_line, predict_label)
        if (node.right.result == -1):
            return node.left.result
        else:
            if (data_line[node.split_attr] == node.right.dataset[0, node.split_attr]):
                # print(node.right.dataset)
                classify_line(node.right, data_line, predict_label)

            
   

def classify(node, dataset):
    predict_label = []
    for row in dataset:
        # print('+++++++++')
        classify_line(node, row, predict_label)
        

    return predict_label
        

def metrics(predict_label, true_label):
    error_num = sum(predict_label != true_label)
    error = error_num / len(true_label)
    return error
      

def main(args):
    #train
    train_dataset, total_attrs, train_true = load_data(args.train_infile)
    node = build_tree(train_dataset, args.max_depth, total_attrs,[] , 1)
    train_predict = classify(node, train_dataset)
    np.savetxt(args.train_outfile, train_predict, fmt='%s', delimiter='\n')
    
    #predict
    test_dataset, total_attrs, test_true = load_data(args.test_infile)
    test_predict = classify(node, test_dataset)
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