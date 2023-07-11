import matplotlib.pyplot as plt
import numpy 


def lr_model_train(learning_rate) -> ([], []):
    

    data_input = numpy.genfromtxt(mod1_train_input, delimiter="\t")

    X_value = data_input[:, 1:]
    X_value = numpy.column_stack((numpy.ones(len(X_value)), X_value))

    w = data_input.shape[1]
    N = data_input.shape[0]

    # init theta
    theta_trained = numpy.zeros(w)
    j_value_train = []

    for i in range(num_epoch):
        # should be shuffle(but the trait not)
        for row, X in enumerate(X_value):
            y_i = data_input[row][0]
            constant_param = (y_i - sigmoid(numpy.dot(theta_trained, X))) / N * learning_rate
            theta_trained += X * constant_param

        sum_train = 0
        for row, X in enumerate(X_value):
            y_i = data_input[row][0]

            a = numpy.dot(theta_trained, X)
            # pos = sigmoid(numpy.dot(theta_trained, X)) ** y_i
            # neg = (1 - sigmoid(numpy.dot(theta_trained, X))) ** (1 - y_i)
            sum_train += -y_i * a + numpy.log(1 + numpy.exp(a))
        average_train = sum_train / N
        j_value_train.append(average_train)

    return j_value_train


def sigmoid(x: float) -> float:
    temp = numpy.exp(x)
    return temp / (1.0 + temp)


def assess_result_to_file(real_class: [int], classified_class: [int]):
    error_num = 0
    for i in range(len(real_class)):
        if real_class[i] != classified_class[i]:
            error_num += 1
    return error_num / len(real_class)


def write_to_file(result_data: [], out_file_path: str):
    with open(out_file_path, 'w') as file:
        for item in result_data:
            file.write("%s\n" % item)


if __name__ == '__main__':
    mod1_train_input = "largeoutput\model1_formatted_train.tsv"
    mod1_validation_input = "largeoutput/model1_formatted_valid.tsv"

    num_epoch = 5000

    x_axis = range(num_epoch)

    j_value = lr_model_train(0.001)
    j_value2 = lr_model_train(0.01)
    j_value3 = lr_model_train(0.1)

    plt.title('Model 1 training NNL with different learning rate')
    
    plt.plot(x_axis, j_value, '-b', label = 'alpha = 0.001')
    plt.plot(x_axis, j_value2, ':r', label = 'alpha = 0.01')
    plt.plot(x_axis, j_value3, '--g', label = 'alpha = 0.1')
    
    plt.xlabel('epochs')
    plt.ylabel('Negative Log Likelihood')

    plt.xlim((- 100, num_epoch+100))
    plt.ylim((0, 1+0.05))
    plt.xticks(numpy.arange(0, num_epoch+1, num_epoch / 10))
    plt.yticks(numpy.arange(0, 1+0.01, 0.1))
    plt.legend()
    plt.show()
