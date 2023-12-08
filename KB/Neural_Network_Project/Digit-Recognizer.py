import numpy as np
import pandas as pd
from PIL import Image
from matplotlib import pyplot as plt
from matplotlib import image as mpimg



data = pd.read_csv('Digit-Recognizer_Dataset/train.csv')

data = np.array(data)
m, n = data.shape
np.random.shuffle(data)

data_dev = data[0:1000].T
Y_dev = data_dev[0]
X_dev = data_dev[1:n]
X_dev = X_dev / 255.

data_train = data[1000: m].T
Y_train = data_train[0]
X_train = data_train[1:n]
X_train = X_train / 255.
_, m_train = X_train.shape


def init_params():
    W1 = np.random.rand(10, 784) - 0.5
    b1 = np.random.rand(10, 1) - 0.5
    W2 = np.random.rand(10, 10) - 0.5
    b2 = np.random.rand(10, 1) - 0.5
    return W1, b1, W2, b2


def ReLU(Z):
    return np.maximum(Z, 0)


def softmax(Z):
    A = np.exp(Z) / sum(np.exp(Z))
    return A


def forward_prop(W1, b1, W2, b2, X):
    # W1 = Weight dari semua hubungan antara Input layer dengan Hidden Layer pertama
    # b1 = Bias dari Weight
    # A1 = Hasil dari Activation Function (Ex : Sigmoid, ReLU, Tan function)
    # Z2 = Weight dari semua hubungan antara Hidden layer kedua dengan hidden layer pertama
    # b2 = Bias dari weight 2
    # A2 = Hasil dari activation function untuk output yang namanya Softmax
    Z1 = W1.dot(X) + b1
    A1 = ReLU(Z1)
    Z2 = W2.dot(A1) + b2
    A2 = softmax(Z2)
    return Z1, A1, Z2, A2


def one_hot(Y):
    one_hot_Y = np.zeros((Y.size, Y.max() + 1))
    one_hot_Y[np.arange(Y.size), Y] = 1
    one_hot_Y = one_hot_Y.T
    return one_hot_Y


def deriv_ReLU(Z):
    return Z > 0


def back_prop(Z1, A1, Z2, A2, W2, X, Y):
    m = Y.size
    one_hot_Y = one_hot(Y)
    dZ2 = A2 - one_hot_Y
    dW2 = 1 / m * dZ2.dot(A1.T)
    db2 = 1 / m * np.sum(dZ2)
    dZ1 = W2.T.dot(dZ2) * deriv_ReLU(Z1)
    dW1 = 1 / m * dZ1.dot(X.T)
    db1 = 1 / m * np.sum(dZ1)
    return dW1, db1, dW2, db2


def update_params(W1, b1, W2, b2, dW1, db1, dW2, db2, alpha):
    W1 = W1 - alpha * dW1
    b1 = b1 - alpha * db1
    W2 = W2 - alpha * dW2
    b2 = b2 - alpha * db2
    return W1, b1, W2, b2


def get_predictions(A2):
    print("A2: ", A2)
    return np.argmax(A2, 0)


def get_accuracy(predictions, Y):
    print(predictions, Y)
    return np.sum(predictions == Y) / Y.size


def getSavedWeights():
    W1 = np.load('Digit-Recognizer_Dataset/numpy-array/W1.npy')
    b1 = np.load('Digit-Recognizer_Dataset/numpy-array/b1.npy')
    W2 = np.load('Digit-Recognizer_Dataset/numpy-array/W2.npy')
    b2 = np.load('Digit-Recognizer_Dataset/numpy-array/b2.npy')
    return W1, b1, W2, b2


def setWeights(W1, b1, W2, b2):
    np.save('Digit-Recognizer_Dataset/numpy-array/W1.npy', W1)
    np.save('Digit-Recognizer_Dataset/numpy-array/W2.npy', W2)
    np.save('Digit-Recognizer_Dataset/numpy-array/b1.npy', b1)
    np.save('Digit-Recognizer_Dataset/numpy-array/b2.npy', b2)


