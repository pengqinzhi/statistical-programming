# -*- coding: utf-8 -*-
# Qinzhi Peng
import numpy as np
import argparse


parser = argparse.ArgumentParser()
parser.add_argument('train_input')
parser.add_argument('validation_input')
parser.add_argument('train_out')
parser.add_argument('validation_out')
parser.add_argument('metrics_out')
parser.add_argument('num_epoch', type=int)
parser.add_argument('hidden_units', type=int)
parser.add_argument('init_flag', type=int)
parser.add_argument('learning_rate', type=float)

class DataLoader:
    def __init__(self, infile):
      self.infile = infile
      
    def load_data(self):
        pixels_set = []
        labels_true = []
        with open(self.infile, 'r') as file:
            dataset = file.readlines()
        labels = np.zeros((len(dataset), 4), dtype=int)
        for i in range(len(dataset)):
            line = dataset[i].strip('\n').split(",")
            label = int(float(line[0]))
            labels_true.append(label)
            labels[i, label] = 1
            pixels_set.append(np.array(line[1:], float))
        labels_true = np.array(labels_true)
        pixels_set = np.array(pixels_set)
                
        return labels, pixels_set



class NeuralNet:
    def __init__(self, num_epoch, hidden_units, init_flag, learning_rate):
        self.num_epoch = num_epoch
        self.hidden_units = hidden_units
        self.init_flag = init_flag
        self.learning_rate = learning_rate
        
        
    def linear_forward(self, x, alpha):
        x_bias = np.insert(x, 0, 1, axis=0)
        a = np.dot(alpha, x_bias)
        return a, x_bias
    
    
    def sigmoid_forward(self, a):
        z = 1 / (1 + np.exp(-a))
        return z
        
    
    def cross_entropy(self, b):
        y_hat = np.exp(b) / sum(np.exp(b))
        return y_hat
    
    
    def softmax(self, y, y_hat):
        J = - sum(y * np.log(y_hat).T)
        return J
         
    
    def linear_backward(self, x, alpha, grad_a):
        grad_x = np.dot(grad_a[None, :], alpha[:, 1:])
        grad_alpha = np.dot(grad_a[:,None], x[None, :])  
        return grad_alpha, grad_x[0]
    
    
    def sigmoid_backward(self, a, z, grad_z):
        grad_a = (grad_z * z[1:] * (1 - z[1:]))
        return grad_a
    
    
    def softmax_cross_entropy_backward(self, y, y_hat, grad_J):
        grad_b = grad_J * (y_hat - y)
        return grad_b
    
    
    def init_para(self, x, y):
        if self.init_flag == 1:
            alpha = np.random.uniform(-0.1, 0.1, (self.hidden_units, x.shape[0]+1)) 
            beta = np.random.uniform(-0.1, 0.1, (4, self.hidden_units+1)) 
        else:
            alpha = np.zeros((self.hidden_units, x.shape[0]+1))
            beta = np.zeros((4, self.hidden_units+1)) 
        return alpha, beta
    
    
    def adagrad_update(self, theta, grad_theta, s):
        epsilon = 1e-5
        #print("s")
        #print(s)
        s = s + grad_theta * grad_theta
        #print("grad_theta")
        #print(grad_theta)
        #print("new s")
        #print(s)
        theta = theta - (self.learning_rate / (np.sqrt(s + epsilon))) * grad_theta
        return theta, s
     
        
    def nnforward(self, x, y, alpha, beta):
        a, x_bias = self.linear_forward(x, alpha)
        #print("a")
        #print(a)
        z = self.sigmoid_forward(a) 
        #print("z")
        #print(z)
        b, z_bias = self.linear_forward(z, beta)
        y_hat = self.cross_entropy(b)
        J = self.softmax(y, y_hat)
        #print("y_hat")
        #print(y_hat)
        #print("J")
        #print(J)
        
        return x_bias, a, z_bias, b, y_hat, J

    
    def nnbackward(self, x, y, alpha, beta, a, z, b, y_hat, J):
        grad_J = 1
        grad_b = self.softmax_cross_entropy_backward(y, y_hat, grad_J)  
        grad_beta, grad_z = self.linear_backward(z, beta, grad_b)  
        grad_a = self.sigmoid_backward(a, z, grad_z)
        grad_alpha, grad_x = self.linear_backward(x, alpha, grad_a)
        #print("grad_beta")
        #print(grad_beta)
        #print("grad_a")
        #print(grad_a)
        #print("grad_alpha")
        #print(grad_alpha)
        
        return grad_alpha, grad_beta
    
    
    def train(self, train_input, validation_input, train_output, validation_out, metrics_out):
        labels_train, pixels_set_train = DataLoader(train_input).load_data()
        labels_valid, pixels_set_valid = DataLoader(validation_input).load_data()
        alpha, beta =  self.init_para(pixels_set_train[0], labels_train)
        s_alpha = np.zeros(alpha.shape)
        s_beta = np.zeros(beta.shape)
        num_train = pixels_set_train.shape[0]

        with open (metrics_out,'w') as f:
            for epoch in range(self.num_epoch):
                for i in range(num_train):
                    #print("i")
                    #print(i)
                    x_train = pixels_set_train[i, :]
                    y_train = labels_train[i, :]
                    #print("x_train")
                    #print(x_train)
                    #print("y_train")
                    #print(y_train)
                    
                    x, a, z, b, y_hat_train, J_train = self.nnforward(x_train, y_train, alpha, beta)
                    # print("J_train")
                    # print(J_train)
                    grad_alpha, grad_beta = self.nnbackward(x, y_train, alpha, beta, a, z, b, y_hat_train, J_train)
                    alpha, s_alpha = self.adagrad_update(alpha, grad_alpha, s_alpha)
                    beta, s_beta = self.adagrad_update(beta, grad_beta, s_beta)
                    print("New alpha")
                    print(alpha)
                    print("New beta")
                    print(beta)
                    
                error_train, loss_train = self.predict(pixels_set_train, labels_train, alpha, beta, train_output)
                error_valid, loss_valid = self.predict(pixels_set_valid, labels_valid, alpha, beta, validation_out)
                f.write('epoch={} crossentropy(train):{}\n'.format(epoch+1, loss_train))
                f.write('epoch={} crossentropy(test): {}\n'.format(epoch+1, loss_valid))
            
            f.write('error (train): {}\n'.format(error_train))
            f.write('error (test): {}\n'.format(error_valid))
        
        
    def predict(self, pixels_set, labels, alpha, beta, outfile):
        error_num = 0
        loss = 0
        num = pixels_set.shape[0]
        with open(outfile, 'w') as f:
            for i in range(num):
                x = pixels_set[i, :]
                y = labels[i, :]
                y_hat, J = self.nnforward(x, y, alpha, beta)[-2:]  
                loss = loss + J/num
                
                label_predict = np.argmax(y_hat)
                label_true = np.argmax(y)
                if (label_predict != label_true):
                    error_num += 1  
                f.write('{}\n'.format(label_predict))
              
        error = error_num / num
        
        return error, loss
            
    

def main(args):
    nn = NeuralNet(args.num_epoch, args.hidden_units, args.init_flag, args.learning_rate)
    nn.train(args.train_input, args.validation_input, args.train_out, args.validation_out, args.metrics_out)
    
    
    
if __name__ == '__main__':
    args = parser.parse_args()
    main(args)     