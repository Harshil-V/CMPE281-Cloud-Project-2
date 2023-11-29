import { useState } from 'react';
import { Container, Form, Button, ListGroup, Card } from 'react-bootstrap';
import axios from 'axios';

const baseURL = "http://travelapplicationelb-719830694.us-west-2.elb.amazonaws.com:8080";

const Textract = () => {
    const [file, setFile] = useState(null);
    const [responseList, setResponseList] = useState([]);

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
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
        setResponseList([]); // Clear the response list
    };

    return (
        <Container className='mt-4'>
            <h1>File Upload Page</h1>
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
                <div className='mt-4'>
                    <h2>Response List</h2>
                    <ListGroup>
                        {responseList.map((item, index) => (
                            <ListGroup.Item key={index}>{item}</ListGroup.Item>
                        ))}
                    </ListGroup>
                </div>
            )}
        </Container>
    );
};

export default Textract;