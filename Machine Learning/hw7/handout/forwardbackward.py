import numpy as np
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('validation_input')
parser.add_argument('index_to_word')
parser.add_argument('index_to_tag')
parser.add_argument('hmminit')
parser.add_argument('hmmemit')
parser.add_argument('hmmtrans')
parser.add_argument('predicted_file')
parser.add_argument('metric_file')

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
    
    def load_hmm(infile):
        hmm_matrix = np.loadtxt(infile, dtype=np.float64, delimiter=' ')
        
        return hmm_matrix
       
    
class ForwardBackward():
    def forwardbackward(sentence, word2index, tag2index, init, emit, trans):
        words_num = len(sentence)
        tag_num = len(tag2index)
        alpha = np.zeros((tag_num, words_num))
        beta = np.ones((tag_num, words_num))    # All states could be ending states, initialize 1
        
        # forward recursively
        for i, word in enumerate(sentence): 
            word_index = word2index[word]
            if (i == 0):
                alpha[:, i] = np.log(emit[:, word_index]) + np.log(init) 
            else: 
                m = alpha[:, i-1].max()   
                for j in range(tag_num):
                    alpha_sum = 0
                    for k in range(tag_num):
                         alpha_sum += np.exp((alpha[k][i-1] - m) + np.log(trans[k][j]))
                    alpha_sum = m + np.log(alpha_sum)  
                    alpha[j][i] = np.log(emit[j][word_index]) + alpha_sum               

        # recursively backwards       
        for i, word in enumerate(reversed(sentence)):
            if (i == 0):
                beta[:, -i-1] = np.log(beta[:, -i-1])
            else:
                b = beta[:, -i].max() 
                word_next = sentence[-i]
                word_index = word2index[word_next]
                for j in range(tag_num):
                    beta_sum = 0
                    for k in range(tag_num):
                         beta_sum += np.exp(np.log(emit[k][word_index]) + (beta[k][-i] - b) + np.log(trans[j][k]))         
                    beta[j][-i-1] = b + np.log(beta_sum)
         
        return alpha, beta
    
    def predict(alpha, beta):
        cond_prob = alpha + beta         
        tags_predict_index = np.argmax(cond_prob,axis=0)  # select the tag that appears earlier if ties
                
        return tags_predict_index
     
    def evaluation(alpha):
        m = alpha[:, -1].max()   
        log_likelihood = m + np.log(np.sum(np.exp(alpha[:, -1] - m)))   

        return log_likelihood
    
    def metrics(tags_predict_index, sentence_tags, tag2index):
        accuracy_num = 0
        for i in range(len(sentence_tags)):
            tag_true = tag2index[sentence_tags[i]]
            if (tags_predict_index[i] == tag_true):
                accuracy_num += 1
                
        return accuracy_num
    
    def index2tag(index, tag2index):
        for i, tag in enumerate(tag2index):
            if tag2index[tag] == index:
                return tag

        return -1


def main(arg):
    sentences, sentences_tags =  DataLoader.load_data(args.validation_input)
    word2index  = DataLoader.load_index(args.index_to_word)
    tag2index = DataLoader.load_index(args.index_to_tag)
    init = DataLoader.load_hmm(args.hmminit)
    emit = DataLoader.load_hmm(args.hmmemit)
    trans = DataLoader.load_hmm(args.hmmtrans) 
    total_accuracy = 0
    total_tags = 0
    avg_log_likelihood = 0
    tags_predict = []
    words = []
    
    for i, (sentence, sentence_tags) in enumerate(zip(sentences, sentences_tags)):
        alpha, beta = ForwardBackward.forwardbackward(sentence, word2index, tag2index, init, emit, trans)
        tags_predict_index = ForwardBackward.predict(alpha, beta)
        accuracy_num = ForwardBackward.metrics(tags_predict_index, sentence_tags, tag2index)
        tags_num = len(sentence_tags)
        total_tags += tags_num
        total_accuracy += accuracy_num
        accuracy = total_accuracy / total_tags
        
        log_likelihood = ForwardBackward.evaluation(alpha)
        avg_log_likelihood += log_likelihood / len(sentences)
        
        for j, word in enumerate(sentence):   
            tag_predict = ForwardBackward.index2tag(tags_predict_index[j], tag2index)
            tags_predict.append(tag_predict + "\n")
            words.append(word + "\t")
        
        tags_predict.append("")
        words.append("\n")

    with open(arg.predicted_file, 'w') as f_pred:
        for k in range(len(words)):         
            f_pred.writelines("{}{}".format(words[k], tags_predict[k]))
    
    with open(arg.metric_file, 'w') as f_metrics:
        f_metrics.writelines("Average Log-Likelihood: {}\n".format(avg_log_likelihood))
        f_metrics.writelines("Accuracy: {}".format(accuracy))
        
        
    
    
if __name__ == '__main__':
    args = parser.parse_args()
    main(args)     
    