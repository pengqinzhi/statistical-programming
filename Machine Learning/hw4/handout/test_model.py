# -*- coding: utf-8 -*-
from feature import *
import numpy as np
import random 
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('ori_out')
parser.add_argument('train_out')


def load(infile):
    labels = []
    sentences = []
    with open(infile,'r') as file:
        dataset = file.readlines()
    for line in dataset:
        line = line.strip('\n').split("\t")
        labels.append(line[0])
        sentences.append(line[1:])      
    return labels, sentences



ori_out = 'largeoutput/model2_formatted_valid.tsv'
train_out = 'largeoutput/formatted_valid.tsv'
ori_labels, ori_sentences = load(ori_out)
train_labels, train_sentences = load(train_out)
for i in range(len(ori_labels)):
    if train_labels[i] != ori_labels[i]:
        print(train_labels[i])
        print(ori_labels[i])

print("sentences")
ori_sentence = ori_sentences[0]

for i in range(len(ori_sentences)):
    ori_sentence = ori_sentences[i]
    train_sentence = train_sentences[i]
    
    for j in range(len(ori_sentence)):

        if ori_sentence[j] != train_sentence[j]:
            
            print(ori_sentence[j])
            print(train_sentence[j])

    
