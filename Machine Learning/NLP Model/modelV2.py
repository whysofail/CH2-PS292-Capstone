# import json
# import tensorflow as tf
# from tensorflow.keras.preprocessing.text import Tokenizer
# from tensorflow.keras.preprocessing.sequence import pad_sequences
# from sklearn.model_selection import train_test_split
# import numpy as np

# file_path = '../Dataset/Dataset.json'
# # Your dataset
# with open(file_path, 'r', encoding='utf-8') as file:
#     dataset = json.load(file)

# # Prepare data
# texts = []
# labels = []
# for intent in dataset["intents"]:
#     for pattern in intent["patterns"]:
#         texts.append(pattern)
#         labels.append(intent["tag"])

# # Tokenize the text
# tokenizer = Tokenizer(oov_token="<OOV>")
# tokenizer.fit_on_texts(texts)
# word_index = tokenizer.word_index

# # Convert text to sequences
# sequences = tokenizer.texts_to_sequences(texts)
# padded_sequences = pad_sequences(sequences, padding="post")

# # Convert labels to one-hot encoding
# label_set = list(set(labels))
# label_dict = {label: i for i, label in enumerate(label_set)}
# labels = [label_dict[label] for label in labels]
# one_hot_labels = tf.keras.utils.to_categorical(labels, num_classes=len(label_set))

# # Split the data into training and testing sets
# X_train, X_test, y_train, y_test = train_test_split(padded_sequences, one_hot_labels, test_size=0.2, random_state=42)

# # Build the model
# model = tf.keras.Sequential([
#     tf.keras.layers.Embedding(len(word_index) + 1, 32, input_length=padded_sequences.shape[1]),
#     tf.keras.layers.GlobalAveragePooling1D(),
#     tf.keras.layers.Dense(256, activation="relu"),
#     tf.keras.layers.Dense(128, activation="relu"),
#     tf.keras.layers.Dense(64, activation="relu"),
#     tf.keras.layers.Dense(len(label_set), activation="softmax")
# ])

# model.compile(loss="categorical_crossentropy", optimizer="adam", metrics=["accuracy"])

# # Train the model
# model.fit(X_train, y_train, epochs=100, validation_data=(X_test, y_test))

# # Example usage
# user_input = "saya mengalami pelecehan seksual"
# sequence = pad_sequences(tokenizer.texts_to_sequences([user_input]), padding="post", maxlen=padded_sequences.shape[1])
# prediction = model.predict(sequence)
# predicted_label = label_set[np.argmax(prediction)]
# response = [intent["responses"] for intent in dataset["intents"] if intent["tag"] == predicted_label][0][0]

# print(response)
import json
import tensorflow as tf
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from sklearn.model_selection import train_test_split
import numpy as np


file_path = '../Dataset/Dataset.json'
# Load your dataset from a JSON file
with open(file_path, 'r', encoding='utf-8') as file:
    dataset = json.load(file)

# Prepare data
texts = []
labels = []
for intent in dataset["intents"]:
    for pattern in intent["patterns"]:
        texts.append(pattern)
        labels.append(intent["tag"])

# Tokenize the text
tokenizer = Tokenizer(oov_token="<OOV>")
tokenizer.fit_on_texts(texts)
word_index = tokenizer.word_index

# Convert text to sequences
sequences = tokenizer.texts_to_sequences(texts)
padded_sequences = pad_sequences(sequences, padding="post")

# Convert labels to one-hot encoding
label_set = list(set(labels))
label_dict = {label: i for i, label in enumerate(label_set)}
labels = [label_dict[label] for label in labels]
one_hot_labels = tf.keras.utils.to_categorical(labels, num_classes=len(label_set))

# Split the data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(padded_sequences, one_hot_labels, test_size=0.2, random_state=42)

# Build the model with LSTM layers
model = tf.keras.Sequential([
    tf.keras.layers.Embedding(len(word_index) + 1, 32, input_length=padded_sequences.shape[1]),
    tf.keras.layers.Bidirectional(tf.keras.layers.LSTM(128, return_sequences=True)),
    tf.keras.layers.Bidirectional(tf.keras.layers.LSTM(128)),
    tf.keras.layers.Dense(128, activation="relu"),
    tf.keras.layers.Dropout(0.5),
    tf.keras.layers.Dense(len(label_set), activation="softmax")
])

model.compile(loss="categorical_crossentropy", optimizer='adam', metrics=["accuracy"])

# Train the model
model.fit(X_train, y_train, epochs=200, validation_data=(X_test, y_test))

# Example usage
user_input = "saya mengalami pelecehan seksual"
sequence = pad_sequences(tokenizer.texts_to_sequences([user_input]), padding="post", maxlen=padded_sequences.shape[1])
prediction = model.predict(sequence)
predicted_label = label_set[np.argmax(prediction)]
response = [intent["responses"] for intent in dataset["intents"] if intent["tag"] == predicted_label][0][0]

print(user_input + '\n')
print(response)
