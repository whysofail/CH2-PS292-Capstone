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

#load data
base_dir = '../Dataset/Images'
print('Content of base directory : ')
print(os.listdir(base_dir))

print("\nContents of train directory:")
print(os.listdir(f'{base_dir}/train'))

print("\nContents of validation directory:")
print(os.listdir(f'{base_dir}/validation'))

train_dir = os.path.join(base_dir, 'train')
test_dir = os.path.join(base_dir, 'validation')

train_chat_dir = os.path.join(train_dir, 'chat')
train_image_dir = os.path.join(train_dir, 'images')

test_chat_dir = os.path.join(test_dir, 'chat')
test_image_dir = os.path.join(test_dir, 'images')

train_chat_fnames = os.listdir(train_chat_dir)
train_image_fnames = os.listdir(train_image_dir)

print(train_chat_fnames[:10])
print(train_image_fnames[:10])

print('total training chat: ', len(os.listdir(train_chat_dir)))
print('total training image: ', len(os.listdir(train_image_dir)))

print('total validation chat: ', len(os.listdir(test_chat_dir)))
print('total validation image: ', len(os.listdir(test_image_dir)))


nrows = 4
ncols = 4

pic_index = 0

fig = plt.gcf()
fig.set_size_inches(ncols*4, nrows*4)

pic_index+=8

next_chat_pix = [os.path.join(train_chat_dir, fname)
                 for fname in train_chat_fnames[pic_index-8:pic_index]
                 ]

next_image_pix = [os.path.join(train_image_dir, fname)
                  for fname in train_image_fnames[pic_index-8:pic_index]
                  ]

for i, img_path in enumerate(next_chat_pix+next_image_pix):
  sp = plt.subplot(nrows, ncols, i + 1)
  sp.axis('off')

  img = mpimg.imread(img_path)
  plt.imshow(img)

plt.show()

model = tf.keras.models.Sequential([
  # Note the input shape is the desired size of the image 150x150 with 3 bytes color
    tf.keras.layers.Conv2D(16, (3,3), activation='relu', input_shape=(150, 150, 3)),
    tf.keras.layers.MaxPooling2D(2,2),
    tf.keras.layers.Conv2D(32, (3,3), activation='relu'),
    tf.keras.layers.MaxPooling2D(2,2), 
    tf.keras.layers.Conv2D(64, (3,3), activation='relu'), 
    tf.keras.layers.MaxPooling2D(2,2),
    # Flatten the results to feed into a DNN
    tf.keras.layers.Flatten(), 
    # 512 neuron hidden layer
    tf.keras.layers.Dense(512, activation='relu'), 
    # Only 1 output neuron. It will contain a value from 0-1 where 0 for 1 class ('cats') and 1 for the other ('dogs')
    tf.keras.layers.Dense(1, activation='sigmoid') 
])

model.summary()

model.compile(optimizer=RMSprop(learning_rate=0.01),
              loss='binary_crossentropy',
              metrics = ['accuracy'])

# All images will be rescaled by 1./255.
train_datagen = ImageDataGenerator( rescale = 1.0/255. )
test_datagen  = ImageDataGenerator( rescale = 1.0/255. )

# --------------------
# Flow training images in batches of 20 using train_datagen generator
# --------------------
train_generator = train_datagen.flow_from_directory(train_dir,
                                                    batch_size=20,
                                                    class_mode='binary',
                                                    target_size=(150, 150))     
# --------------------
# Flow validation images in batches of 20 using test_datagen generator
# --------------------
validation_generator =  test_datagen.flow_from_directory(test_dir,
                                                         batch_size=20,
                                                         class_mode  = 'binary',
                                                         target_size = (150, 150))

history = model.fit(
            train_generator,
            epochs=10,
            validation_data=validation_generator,
            verbose=2
            )

model.save('model_image.h5', history)
print('model created')

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

image_url = "https://i.pinimg.com/564x/0c/8d/54/0c8d54a53e8f3566a7bbcc15dbe3373e.jpg"
preprocessed_image = preprocess_image_from_url(image_url)

# Example usage
# uploaded_image_path = r"C:\Users\LENOVO\Desktop\CH2-PS292-Capstone\Machine Learning\Dataset\Images\Screenshot 2023-12-07 020954.png"
# preprocessed_image = preprocess_image(uploaded_image_path)

# Make a prediction using the trained model
prediction = model.predict(preprocessed_image)

print(f"Prediction value: {prediction[0]}")
# Display the prediction
if prediction[0] > 0.5:
    print("The model predicts the image is in the 'image' class.")
else:
    print("The model predicts the image is in the 'chat' class.")