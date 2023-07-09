# -*- coding: utf-8 -*-
# Qinzhi Peng
import numpy as np
import argparse
import matplotlib.pyplot as plt

parser = argparse.ArgumentParser()
parser.add_argument('train_input')
parser.add_argument('validation_input')
parser.add_argument('--num_epoch', type=int)
parser.add_argument('--hidden_units', type=int)
parser.add_argument('--init_flag', type=int)
parser.add_argument('--learning_rate', type=float)

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
        s = s + grad_theta * grad_theta
        theta = theta - (self.learning_rate / (np.sqrt(s + epsilon))) * grad_theta
        return theta, s
     
        
    def nnforward(self, x, y, alpha, beta):
        a, x_bias = self.linear_forward(x, alpha)
        z = self.sigmoid_forward(a) 
        b, z_bias = self.linear_forward(z, beta)
        y_hat = self.cross_entropy(b)
        J = self.softmax(y, y_hat)
        
        return x_bias, a, z_bias, b, y_hat, J

    
    def nnbackward(self, x, y, alpha, beta, a, z, b, y_hat, J):
        grad_J = 1
        grad_b = self.softmax_cross_entropy_backward(y, y_hat, grad_J)  
        grad_beta, grad_z = self.linear_backward(z, beta, grad_b)  
        grad_a = self.sigmoid_backward(a, z, grad_z)
        grad_alpha, grad_x = self.linear_backward(x, alpha, grad_a)
        
        return grad_alpha, grad_beta
    
    
    def train(self, train_input, validation_input):
        labels_train, pixels_set_train = DataLoader(train_input).load_data()
        labels_valid, pixels_set_valid = DataLoader(validation_input).load_data()
        alpha, beta =  self.init_para(pixels_set_train[0], labels_train)
        s_alpha = np.zeros(alpha.shape)
        s_beta = np.zeros(beta.shape)
        num_train = pixels_set_train.shape[0]
        # val_loss_AdaGrad = []
        train_loss = []
        val_loss = []
        
        for epoch in range(self.num_epoch):
            for i in range(num_train):
                x_train = pixels_set_train[i, :]
                y_train = labels_train[i, :]
                
                x, a, z, b, y_hat_train, J_train = self.nnforward(x_train, y_train, alpha, beta)
                grad_alpha, grad_beta = self.nnbackward(x, y_train, alpha, beta, a, z, b, y_hat_train, J_train)
                alpha, s_alpha = self.adagrad_update(alpha, grad_alpha, s_alpha)
                beta, s_beta = self.adagrad_update(beta, grad_beta, s_beta)
           
            error_train, loss_train = self.predict(pixels_set_train, labels_train, alpha, beta)
            error_valid, loss_valid = self.predict(pixels_set_valid, labels_valid, alpha, beta)

            train_loss.append(loss_train)
            val_loss.append(loss_valid)
            
        # return loss_train, loss_valid     # plot 3.1.1
        # return val_loss_AdaGrad           # plot 3.1.3
        return train_loss, val_loss         # plot 3.2.1
        
        
    def predict(self, pixels_set, labels, alpha, beta):
        error_num = 0
        loss = 0
        num = pixels_set.shape[0]
        
        for i in range(num):
            x = pixels_set[i, :]
            y = labels[i, :]
            y_hat, J = self.nnforward(x, y, alpha, beta)[-2:]  
            loss = loss + J/num
            
            label_predict = np.argmax(y_hat)
            label_true = np.argmax(y)
            if (label_predict != label_true):
                error_num += 1  
               
        error = error_num / num
        
        return error, loss
            
    

