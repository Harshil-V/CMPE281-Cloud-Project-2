// ImageGallery.js
import { useState, useEffect } from 'react';
// import axios from 'axios';
import { Card, Button, Form, Container, Row, Col, Pagination, Badge } from 'react-bootstrap';
import { Auth } from 'aws-amplify';

const ImageGallery = () => {
    const [images, setImages] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [imagesPerPage] = useState(8);
    const [filter, setFilter] = useState('');
    const [newImage, setNewImage] = useState(null);
    const [authUser, setAuthUser] = useState("");

    useEffect(() => {

        Auth.currentAuthenticatedUser({
            bypassCache: false  // Optional, By default is false. If set to true, this call will send a request to Cognito to get the latest user data
        }).then(user => {
            // console.log(user)
            setAuthUser(user.username)
        }).catch(err => {
            console.log(err)
        });


        // Fake mock data for testing
        const generateMockData = () => {
            const mockData = [];

            for (let i = 1; i <= 32; i++) {
                mockData.push({
                    id: i,
                    url: `https://via.placeholder.com/300?text=Image${i}`,
                    tags: [`tag${i % 5}`, `tag${(i + 1) % 5}`],
                });
            }

            return mockData;
        };

        // Set mock data to the state
        setImages(generateMockData());
    }, []);

    const indexOfLastImage = currentPage * imagesPerPage;
    const indexOfFirstImage = indexOfLastImage - imagesPerPage;
    const currentImages = images
        .filter(image => image.tags.some(tag => tag.includes(filter)))
        .slice(indexOfFirstImage, indexOfLastImage);

    const paginate = pageNumber => setCurrentPage(pageNumber);

    const handleDelete = (id) => {
        // Implement the logic to delete the image with the given id
        // You may need to make an API request to delete the image on the server
        // and then update the local state
        // For simplicity, let's assume that there's a deleteImage function
        // that deletes the image from the state
        const updatedImages = images.filter(image => image.id !== id);
        setImages(updatedImages);
    };

    const handleImageUpload = async () => {
        if (!newImage) {
            alert('Please select an image to upload.');
            return;
        }

        const formData = new FormData();
        formData.append('image', newImage);
        formData.append('user_name', authUser);
        console.log(formData.get('image'));
        console.log(formData.get('user_name'));
        // try {
        //     const response = await axios.post('https://your-api-endpoint.com/upload', formData, {
        //         headers: {
        //             'Content-Type': 'multipart/form-data',
        //         },
        //     });

        //     // Assuming the server responds with the new image data
        //     const newImageData = response.data;
        //     setImages([...images, newImageData]);
        //     setNewImage(null); // Clear the file input after a successful upload
        // } catch (error) {
        //     console.error('Error uploading image:', error);
        // }
    };

    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];

        if (selectedFile && selectedFile.size > 20 * 1024 * 1024) {
            alert('File size exceeds 20MB. Please choose a smaller file.');
        } else {
            setNewImage(selectedFile);
        }
    };

    const handleDeleteConfirmation = (id) => {
        const isConfirmed = window.confirm('Are you sure you want to delete this image?');
        
        if (isConfirmed) {
          handleDelete(id);
        }
      };

    return (
        <Container className='mt-4'>
            <h1>Image Gallary</h1>

            <Row>
                <Col className='mb-3'>
                    <Form className="filter-form">
                        {/* Filter Input */}
                        <Form.Group controlId="filterInput" style={{ marginTop: 10 }}>
                            <Form.Label>Filter by Tag:</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter tag"
                                value={filter}
                                onChange={(e) => setFilter(e.target.value)}
                            />
                        </Form.Group>
                    </Form>
                </Col>
                <Col>
                    <Form className="upload-form" >
                        {/* File Upload Input */}
                        <Form.Group controlId="uploadInput" className="d-flex" style={{ marginTop: 40 }}>
                            <Form.Control
                                type="file"
                                accept="image/*"
                                onChange={handleFileChange}
                                className="mr-2"
                            />
                            <Button style={{ marginLeft: 15 }} variant="primary" onClick={handleImageUpload}>Upload</Button>
                        </Form.Group>
                    </Form>
                </Col>

            </Row>

            {/* Image Gallery */}
            <Row>
                {currentImages.map(image => (
                    <Col key={image.id} xs={12} sm={6} md={4} lg={3} className="mb-4">
                        <Card className="image-card">
                            <Card.Img variant="top" src={image.url} alt={`Image ${image.id}`} loading='lazy' />
                            <Card.Body>

                                <div className="tags mb-2" >
                                    {image.tags.map(tag => (
                                        <Badge key={tag} variant="secondary" style={{ marginRight: 5 }}>
                                            {tag}
                                        </Badge>
                                    ))}
                                </div>
                                <Button variant="danger" onClick={() => handleDeleteConfirmation(image.id)}>Delete</Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            {/* Pagination */}
            <center>
                <Pagination>

                    {Array.from({ length: Math.ceil(images.length / imagesPerPage) }).map((_, index) => (
                        <Pagination.Item
                            key={index + 1}
                            active={index + 1 === currentPage}
                            onClick={() => paginate(index + 1)}
                        >
                            {index + 1}
                        </Pagination.Item>
                    ))}

                </Pagination>
            </center>

        </Container>
    );
};

export default ImageGallery;
