
import random
import tensorflow as tf
import numpy as np
import pickle
import json
import nltk
import os
import random
import requests

from io import BytesIO
from PIL import Image
from nltk.stem import WordNetLemmatizer
from keras.models import load_model
from flask import Flask, request, jsonify
from tensorflow.keras.preprocessing.image import img_to_array

app = Flask(__name__)

nltk.download('punkt')
nltk.download('wordnet')
lemmatizer = WordNetLemmatizer()
file_path = os.path.join('.','app','dataset','dataset.json')
words_path = os.path.join('.','app','models', 'words.pkl')
classes_path = os.path.join('.','app','models', 'classes.pkl')
model_path =  os.path.join('.','app','models', 'model_chatbot.h5')
model_image_path = os.path.join('.', 'app', 'models', 'model_image.h5')

words = pickle.load(open(words_path, 'rb'))
classes = pickle.load(open(classes_path , 'rb'))
model = load_model(model_path)
model_image = load_model(model_image_path)

with open(file_path, 'r', encoding='utf-8') as file:
  intents_json = json.load(file)

@app.route('/', methods=['GET'])
def index():
    return jsonify({"msg": "LawMate Chatbot status : up"})

@app.route('/chat', methods=['POST'])
def predict():
    data = request.get_json()
    user_input = data['user_input']

    intents = predict_class(user_input)
    response = get_response(intents, intents_json)[1]
    tag = get_response(intents, intents_json)[0]

    return jsonify({"msg": response,
                   "tag": tag})

@app.route('/images', methods=['POST'])
def image_predict():
    data = request.get_json()
    image_url = data['image_url']

    preprocessed_image = preprocess_image_from_url(image_url)
    prediction = model_image.predict(preprocessed_image)

    
    if prediction[0] > 0.5:
        return jsonify({"msg": "image"})
    else:
        return jsonify({"msg": "chat"})

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

#image processing
def preprocess_image_from_url(image_url):
    try:
        response = requests.get(image_url)
        response.raise_for_status()  # Check for HTTP errors
        img = Image.open(BytesIO(response.content))
        img = img.resize((150, 150))  # Adjust the size as needed
        img_array = img_to_array(img)
        img_array = np.expand_dims(img_array, axis=0)  # Add an extra dimension for batch size
        img_array /= 255.0  # Rescale to the range [0, 1]
        return img_array
    except Exception as e:
        print(f"Error processing image from URL: {e}")
        return None

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=int(os.environ.get("PORT", 8080)))
