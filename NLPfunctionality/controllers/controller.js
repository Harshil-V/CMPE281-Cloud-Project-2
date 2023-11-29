const config = require('../config');
const AWS = require('aws-sdk');

// Initialize AWS
async function initializeAWS() {
  try {
    const secrets = await config();

    AWS.config.update({
      accessKeyId: secrets.AWS_ACCESS_KEY_ID,
      secretAccessKey: secrets.AWS_SECRET_ACCESS_KEY,
      region: secrets.AWS_DEFAULT_REGION,
    });

    // Initialize AWS services using fetched credentials
    const translate = new AWS.Translate();
    const Polly = new AWS.Polly();
    const Comprehend = new AWS.Comprehend();

    return { translate, Polly, Comprehend };
  } catch (error) {
    console.error('Error initializing AWS:', error);
    throw error;
  }
}

// Other controller functions using AWS services
async function translateText(text, language) {
  try {
    const { translate } = await initializeAWS();

    const translateParams = {
      Text: text,
      SourceLanguageCode: 'auto',
      TargetLanguageCode: language,
    };

    const translatedData = await translate.translateText(translateParams).promise();
    return translatedData.TranslatedText;
  } catch (error) {
    throw error;
  }
}

async function synthesizeSpeech(translatedText) {
  try {
    const { Polly } = await initializeAWS();

    const speechParams = {
      OutputFormat: 'mp3',
      Text: translatedText,
      VoiceId: 'Joanna'
    };

    const pollyResponse = await Polly.synthesizeSpeech(speechParams).promise();
    return pollyResponse.AudioStream;
  } catch (error) {
    throw error;
  }
}

async function analyzeSentiment(text) {
  try {
    const { Comprehend } = await initializeAWS();

    const sentimentParams = {
      LanguageCode: 'en',
      Text: text
    };

    const sentimentData = await Comprehend.detectSentiment(sentimentParams).promise();
    return sentimentData.Sentiment;
  } catch (error) {
    throw error;
  }
}

module.exports = {
  translateText,
  synthesizeSpeech,
  analyzeSentiment
};
