import numpy as np
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('train_input')
parser.add_argument('index_to_word')
parser.add_argument('index_to_tag')
parser.add_argument('hmminit')
parser.add_argument('hmmemit')
parser.add_argument('hmmtrans')

class DataLoader:  
    def load_data(infile):
        words = []  
        tags = []
        sentences_tags = []
        sentences = []
        with open(infile, 'r') as file:
            dataset = file.readlines() 
        for i in range(len(dataset)):
            line = dataset[i].strip('\n').split("\t")
            if (line[0] != ""):    
                words.append(line[0])
                tags.append(line[1])
                if (i == len(dataset)-1):
                    sentences.append(words)
                    sentences_tags.append(tags)
            else:
                sentences.append(words)
                sentences_tags.append(tags)
                words = []  
                tags = []
            
        return sentences, sentences_tags
            
    def load_index(infile):
        data2index = {}
        with open(infile, 'r') as file:
            dataset = file.readlines() 
        for i in range(len(dataset)):
            line = dataset[i].strip('\n')
            data2index[line] = i
            
        return data2index
    
def Learning(sentences, sentences_tags, word2index, tag2index):
    tag_num = len(tag2index)
    words_num = len(word2index)
    init = np.ones(tag_num)    #pseudocount
    trans = np.ones((tag_num, tag_num))
    emit = np.ones((tag_num, words_num))
    for i, (sentence, sentence_tags) in enumerate(zip(sentences, sentences_tags)):
        if (i < 10000):
            for j, word in enumerate(sentence):
                word_index = word2index[word]
                tag = sentence_tags[j]
                tag_index = tag2index[tag]
                
                # initialization
                if (j == 0):
                    init[tag_index] += 1
                
                # transition probabilities   
                if (j != len(sentence) - 1):
                    tag_next = sentence_tags[j+1]
                    tag_next_index = tag2index[tag_next]
                    trans[tag_index][tag_next_index] += 1
                    
                # emission probabilities
                emit[tag_index][word_index] += 1
                
    init /= np.sum(init)
    trans /= np.sum(trans, axis=1)[:, None]
    emit /= np.sum(emit, axis=1)[:, None]
    
    return init, trans, emit

def main(arg):
    sentences, sentences_tags =  DataLoader.load_data(args.train_input)
    word2index  = DataLoader.load_index(args.index_to_word)
    tag2index = DataLoader.load_index(args.index_to_tag)
    init, trans, emit = Learning(sentences, sentences_tags, word2index, tag2index)
    
    np.savetxt(arg.hmminit, init, delimiter=' ')
    np.savetxt(arg.hmmemit, emit, delimiter=' ')
    np.savetxt(arg.hmmtrans, trans, delimiter=' ')
    

if __name__ == '__main__':
    args = parser.parse_args()
    main(args)     
    
        