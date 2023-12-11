FROM python:3.9-slim

ENV PYTHONUNBUFFERED True

ENV APP_HOME /app
WORKDIR $APP_HOME
COPY . .

RUN pip install --upgrade pip
RUN pip install --no-cache-dir -r requirements.txt
RUN [ "python3", "-c", "import nltk; nltk.download('punkt')"]
RUN [ "python3", "-c", "import nltk; nltk.download('wordnet')"]

CMD exec gunicorn --bind :$PORT --workers 1 --threads 8 --timeout 0 main:app