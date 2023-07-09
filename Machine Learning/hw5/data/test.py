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
parser.add_argument('learning_rate', type=int)

class DataLoader:
    def __init__(self, infile):
      self.infile = infile
      
    def load_data(self):
        pixels_set = []
        with open(self.infile, 'r') as file:
            dataset = file.readlines()
        y = np.zeros((4, len(dataset)), dtype=int)
        for i in range(len(dataset)):
            line = dataset[i].strip('\n').split(",")
            label = int(float(line[0]))
            y[label, i] = 1
            pixels_set.append(np.array(line[1:], float))
        x = np.array(pixels_set)
                
        return y, x

class NeuralNet:

        
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
        print(1)
        print(grad_a[None, :])
        print(alpha[:, 1:])
        grad_x = np.dot(grad_a[None, :], alpha[:, 1:])
        grad_alpha = np.dot(grad_a[:,None], x[None, :])  # 3*1  1*5)
        return grad_alpha, grad_x[0]
    
    def sigmoid_backward(self, a, z, grad_z):
        grad_a = (grad_z * z[1:] * (1 - z[1:]))
        return grad_a
    
    def softmax_cross_entropy_backward(self, y, y_hat, grad_J):
        grad_b = grad_J * (y_hat - y)
        return grad_b
    
    def adagrad_update(self,theta, ):
        epsilon = 1e-5
        
        return
    
    def init_para(self, x, y):
        if self.init_flag == 1:
            alpha = np.random.uniform(-0.1, 0.1, (self.hidden_units, x.shape[0]+1))  # 4*7
            beta = np.random.uniform(-0.1, 0.1, (y.shape[0], self.hidden_units+1))   # 3*5
        else:
            alpha = np.zeros((self.hidden_units, x.shape[0]+1))
            beta = np.zeros((y.shape[0], self.hidden_units+1))
        
        return alpha, beta
     
    def nnforward(self, x, y, alpha, beta):
        # print(x_bias)
        a, x_bias = self.linear_forward(x, alpha)
        # print(a)         # [2 7 8 2]
        z = self.sigmoid_forward(a) 
        # print(z)             # [0.88079708 0.99908895 0.99966465 0.88079708]
        b, z_bias = self.linear_forward(z, beta)
        # print(b)                # [2.76044275 3.64296693 4.52261261]
        y_hat = self.cross_entropy(b)
        J = self.softmax(y, y_hat)
        # print(y_hat)
        print(J)                 # 1.3412402107571517
        return x_bias, a, z_bias, b, y_hat, J

    
    def nnbackward(self, x, y, alpha, beta, a, z, b, y_hat, J):
        grad_J = 1
        grad_b = self.softmax_cross_entropy_backward(y, y_hat, grad_J)  
        print("grad_b") 
        print(grad_b)           # 1*3
        grad_beta, grad_z = self.linear_backward(z, beta, grad_b)  
        print("grad_beta")
        print(grad_beta)        # 3*5  
        print("grad_z")
        print(grad_z)           # [[ 1.26055569  1.58515878 -1.58515878 -0.73847887]]

        grad_a = self.sigmoid_backward(a, z, grad_z)
        print("grad_a")
        print(grad_a)
        grad_alpha, grad_x = self.linear_backward(x, alpha, grad_a)
        print("grad_alpha")
        print(grad_alpha)
        print("grad_x")
        print(grad_x)
        
        return grad_alpha, grad_beta
     
    

    
# x =  np.array([[1,1,0,0,1,1],[1,1,0,0,1,1]]).T
# alpha = np.zeros((4, x.shape[0]+1))
# print(alpha)

# a = DataLoader("tinyTrain.csv")      
# y, x = a.load_data()
# print(type(y))

x =  np.array([1,1,0,0,1,1])
y =  np.array([0,1,0])
print(">>>>>>>>")
print(np.argmax(x))
alpha = np.array([[1,1,2,-3,0,1,-3],[1,3,1,2,1,0,2],[1,2,2,2,2,2,1],[1,1,0,2,1,-2,2]])
beta = np.array([[1,1,2,-2,1],[1,1,-1,1,2],[1,3,1,-1,1]])
nn = NeuralNet()
x, a, z, b, y_hat, J = nn.nnforward(x, y, alpha, beta)
grad_alpha, grad_beta = nn.nnbackward(x, y, alpha, beta, a, z, b, y_hat, J)


# s_beta = np.zeros((4,5))
# grad_beta1 = np.array([[ 0.25 ,  0.125,  0.125,  0.125 , 0.125],
#  [ 0.25  , 0.125,  0.125 , 0.125,  0.125],
#  [-0.75  ,-0.375, -0.375, -0.375, -0.375],
#  [ 0.25  , 0.125 , 0.125,  0.125,  0.125]])

# s1 = s_beta + grad_beta1 * grad_beta1
# print("s1")
# print(s1)



# beta1 =  - (0.1 / (np.sqrt(s1 + 1e-5))) * grad_beta1
# print("beta1")
# print(beta1)


# grad_beta2 = np.array([[ 0.20738399 , 0.10369199 , 0.10369199 , 0.10369199 , 0.10369199],
#                       [ 0.20738399,  0.10369199 , 0.10369199 , 0.10369199  ,0.10369199],
#                       [ 0.37784804 , 0.18892402,  0.18892402 , 0.18892402  ,0.18892402], 
#                       [-0.79261601 ,-0.39630801, -0.39630801 ,-0.39630801 ,-0.39630801]])

# s2 = s1 + grad_beta2 * grad_beta2
# print("s2")
# print(s2)

# beta2 = beta1 - (0.1 / (np.sqrt(s2 + 1e-5))) * grad_beta2
# print("beta2")
# print(beta2)


# infile = "val_loss_sgd_out.txt"
# with open(infile, 'r') as file:
#     loss_sgd = np.loadtxt(infile, dtype=float, delimiter='\n') 
# x_axis = np.arange(1,101)
# print(x_axis)
