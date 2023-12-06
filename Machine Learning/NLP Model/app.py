import random
import tensorflow as tf
import numpy as np
import pickle
import json
import nltk
from nltk.stem import WordNetLemmatizer
from keras.models import load_model

nltk.download('punkt')
nltk.download('wordnet')
lemmatizer = WordNetLemmatizer()
file_path = '../Dataset/Dataset.json'

words = pickle.load(open('words.pkl', 'rb'))
classes = pickle.load(open('classes.pkl', 'rb'))
model = load_model('model_chatbot.h5')
with open(file_path, 'r', encoding='utf-8') as file:
  intents_json = json.load(file)
def clean_up_sentence(sentence):
    sentence_words = nltk.word_tokenize(sentence)
    sentence_words = [lemmatizer.lemmatize(word) for word in sentence_words]
    return sentence_words

def bag_of_words(sentence):
    sentence_words = clean_up_sentence(sentence)
    bag = [0] * len(words)
    for w in sentence_words:
        for i, word in enumerate(words):
            if word == w:
                bag[i] = 1
    return np.array(bag)

def predict_class(sentence):
    bow = bag_of_words(sentence)
    res = model.predict(np.array([bow]))[0]
    ERROR_THRESHOLD = 0.25
    results = [[i, r] for i, r in enumerate(res) if r > ERROR_THRESHOLD]

    results.sort(key=lambda x: x[1], reverse=True)
    return_list = [{'intent': classes[r[0]], 'probability': str(r[1])} for r in results]
    return return_list

def get_response(intents_list, intents_json):
    tag = intents_list[0]['intent']
    list_of_intents = intents_json['intents']
    for i in list_of_intents:
        if i['tag'] == tag:
            result = random.choice(i['responses'])
            break
    return tag, result

while True:
    user_input = input("You: ")
    if user_input.lower() == 'exit':
        break

    intents = predict_class(user_input)
    response = get_response(intents, intents_json)[1]
    tag = get_response(intents, intents_json)[0]

    print(f"ChatBot: {response} \n Tag: {tag}")