def gradient_descent_init(X, Y, iterations, alpha):
    W1, b1, W2, b2 = init_params()
    for i in range(iterations):
        Z1, A1, Z2, A2 = forward_prop(W1, b1, W2, b2, X)
        dW1, db1, dW2, db2 = back_prop(Z1, A1, Z2, A2, W2, X, Y)
        W1, b1, dW2, db2 = update_params(W1, b1, W2, b2, dW1, db1, dW2, db2, alpha)
        if (i % 100 == 0):
            print("Iteration: ", i)
            print("Accuracy: ", get_accuracy(get_predictions(A2), Y))
    setWeights(W1, b1, W2, b2)


def gradient_descent(X, Y, iterations, alpha):
    W1, b1, W2, b2 = getSavedWeights()
    for i in range(iterations):
        Z1, A1, Z2, A2 = forward_prop(W1, b1, W2, b2, X)
        dW1, db1, dW2, db2 = back_prop(Z1, A1, Z2, A2, W2, X, Y)
        W1, b1, W2, b2 = update_params(W1, b1, W2, b2, dW1, db1, dW2, db2, alpha)
        if i % 100 == 0:
            print("Iteration: ", i)
            print("Accuracy: ", get_accuracy(get_predictions(A2), Y))
        if i % 1000 == 0:
            setWeights(W1, b1, W2, b2)
    setWeights(W1, b1, W2, b2)



# gradient_descent(X_train, Y_train, 1, 0.10)

# gradient_descent_init(X_train, Y_train, 50, 0.1)
#
# np.save('Digit-Recognizer_Dataset/numpy-array/W1.npy', W1)
# np.save('Digit-Recognizer_Dataset/numpy-array/W2.npy', W2)
# np.save('Digit-Recognizer_Dataset/numpy-array/b1.npy', b1)
# np.save('Digit-Recognizer_Dataset/numpy-array/b2.npy', b2)


def make_predictions(X, W1, b1, W2, b2):
    _, _, _, A2 = forward_prop(W1, b1, W2, b2, X)
    predictions = get_predictions(A2)
    return predictions


def test_prediction(index, W1, b1, W2, b2 ,count = 0):
    current_image = X_train[:, index, None]
    print(current_image)
    prediction = make_predictions(X_train[:, index, None], W1, b1, W2, b2)
    A2 = forward_prop(W1, b1, W2, b2, current_image)
    label = Y_train[index]
    print("Prediction: ", prediction)
    print("Label: ", label)
    print()

    current_image = current_image.reshape((28, 28)) * 255
    plt.gray()
    plt.imshow(current_image, interpolation='nearest')
    plt.show()


def test_prediction_image(index, W1, b1, W2, b2,image ,count = 0):
    im = Image.open(image).convert('L')
    newsize = (28, 28)
    im1 = im.resize(newsize)
    flat = np.array(im1)
    flat_im = flat.flatten()

    for i in range(len(flat_im)):
        flat_im[i] = abs(flat_im[i] - 255)
    flat_im = flat_im/255.
    flat_im = flat_im.T
    print(flat_im)
    current_image = flat_im
    prediction = make_predictions(current_image, W1, b1, W2, b2)
    A2 = forward_prop(W1, b1, W2, b2, current_image)
    # label = Y_train[index]
    print("Prediction: ", prediction)
    # print("Label: ", label)
    print()

    current_image = current_image.reshape((28, 28)) * 255
    plt.gray()
    plt.imshow(current_image, interpolation='nearest')
    plt.show()



W1, b1, W2, b2 = getSavedWeights()
# Z1, A1, Z2, A2 = forward_prop(W1, b1, W2, b2, X_train)
# dW1, db1, dW2, db2 = back_prop(Z1, A1, Z2, A2, W2, X_train, Y_train)
# print("Accuracy: ", get_accuracy(get_predictions(A2), Y_train))
# for i in range(1):
#     test_prediction(i, W1, b1, W2, b2)
path = 'Digit-Recognizer_Dataset/numpy-array/Untitled2.png'
test_prediction_image(0,W1,b1,W2,b2,path)
