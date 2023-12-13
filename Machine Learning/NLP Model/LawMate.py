import random
import tensorflow as tf

import numpy as np
import pickle
import json
import matplotlib.pyplot as plt

import nltk
nltk.download('punkt')
nltk.download('wordnet')
from nltk.stem import WordNetLemmatizer

import string
nltk.download()
from nltk.tokenize import word_tokenize 
from nltk.corpus import stopwords
from nltk.probability import FreqDist
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory

import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from keras.models import Sequential
from keras.layers import Dense, Activation, Dropout
from tensorflow.keras.optimizers.legacy import SGD
lemmatizer = WordNetLemmatizer()

words = []
classes = []
documents = []
ignore_words = ['?', '!', '.', ',']
file_path = '../Dataset/Dataset.json'
stop_words = '../Dataset/combined_stop_words.txt'

with open(stop_words, 'r', encoding='utf-8') as file:
  stop_word = file.read().splitlines()

with open(file_path, 'r', encoding='utf-8') as file:
  data_json = json.load(file)

def preprocess_words(sentence):
  lowercase_sentence = sentence.lower()
  print("casefolding: ",lowercase_sentence)

  lowercase_sentence = lowercase_sentence.translate(str.maketrans("","",string.punctuation))
  lowercase_sentence = lowercase_sentence.strip()
  tokens = nltk.tokenize.word_tokenize(lowercase_sentence)

  # print("tokens result: " ,tokens)
  freq_tokens = nltk.FreqDist(tokens)

  # print('Frequency Tokens : \n') 
  # print(freq_tokens.most_common())

  list_stopwords = set(stopwords.words('indonesian'))

  tokens_without_stopwords = [word for word in freq_tokens if not word in list_stopwords]

  # print(tokens_without_stopwords)

  factory = StemmerFactory()
  stemmer = factory.create_stemmer()

  list_tokens = tokens_without_stopwords

  output   = [(token + " : " + stemmer.stem(token)) for token in list_tokens]

  output

  return tokens_without_stopwords

for intent in data_json['intents']:
  for pattern in intent['patterns']:
    w = nltk.word_tokenize(pattern)
    # w = preprocess_words(pattern)
    words.extend(w)
    documents.append((w, intent['tag']))

    if intent['tag'] not in classes:
      classes.append(intent['tag'])

words = [lemmatizer.lemmatize(w.lower()) for w in words if w not in ignore_words] 
words = sorted(list(set(words)))

classes = sorted(list(set(classes)))

print(len(documents), "documents")
print(len(classes), "classes", classes)
print(len(words), "unique lemmatized words", words)

pickle.dump(words, open('words.pkl', 'wb'))
pickle.dump(classes, open('classes.pkl', 'wb'))

training = []
output_empty = [0] * len(classes)

for document in documents:
  bag = []
  word_patterns = document[0]
  word_patterns = [lemmatizer.lemmatize(word.lower()) for word in word_patterns]

  for w in words:
    bag.append(1) if w in word_patterns else bag.append(0)

  output_row = list(output_empty)
  output_row[classes.index(document[1])] = 1
  training.append([bag, output_row])

random.shuffle(training)
training = np.array(training, dtype=object)

X = list(training[:, 0])
Y = list(training[:, 1])

X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size=0.2, random_state=1)
X_train, X_test, y_train, y_test = train_test_split(X_train, y_train, test_size=0.2, random_state=1)

print('Data Training has been created')

model = Sequential()
model.add(Dense(128, input_shape=(len(X_train[0]),), activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(64, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(len(y_train[0]), activation='softmax'))

sgd = SGD(lr=1, decay=1e-6, momentum=0.9, nesterov=True)
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

history = model.fit(np.array(X_train), np.array(y_train), epochs=200, batch_size=32, verbose=1, validation_data=(X_test, y_test))
model.save('model_chatbot.h5', history)

print(history.history.keys())

loss_train = history.history['loss']
loss_val = history.history['val_loss']
epochs = range(200)
plt.plot(epochs, loss_train, 'g', label='Training loss')
plt.plot(epochs, loss_val, 'b', label='validation loss')
plt.title('Training and Validation loss')
plt.xlabel('Epochs')
plt.ylabel('Loss')
plt.legend()
plt.show()

loss_train = history.history['accuracy']
loss_val = history.history['val_accuracy']
epochs = range(200)
plt.plot(epochs, loss_train, 'g', label='Training accuracy')
plt.plot(epochs, loss_val, 'b', label='validation accuracy')
plt.title('Training and Validation accuracy')
plt.xlabel('Epochs')
plt.ylabel('Accuracy')
plt.legend()
plt.show()

model.summary()

print("model created")