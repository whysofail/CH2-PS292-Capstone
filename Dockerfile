FROM python:3.9-slim

ENV PYTHONUNBUFFERED True

ENV APP_HOME /app
WORKDIR $APP_HOME
COPY . .

RUN pip install --no-cache-dir -r requirements.txt
RUN [ "python3", "-c", "import nltk; nltk.download('punkt', download_dir='/usr/local/nltk_data')" ]
RUN [ "python3", "-c", "import nltk; nltk.download('wordnet', download_dir='/usr/local/nltk_data')" ]

CMD exec gunicorn --bind :$PORT --workers 1 --threads 8 --timeout 0 main:app