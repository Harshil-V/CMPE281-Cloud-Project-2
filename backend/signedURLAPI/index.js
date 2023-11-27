const AWS = require('aws-sdk');

exports.handler = async (event) => {
    // Parse the JSON string from the request body
    const requestBody = event;
   
    // Extract bucket and file name from the request body
    const { bucket, fileName } = requestBody;

    // Set up the CloudFront and S3 clients
    const cloudfront = new AWS.CloudFront();
    const s3 = new AWS.S3();

    try {
        // Get the pre-signed CloudFront URL
        const signedUrl = await s3.getSignedUrl('getObject', {
            Bucket: bucket,
            Key: fileName,
            Expires: 3600,
        });

        // Return the signed URL in the response
        return signedUrl;

    } catch (error) {
        console.error('Error generating pre-signed CloudFront URL:', error);

        // Return an error response
        return {
            statusCode: 500,
            body: JSON.stringify({ message: 'Error generating pre-signed CloudFront URL' }),
        };
    }
};