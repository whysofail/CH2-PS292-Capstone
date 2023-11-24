const path = require('path')
const { Storage } = require('@google-cloud/storage');

const keyPath = path.join(__dirname, '../helper/lawmate-gcp-70d490310c95.json')
const { GCP_PROJECT_ID, GCP_BUCKET_NAME } = process.env;
const bucketName = GCP_BUCKET_NAME;
const storage = new Storage({ keyFilename: keyPath });
const bucket = storage.bucket(bucketName);

const checkBucket = async () => {
    try {
        await bucket.get({
            userProject : GCP_PROJECT_ID
        })
        console.log(`${bucketName} in ${GCP_PROJECT_ID} is exists.`)        
    } catch (error) {
        console.error(error)
    }

  };

const main = async () => {
  try {
    await checkBucket()
  } catch (error) {
    console.error(error);
  }

};

main();
