import tensorflow as tf
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import numpy as np
import random
import os
import requests
from PIL import Image
from io import BytesIO

from tensorflow.keras.preprocessing.image import ImageDataGenerator, load_img, img_to_array
from tensorflow.keras.optimizers import RMSprop
from keras.models import load_model

model = load_model('model_image.h5')

# Function to preprocess the uploaded image
def preprocess_image(image_path):
    img = load_img(image_path, target_size=(150, 150))  # Adjust target_size as needed
    img_array = img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)  # Add an extra dimension for batch size
    img_array /= 255.0  # Rescale to the range [0, 1]
    return img_array

# Function to preprocess the uploaded image from url
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

image_url = "https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg"
preprocessed_image = preprocess_image_from_url(image_url)

# Example usage
# uploaded_image_path = r"C:\Users\LENOVO\Desktop\CH2-PS292-Capstone\Machine Learning\Dataset\Images\Screenshot 2023-12-07 020954.png"
# preprocessed_image = preprocess_image(uploaded_image_path)

# Make a prediction using the trained model
prediction = model.predict(preprocessed_image)

# Display the prediction
if prediction[0] > 0.5:
    print("The model predicts the image is in the 'image' class.")
else:
    print("The model predicts the image is in the 'chat' class.")