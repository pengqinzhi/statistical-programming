import numpy as np


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
        matrix = np.loadtxt(infile, dtype=np.float64, delimiter=' ')
        
        return matrix

# class ForwardBackward():
#     def forwardbackward(sentence, sentence_tags, word2index, tag2index, init, emit, trans):
#         words_num = len(word2index)
#         tag_num = len(tag2index)
#         alpha = np.zeros((tag_num, words_num))
#         beta = np.ones((tag_num, words_num))    # All states could be ending states, initialize 1
        
#         # forward recursively
#         for i, word in enumerate(sentence):
#             word_index = word2index[word]
#             if (i == 0):
                
#                 alpha[:, i] = emit[:, word_index] * init
#             else:
#                 alpha[:, i] = emit[:, word_index] * np.dot(trans.T, alpha[:, i-1])
#         # recursively backwards
        
#         for i, word in enumerate(reversed(sentence)):            
#             if (i != 0):
#                 word_next = sentence[-i]
#                 word_index = word2index[word_next]
#                 beta[:, -i-1] = np.dot(trans, emit[:, word_index] * beta[:, -i])    
                
#         return alpha, beta
    
    
    
class ForwardBackward():
    def forwardbackward(sentence, word2index, tag2index, init, emit, trans):
        words_num = len(word2index)
        tag_num = len(tag2index)
        alpha = np.zeros((tag_num, words_num))
        beta = np.ones((tag_num, words_num))    # All states could be ending states, initialize 1
        
        # forward recursively
        for i, word in enumerate(sentence):
            word_index = word2index[word]
            if (i == 0):
                alpha[:, i] = emit[:, word_index] * init      
            else:
                
                alpha[:, i-1] = np.exp(alpha[:, i-1] - ForwardBackward.log_sum_exp(np.log(alpha[:, i-1])))
                alpha[:, i] = emit[:, word_index] + np.dot(trans.T, alpha[:, i-1])
              
        print(alpha[:, 6])
        
        # recursively backwards       
        for i, word in enumerate(reversed(sentence)):
            if (i == 0):
                beta[:, -i] = beta[:, -i]
            else:
                word_next = sentence[-i]
                word_index = word2index[word_next]
                beta[:, -i]  = np.exp(beta[:, -i] - ForwardBackward.log_sum_exp(np.log(beta[:, -i])))
                beta[:, -i-1] = np.dot(trans, emit[:, word_index] * beta[:, -i])
                 
        return alpha, beta
    
    def predict(alpha, beta):
         cond_prob = np.log(alpha) + np.log(beta)
         tags_predict_index = np.argmax(cond_prob,axis=0)  # select the tag that appears earlier if ties
         
         return tags_predict_index
     
    def evaluation(alpha):
        m = max(alpha[:, -1]) 
        log_likelihood = m + np.log(np.sum(np.exp(alpha[:, -1] - m)))   

        return log_likelihood
    
    def metrics(tags_predict_index, sentence_tags, tag2index):
        accuracy_num = 0
        for i in range(len(sentence_tags)):
            tag_true = tag2index[sentence_tags[i]]
            if (tags_predict_index[i] == tag_true):
                accuracy_num += 1
                
        return accuracy_num
    
    def log_sum_exp(alpha_col):
        m = max(alpha_col) 
        alpha_col = m + np.log(np.sum(np.exp(alpha_col - m)))   
        
        return alpha_col
    
    def index2tag(index, tag2index):
        for i, tag in enumerate(tag2index):
            if tag2index[tag] == index:
                return tag

        return -1

        
train = "fr_data/train.txt"

valid = "toy_data/validation.txt"
word_index = "toy_data/index_to_word.txt"
tag_index = "toy_data/index_to_tag.txt"
hmmemit = "toy_output/toy_hmmemit.txt"
hmmtrans = "toy_output/toy_hmmtrans.txt"
init = "toy_output/toy_hmminit.txt"

sentences, sentences_tags =  DataLoader.load_data(valid)
word2index  = DataLoader.load_index(word_index)
tag2index = DataLoader.load_index(tag_index)
init = DataLoader.load_hmm(init)
emit = DataLoader.load_hmm(hmmemit)
trans = DataLoader.load_hmm(hmmtrans)
sentence = sentences[0]
sentence_tags = sentences_tags[0]


alpha, beta = ForwardBackward.forwardbackward(sentence, word2index, tag2index, init, emit, trans)
cond_prob = alpha * beta
tag_predict = np.argmax(cond_prob,axis=0)
print(alpha)

print(beta)
print(cond_prob)
print(tag_predict)


class ForwardBackward():
    def forwardbackward(sentence, word2index, tag2index, init, emit, trans):
        words_num = len(word2index)
        tag_num = len(tag2index)
        alpha = np.zeros((tag_num, words_num))
        beta = np.zeros((tag_num, words_num))    # All states could be ending states, initialize 1
        
        # forward recursively
        for i, word in enumerate(sentence):
            print(i)
            word_index = word2index[word]
            if (i == 0):
                alpha[:, i] = np.log(emit[:, word_index]) + np.log(init)          
            else:
                m = alpha[:, i-1].max()
                
                for j in range(tag_num):
                    alpha_sum = 0
                    # n = max(trans[:, j])
                    for k in range(tag_num):
                         alpha_sum += np.exp((alpha[k][i-1] - m) + np.log(trans[k][j]))
                              
                         
                    alpha_sum = m + np.log(alpha_sum)
                    
                    alpha[j][i] = np.log(emit[j][word_index]) + alpha_sum
        print( alpha)

                    
                
                
                
                
        
        # recursively backwards       
        for i, word in enumerate(reversed(sentence)):
            if (i == 0):
                beta[:, -i] = np.log(beta[:, -i])
            else:
                word_next = sentence[-i]
                word_index = word2index[word_next]
                beta[:, -i-1] = np.log(np.dot(trans, emit[:, word_index] * np.exp(beta[:, -i])))
                 
        return alpha, beta
    
    def predict(alpha, beta):
         cond_prob = alpha * beta
         tags_predict_index = np.argmax(cond_prob,axis=0)  # select the tag that appears earlier if ties
         
         return tags_predict_index
     
    def evaluation(alpha):
        m = max(alpha[:, -1]) 
        log_likelihood = m + np.log(np.sum(np.exp(alpha[:, -1] - m)))   

        
        return log_likelihood
    
    def metrics(tags_predict_index, sentence_tags, tag2index):
        accuracy_num = 0
        for i in range(len(sentence_tags)):
            tag_true = tag2index[sentence_tags[i]]
            if (tags_predict_index[i] == tag_true):
                accuracy_num += 1
                
        return accuracy_num
    
    def log_sum_exp_trick(alpha_col):
        m = max(alpha_col) 
        alpha_col = m + np.log(np.sum(np.exp(alpha_col - m)))   
        
        return alpha_col
    
    def index2tag(index, tag2index):
        for i, tag in enumerate(tag2index):
            if tag2index[tag] == index:
                return tag

        return -1


