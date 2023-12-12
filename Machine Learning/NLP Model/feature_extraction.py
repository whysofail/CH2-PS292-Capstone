import nltk
import string
nltk.download()
from nltk.tokenize import word_tokenize 
from nltk.corpus import stopwords
from nltk.probability import FreqDist
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory


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


sentences = "saya adalah korban pelecehan seksual "
test = preprocess_words(sentences)

print(test)





