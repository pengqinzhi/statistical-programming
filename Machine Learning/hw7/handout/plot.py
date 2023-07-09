import matplotlib.pyplot as plt
import numpy as np
plt.rcParams["axes.unicode_minus"]  = False

# plot
x = np.log([10,100,1000,10000])
y1 = [-80.54472148949795, -74.9944493589363, -67.59317395746366, -61.085620923344955]
y2 = [-87.97153090660557, -80.83229271827624, -70.45566558750309,  -61.085620923344955]

plt.title('Train vs Validation Average Log-Likelihood')
plot_y1 = plt.plot(x , y1, '-b', label = 'Train Average Log-Likelihood')
plot_y2 = plt.plot(x , y2 , ':r', label = 'Validation Average Log-Likelihood')

plt.xlabel('Number of Sequences (log)')
plt.ylabel('Average Log-Likelihood')
plt.xlim((0, np.log(10000)+np.log(10)))
plt.ylim((-50, -100-5))
plt.xticks(np.arange(np.log(10), np.log(10000)+1, np.log(10)))
plt.yticks(np.arange(-50, -101, -10))
plt.legend()
plt.show()
