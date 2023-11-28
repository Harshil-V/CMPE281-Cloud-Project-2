import { useState, useEffect } from 'react';
import axios from 'axios';
import { Card, Button, Form, Container, Row, Col, Pagination, Badge, Modal } from 'react-bootstrap';
import { Auth } from 'aws-amplify';
import DeleteButton from './DeleteButton';

const ImageGallery = () => {
    const [images, setImages] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [imagesPerPage] = useState(8);
    const [filter, setFilter] = useState('');
    const [newImage, setNewImage] = useState(null);
    const [authUser, setAuthUser] = useState("");
    const [authUserEmail, setAuthUserEmail] = useState("");
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [imageToDelete, setImageToDelete] = useState(null);

    const [userData, setUserData] = useState("");
    // const [error, setError] = useState(null);

    // useEffect(() => {
    //     const fetchData = async () => {
    //         const endpoint = "http://35.165.94.33:8080/file/getAllFiles";
    //         // const userEmail = "miyarkarthikkamath@gmail.com";
    //         // const payload = ({
    //         //     userEmail: userEmail,
    //         // });

    //         // let data = JSON.stringify({
    //         //     "userEmail": "miyarkarthikkamath@gmail.com"
    //         // });

    //         // let config = {
    //         //     method: 'get',
    //         //     maxBodyLength: Infinity,
    //         //     url: 'http://35.165.94.33:8080/file/getUserFilesDetails',
    //         //     headers: {
    //         //         'Content-Type': 'application/json'
    //         //     },
    //         //     data: data
    //         // };

    //         // axios.request(config)
    //         //     .then((response) => {
    //         //         console.log(JSON.stringify(response.data));
    //         //     })
    //         //     .catch((error) => {
    //         //         console.log(error);
    //         //     });

    //         try {
    //             const response = await axios.get(endpoint);
    //             setUserData(response.data);
    //             console.log(userData);
    //         } catch (error) {
    //             // setError(error.message || 'An error occurred');
    //             console.error('Error fetching user data:', error);
    //         }
    //     };

    //     fetchData();
    // }, []);

    useEffect(() => {

        Auth.currentAuthenticatedUser({
            bypassCache: false  // Optional, By default is false. If set to true, this call will send a request to Cognito to get the latest user data
        }).then(user => {
            // console.log(user.attributes.email)
            setAuthUserEmail(user.attributes.email)
            setAuthUser(user.username)
            fetchData();
        }).catch(err => {
            console.log(err)
        });



        const fetchData = async () => {
            if (authUserEmail && authUser) {
                const endpoint = `http://35.165.94.33:8080/file/getUserFilesDetails/${authUserEmail}`;
                console.log(endpoint)
                // const userEmail = "miyarkarthikkamath@gmail.com";
                // const payload = ({
                //     userEmail: userEmail,
                // });
                try {
                    const response = await axios.get(endpoint);
                    setUserData(response.data);
                    console.log(response.data);
                    console.log(userData);
                    setImages(response.data);
                } catch (error) {
                    // setError(error.message || 'An error occurred');
                    console.error('Error fetching user data:', error);
                }
            }

        };




        // Fake mock data for testing
        // const generateMockData = () => {
        //     const mockData = [];

        //     for (let i = 1; i <= 32; i++) {
        //         mockData.push({
        //             id: i,
        //             desc: "",
        //             userEmail: "",
        //             uploadDate: "",
        //             fileName: "",
        //             url: `https://via.placeholder.com/300?text=Image${i}`,
        //             tags: [`tag${i % 5}`, `tag${(i + 1) % 5}`],
        //         });
        //     }

        //     return mockData;
        // };

        // Set mock data to the state
        // setImages(generateMockData());
        // setImages(userData);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [authUserEmail]);

    const indexOfLastImage = currentPage * imagesPerPage;
    const indexOfFirstImage = indexOfLastImage - imagesPerPage;

    // const filteredImages = images.filter(image => image.tags.some(tag => tag.tagName.includes(filter)));
    // const currentImages = filteredImages.slice(indexOfFirstImage, indexOfLastImage);

    const currentImages = images
        .filter(
            (image) =>
                image.tags &&
                Array.isArray(image.tags) &&
                image.tags.some((tag) => tag.tagName.includes(filter))
        )
        .slice(indexOfFirstImage, indexOfLastImage);


    // const currentImages = images
    //     .filter(image => image.tags.some(tag => tag.tagName.includes(filter)))
    //     .slice(indexOfFirstImage, indexOfLastImage);

    // const currentImages = images
    //     .filter(image => image.tags.some(tag => tag.includes(filter)))
    //     .slice(indexOfFirstImage, indexOfLastImage);

    const paginate = pageNumber => setCurrentPage(pageNumber);

    const handleDelete = () => {
        // Implement the logic to delete the image with the given ID
        // You may need to make an API request to delete the image on the server
        // and then update the local state
        // For simplicity, let's assume that there's a deleteImage function
        // that deletes the image from the state
        const updatedImages = images.filter(image => image.id !== imageToDelete);
        setImages(updatedImages);

        // Close the confirmation modal
        closeConfirmationModal();
    };

    // const handleDelete = (id) => {
    //     // Implement the logic to delete the image with the given id
    //     // You may need to make an API request to delete the image on the server
    //     // and then update the local state
    //     // For simplicity, let's assume that there's a deleteImage function
    //     // that deletes the image from the state
    //     const updatedImages = images.filter(image => image.id !== id);
    //     setImages(updatedImages);
    // };

    const handleImageUpload = async (e) => {
        e.preventDefault();
        if (!newImage) {
            alert('Please select an image to upload.');
            return;
        }
        const description = window.prompt(`Please enter a description for the image file - '${newImage.name}':`);

        if (description === null) {
            return;
        }

        if (description.trim() === '') {
            alert('Description is required. Please try again.');
            return;
        }

        const fileEntity = {
            "fileDesc": description,
            "versionNo": "1",
            "uploadDate": new Date().toISOString().split('T')[0],
            "updateDate": new Date().toISOString().split('T')[0],
            "userEmail": authUserEmail
        }

        console.log(authUser);

        const formData = new FormData();
        formData.append('file', newImage);
        formData.append('fileDesc', description);
        formData.append('versionNo', 1);
        formData.append('userEmail', authUserEmail);

        // formData.append('fileEntity', fileEntity);
        // formData.append('fileName', newImage.name);
        // formData.append('userEmail', authUserEmail)
        // formData.append('userName', authUser);
        // formData.append('description', description);
        // formData.append('uploadDate', new Date());
        // formData.append('updateDate', new Date());

        // console.log(newImage);
        // console.log(`fileName: ${formData.get('fileName')}`);
        // console.log(`description: ${formData.get('description')}`);
        // console.log(`userEmail: ${formData.get('userEmail')}`);
        // console.log(`userName: ${formData.get('userName')}`);
        // console.log(`uploadDate: ${formData.get('uploadDate')}`);
        // console.log(`updateDate: ${formData.get('updateDate')}`);
        console.log(fileEntity)
        // for (const value of formData.values()) {
        //     console.log(value);
        // }

        try {
            const response = await axios.post('http://35.165.94.33:8080/file/uploadFile', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            console.log(response);
            // Assuming the server responds with the new image data
            alert(`Upload successful`)
            const newImageData = response.data;
            setImages([...images, newImageData]);
            setNewImage(null); // Clear the file input after a successful upload
            location.reload();
        } catch (error) {
            console.error('Error uploading image:', error);
        }
    };

    const openConfirmationModal = (id) => {
        setImageToDelete(id);
        setShowConfirmation(true);
    };

    const closeConfirmationModal = () => {
        setImageToDelete(null);
        setShowConfirmation(false);
    };


    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];

        if (selectedFile && selectedFile.size > 20 * 1024 * 1024) {
            alert('File size exceeds 20MB. Please choose a smaller file.');
        } else {
            setNewImage(selectedFile);
        }
    };

    // const handleDeleteConfirmation = (id) => {
    //     const isConfirmed = window.confirm('Are you sure you want to delete this image?');

    //     if (isConfirmed) {
    //         handleDelete(id);
    //     }
    // };

    return (
        <Container className='mt-4'>
            <h1>Image Gallary</h1>

            {/* {userData &&
                <ul>
                    {userData.map((file, i) => (
                        <li key={i}>
                            <p>File ID: {file.fileId}</p>
                            <p>File Name: {file.fileName}</p>
                            <p>File Description: {file.fileDesc}</p>
                            <p>File URL: <a href={file.fileURL} target="_blank" rel="noopener noreferrer">{file.fileURL}</a></p>
                            <p>Upload Date: {file.uploadDate}</p>
                            <p>User Email: {file.userEmail}</p>
                        </li>
                    ))}
                </ul>
            } */}


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
                {currentImages.map((image, i) => (
                    <Col key={i} xs={12} sm={6} md={4} lg={3} className="mb-4">
                        <Card className="image-card">
                            <Card.Img variant="top" src={image.fileURL} alt={`Image ${image.fileName}`} loading='lazy' style={{ width: '100%', height: '14vw', objectFit: 'cover'}}/>
                            <Card.Body>

                                <div className="tags mb-2">
                                    {image.tags && Array.isArray(image.tags) && image.tags.slice(0, 4).map((tag, i) => (
                                        <Badge key={i} variant="secondary" style={{ marginRight: 5, padding: 8 }}>
                                            {tag.tagName}
                                        </Badge>
                                    ))}
                                </div>

                                <div className='button-group'>
                                    <DeleteButton onClick={() => openConfirmationModal(image.fileName)}>
                                        Delete
                                    </DeleteButton>
                                    <Button className='mt-2' style={{ marginLeft: 5, backgroundColor: "#1DA1F2", borderColor: "#1DA1F2" }}>
                                        <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 512 512" fill='#ffffff'>

                                            <path d="M307 34.8c-11.5 5.1-19 16.6-19 29.2v64H176C78.8 128 0 206.8 0 304C0 417.3 81.5 467.9 100.2 478.1c2.5 1.4 5.3 1.9 8.1 1.9c10.9 0 19.7-8.9 19.7-19.7c0-7.5-4.3-14.4-9.8-19.5C108.8 431.9 96 414.4 96 384c0-53 43-96 96-96h96v64c0 12.6 7.4 24.1 19 29.2s25 3 34.4-5.4l160-144c6.7-6.1 10.6-14.7 10.6-23.8s-3.8-17.7-10.6-23.8l-160-144c-9.4-8.5-22.9-10.6-34.4-5.4z" /></svg>{/* <svg xmlns="http://www.w3.org/2000/svg" width="" height="16" fill="currentColor" className="bi bi-twitter" viewBox="0 0 16 16">
                                            <path d="M5.026 15c6.038 0 9.341-5.003 9.341-9.334 0-.14 0-.282-.006-.422A6.685 6.685 0 0 0 16 3.542a6.658 6.658 0 0 1-1.889.518 3.301 3.301 0 0 0 1.447-1.817 6.533 6.533 0 0 1-2.087.793A3.286 3.286 0 0 0 7.875 6.03a9.325 9.325 0 0 1-6.767-3.429 3.289 3.289 0 0 0 1.018 4.382A3.323 3.323 0 0 1 .64 6.575v.045a3.288 3.288 0 0 0 2.632 3.218 3.203 3.203 0 0 1-.865.115 3.23 3.23 0 0 1-.614-.057 3.283 3.283 0 0 0 3.067 2.277A6.588 6.588 0 0 1 .78 13.58a6.32 6.32 0 0 1-.78-.045A9.344 9.344 0 0 0 5.026 15" />
                                        </svg> */}
                                    </Button>
                                </div>

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

            <Modal show={showConfirmation} onHide={closeConfirmationModal} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Confirmation</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Are you sure you want to delete this image?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeConfirmationModal}>
                        Cancel
                    </Button>
                    <Button variant="danger" onClick={handleDelete}>
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default ImageGallery;
