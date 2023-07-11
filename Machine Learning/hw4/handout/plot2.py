import matplotlib.pyplot as plt
import numpy


def lr_model_train() -> ([], []):
    learning_rate = 0.01

    data_input = numpy.genfromtxt(mod1_train_input, delimiter="\t")

    X_value = data_input[:, 1:]
    X_value = numpy.column_stack((numpy.ones(len(X_value)), X_value))

    w = data_input.shape[1]
    N = data_input.shape[0]

    data_input_valid = numpy.genfromtxt(mod1_validation_input, delimiter="\t")

    X_value_valid = data_input_valid[:, 1:]
    X_value_valid = numpy.column_stack((numpy.ones(len(X_value_valid)), X_value_valid))

    w_valid = data_input_valid.shape[1]
    N_valid = data_input_valid.shape[0]

    # init theta
    theta_trained = numpy.zeros(w)
    j_value_train = []
    j_value_valid = []

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

        sum_valid = 0
        for row, X in enumerate(X_value_valid):
            y_i = data_input_valid[row][0]
            a = numpy.dot(theta_trained, X)
            sum_valid += -y_i * a + numpy.log(1 + numpy.exp(a))
        average_valid = sum_valid / N_valid
        j_value_valid.append(average_valid)

    return j_value_train, j_value_valid


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

    mod2_train_input = "largeoutput\model2_formatted_train.tsv"
    mod2_validation_input = "largeoutput\model2_formatted_valid.tsv"
    num_epoch = 5000

    x_axis = range(num_epoch)

    j_value = lr_model_train()
    plt.title('Model 1 training NNL vs validation NNL')
    plt.plot(x_axis, j_value[0], '-b', label = 'training NNL')
    plt.plot(x_axis, j_value[1], '--g', label = 'validation NNL')
    plt.xlabel('epochs')
    plt.ylabel('Negative Log Likelihood')
    plt.xlim((- 100, num_epoch+100))
    plt.ylim((0, 1))
    plt.xticks(numpy.arange(0, num_epoch+1, num_epoch / 10))
    plt.yticks(numpy.arange(0, 1+0.01, 0.1))
    plt.legend()

    plt.show()



