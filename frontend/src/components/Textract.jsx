import { useState } from 'react';
import { Container, Form, Button, Card } from 'react-bootstrap';
import axios from 'axios';

const baseURL = "http://travelapplicationelb-719830694.us-west-2.elb.amazonaws.com:8080";

const Textract = () => {
    const [file, setFile] = useState(null);
    const [responseList, setResponseList] = useState([]);
    const [copied, setCopied] = useState(false);

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
        setResponseList([]); // Clear response list when a new file is selected
    };

    const handleUpload = async () => {
        // Check if no file is selected
        if (!file) {
            console.log('No file selected.');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axios.post(`${baseURL}/file/uploadTextractFile`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            console.log(response);
            // Assuming the response is an array of strings
            setResponseList(response.data);
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    };

    const handleClear = () => {
        setFile(null);
        setResponseList([]);
        setCopied(false); // Reset the copied state
    };

    const handleCopy = () => {
        const textarea = document.getElementById('responseTextArea');
        textarea.select();
        document.execCommand('copy');
        setCopied(true);
    };

    return (
        <Container className='mt-4'>
            <h1>Extract Text From Images</h1>
            <Form>
                <Form.Group controlId="formFile" className="mb-3">
                    <Form.Label>Select a file</Form.Label>
                    <Form.Control type="file" onChange={handleFileChange} />
                </Form.Group>

                <Button variant="primary" onClick={handleUpload} disabled={!file} style={{marginRight: 6}}>
                    Upload
                </Button>

                <Button variant="secondary" onClick={handleClear}>
                    Clear
                </Button>
            </Form>

            {file && (
                <Card className="mt-4" style={{ width: '18rem' }}>
                    <Card.Img variant="top" src={URL.createObjectURL(file)} alt="Selected File" />
                    <Card.Body>
                        <Card.Title>Image Preview</Card.Title>
                    </Card.Body>
                </Card>
            )}

            {responseList.length > 0 && (
                <div className="mt-4">
                    <h2>Text Extraction (OCR)</h2>

                    <Form.Group controlId="responseTextArea" className="mt-4">
                        <Form.Label>Response Text</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={responseList.length}
                            value={responseList.join('\n')}
                            readOnly
                        />
                        <Button
                            variant="primary"
                            onClick={handleCopy}
                            className="mt-2"
                            disabled={responseList.length === 0 || copied}
                        >
                            {copied ? 'Copied!' : 'Copy'}
                        </Button>
                    </Form.Group>
                </div>
            )}
        </Container>
    );
};

export default Textract;