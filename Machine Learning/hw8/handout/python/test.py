import numpy as np
import argparse
from environment import MountainCar


# a = {0: 173, 1: 342, 2: 666, 3: 506, 4: 94}
# b= np.array(list(a.values()))
# state = np.append(1, np.array(list(a.values())))

# env = MountainCar("tile")
# state_arr = np.zeros(300)
# dict=env.transform([0,0])
class LinearModel:
    def __init__(self, state_size: int, action_size: int, lr: float, indices: bool):
        """indices is True if indices are used as input for one-hot features.
        Otherwise, use the sparse representation of state as features
        """
        self.state_size = state_size
        self.action_size = action_size
        self.lr = lr
        self.indices = indices
        self.weights = np.zeros((self.state_size, self.action_size))
        self.bais = 0
    
    def predict(self, state: dict[int, int]):
        """
        Given state, makes predictions.
        """    
        if self.indices:     # sparse state vector 2048 + 1 bais
            state_arr = np.zeros(self.state_size)
            state_arr[list(state.keys())] = 1    
            Q_values = np.dot(state_arr, self.weights) + self.bais   # 3
        else:
            state_arr = np.array(list(state.values()))
            Q_values = np.dot(state_arr, self.weights) + self.bais   # 3
            
        return Q_values, state_arr
    
    def update(self, state: dict[int, int], action: int, target: float):
        """
        Given state, action, and target, update weights.
        """
        Q_values, state_arr = self.predict(state)
        self.weights[:, action] = self.weights[:, action] - self.lr * (Q_values[action] - target) * state_arr
        self.bais = self.bais - self.lr * (Q_values[action] - target)
        print(self.bais)
        

class QLearningAgent:
    def __init__(self, env: MountainCar, mode: str = None, gamma: float = 0.9, lr: float = 0.01, epsilon: float = 0.05):
        self.env = env
        self.mode = mode
        self.gamma = gamma
        self.lr = lr
        self.epsilon = epsilon
        if mode == "tile":
            self.indices = True
        elif mode == 'raw':
            self.indices = False
        self.lm = LinearModel(self.env.state_space, self.env.action_space, lr, self.indices)
               
    def get_action(self, state: dict[int, int]) -> int:
        """epsilon-greedy strategy.
        Given state, returns action.
        """
        if np.random.random() < self.epsilon:
            action = np.random.randint(0, 3)
        else:
            Q_values, state_arr = self.lm.predict(state)
            action = np.argmax(Q_values)
            
        return action
    
    def train(self, episodes: int, max_iterations: int):
        """training function.
        Train for ’episodes’ iterations, where at most ’max_iterations‘ iterations
        should be run for each episode. Returns a list of returns.
        """
        episode = 0       
        returns = []
        while episode < episodes:
            print("***")
            print(episode)
            reward_total = 0
            iteration = 0
            state = self.env.reset()    # Initialize state dict
            while iteration < max_iterations:
                print(iteration)
                action = self.get_action(state)
                
                state_next, reward, done = self.env.step(action)
                print("state_next")
                
                reward_total += reward
                Q_values_next, state_arr_next = self.lm.predict(state_next)
                print(max(Q_values_next))
                target = reward + self.gamma * max(Q_values_next)

                self.lm.update(state, action, target)
                state = state_next
                         
                if done:
                    break
                iteration += 1
                
            episode += 1
            returns.append(reward_total)
            
        return returns

    

# run parser and get parameters values
mode = "tile"
# mode = "raw"
env = MountainCar(mode, 1)
agent = QLearningAgent(env, mode, 0.99, 0.005, 0.0)
returns = agent.train(25,200)

# agent = QLearningAgent(env, mode, 1, 1, 0.0)
# returns = agent.train(1,3)
print(agent.lm.weights)
print(returns)