def main(args):  
   
    # # plot 3.1.1
    # nn_1 = NeuralNet(100, 5, 1, 0.01)
    # nn_2 = NeuralNet(100, 20, 1, 0.01)
    # nn_3 = NeuralNet(100, 50, 1, 0.01)
    # nn_4 = NeuralNet(100, 100, 1, 0.01)
    # nn_5 = NeuralNet(100, 200, 1, 0.01)
    
    # loss_train_1, loss_valid_1 = nn_1.train(args.train_input, args.validation_input)
    # loss_train_2, loss_valid_2 = nn_2.train(args.train_input, args.validation_input)
    # loss_train_3, loss_valid_3 = nn_3.train(args.train_input, args.validation_input)
    # loss_train_4, loss_valid_4 = nn_4.train(args.train_input, args.validation_input)
    # loss_train_5, loss_valid_5 = nn_5.train(args.train_input, args.validation_input)
    
    # x_axis = [5,20,50,100,200]
    # y_axis_train = [loss_train_1, loss_train_2, loss_train_3, loss_train_4, loss_train_5]
    # y_axis_valid = [loss_valid_1, loss_valid_2, loss_valid_3, loss_valid_4, loss_valid_5]
    
    # plt.title('Avg. Train and Validation Cross-Entropy Loss')
    
    # plt.plot(x_axis, y_axis_train, '-bo', label = 'train', markersize=2)
    
    # plt.plot(x_axis, y_axis_valid, '--ro', label = 'validation', markersize=2)
    
    # plt.xlabel('number of hidden units')
    # plt.ylabel('average cross-entropy')
    # plt.xlim((- 5, 200+10))
    # plt.ylim((0.2-0.05, 1.2+0.1))
    # plt.xticks(np.arange(0, 200+10, 25))
    # plt.yticks(np.arange(0.2, 1.2+0.1, 0.2))
    # plt.legend()
    # plt.savefig('./Q3.1.png')

    
    # # plot 3.1.3
    # nn = NeuralNet(100, 50, 1, 0.01)
    # val_loss_AdaGrad = nn.train(args.train_input, args.validation_input)
    
    # infile = "val_loss_sgd_out.txt"
    # with open(infile, 'r') as file:
    #     val_loss_sgd = np.loadtxt(infile, dtype=float, delimiter='\n') 
    
    # x_axis = np.arange(1,101)
    # plt.title('Avg. Validation Cross-Entropy Loss of SGD with and without AdaGrad')
    # plt.plot(x_axis, val_loss_AdaGrad, '-b', label = 'AdaGrad')
    # plt.plot(x_axis, val_loss_sgd, '--r', label = 'SGD')
    
    # plt.xlabel('epochs')
    # plt.ylabel('average cross-entropy')
    # plt.xlim((- 5, 100+10))
    # plt.ylim((1.1, 1.45))
    # plt.xticks(np.arange(0, 100+10, 20))
    # plt.yticks(np.arange(1.1, 1.5, 0.1))
    # plt.legend()
    # plt.savefig('./Q3.1.3.png')
    # plt.show()
    
    
    # # plot 3.2.1
    # nn_1 = NeuralNet(100, 50, 1, 0.1)
    # train_loss, val_loss  = nn_1.train(args.train_input, args.validation_input)
    
    # x_axis = np.arange(1,101)   
    # plt.plot(x_axis, train_loss, '-b', label = 'train')    
    # plt.plot(x_axis, val_loss, '--r', label = 'validation')
    
    # plt.title('Avg. Train and Validation Cross-Entropy Loss when LR=0.1')
    # plt.xlabel('epochs')
    # plt.ylabel('average cross-entropy')
    # plt.xlim((- 5, 100+5))
    # plt.ylim((1.0-0.05, 1.4+0.05))
    # plt.xticks(np.arange(0, 100+5, 20))
    # plt.yticks(np.arange(0.9, 1.4+0.05, 0.1))
    # plt.legend()
    # plt.savefig('./Q3.2.1.png')
    # plt.show()
    
    
    # # plot 3.2.2
    # nn_2 = NeuralNet(100, 50, 1, 0.01)
    # train_loss, val_loss  = nn_2.train(args.train_input, args.validation_input)
    
    # x_axis = np.arange(1,101)   
    # plt.plot(x_axis, train_loss, '-b', label = 'train')    
    # plt.plot(x_axis, val_loss, '--r', label = 'validation')
    
    # plt.title('Avg. Train and Validation Cross-Entropy Loss when LR=0.01')
    # plt.xlabel('epochs')
    # plt.ylabel('average cross-entropy')
    # plt.xlim((- 5, 100+5))
    # plt.ylim((0.7-0.05, 1.4+0.05))
    # plt.xticks(np.arange(0, 100+5, 20))
    # plt.yticks(np.arange(0.7, 1.4+0.05, 0.1))
    # plt.legend()
    # plt.savefig('./Q3.2.2.png')
    # plt.show()
    
    
    # plot 3.2.3
    nn_3 = NeuralNet(100, 50, 1, 0.001)
    train_loss, val_loss  = nn_3.train(args.train_input, args.validation_input)
    
    x_axis = np.arange(1,101)   
    plt.plot(x_axis, train_loss, '-b', label = 'train')    
    plt.plot(x_axis, val_loss, '--r', label = 'validation')
    
    plt.title('Avg. Train and Validation Cross-Entropy Loss when LR=0.001')
    plt.xlabel('epochs')
    plt.ylabel('average cross-entropy')
    plt.xlim((- 5, 100+5))
    plt.ylim((1.1-0.05, 1.4+0.05))
    plt.xticks(np.arange(0, 100+5, 20))
    plt.yticks(np.arange(1.1, 1.4+0.05, 0.1))
    plt.legend()
    plt.savefig('./Q3.2.3.png')
    plt.show()
    
if __name__ == '__main__':

    args = parser.parse_args()
    main(args)

    
    