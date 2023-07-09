import argparse
import numpy as np
from environment import MountainCar


parser = argparse.ArgumentParser()
parser.add_argument('mode')
parser.add_argument('weight_out')
parser.add_argument('returns_out')
parser.add_argument('episodes', type = int)
parser.add_argument('max_iterations', type = int)
parser.add_argument('epsilon', type = float)
parser.add_argument('gamma', type = float)
parser.add_argument('learning_rate', type = float)

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
        if self.indices:     # sparse state vector 2048 
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
            reward_total = 0
            iteration = 0
            state = self.env.reset()    # Initialize state dict
            while iteration < max_iterations:
                action = self.get_action(state)
                state_next, reward, done = self.env.step(action)   
                reward_total += reward
                Q_values_next, state_arr_next = self.lm.predict(state_next)
                target = reward + self.gamma * max(Q_values_next)

                self.lm.update(state, action, target)
                state = state_next
                         
                if done:
                    break
                iteration += 1
                
            episode += 1
            returns.append(reward_total)
            
        return returns

def main(args):
    # run parser and get parameters values
    env = MountainCar(args.mode)
    agent = QLearningAgent(env, args.mode, args.gamma, args.learning_rate, args.epsilon)
    returns = agent.train(args.episodes, args.max_iterations)
    
    # output
    with open(args.weight_out, 'w') as f_weight:
        f_weight.writelines("{}\n".format(agent.lm.bais))
        for i in range(len(agent.lm.weights)):
            for j in range(len(agent.lm.weights[i])):
                f_weight.writelines("{}\n".format(agent.lm.weights[i][j]))

    with open(args.returns_out, 'w') as f_returns:
         for k in range(len(returns)):
             f_returns.writelines("{}\n".format(returns[k]))
             
         
if __name__ == "__main__":
    args = parser.parse_args()
    main(args)     
