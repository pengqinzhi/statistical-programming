# -*- coding: utf-8 -*-
"""
Created on Fri Nov 12 17:27:18 2021

@author: Peng
"""
import numpy as np

y = (0.4*0.75*0.7+0.5*0.25*0.7)/((0.4*0.75*0.7+0.5*0.25*0.7)+(0.5*0.8*0.3+0.4*0.2*0.3))

alpha = np.array([1/15,3/10])
b = np.array([[1/4,3/4],[3/5,2/5]])
a = np.array([2/3,1/8])

al2 =  a * (np.dot(b.T,alpha))

a3 = np.array([1/6,3/8])
al3 =  a3 * (np.dot(b.T,al2))
print(al2)
print(al3)
print(np.log(sum(al3)))