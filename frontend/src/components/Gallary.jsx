import { useState, useEffect } from 'react';
// import axios from 'axios';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Image from 'react-bootstrap/Image';
import Row from 'react-bootstrap/Row';
import Badge from 'react-bootstrap/Badge';
import Stack from 'react-bootstrap/Stack';
import Form from 'react-bootstrap/Form';
import FormControl from 'react-bootstrap/FormControl';
// import { Button } from 'react-bootstrap';


const Gallery = () => {
    const [images, setImages] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    // const [selectedFile, setSelectedFile] = useState(null);
    const [file, setFile] = useState(null);


    useEffect(() => {
        // Fetch image data from data.json (or your API)
        // axios.get('/data.json')
        //     .then(response => {
        //         setImages(response.data);
        //     });
        setImages([
            {
                "id": 1,
                "categories": ["nature", "landscape"],
                "image": "https://i0.wp.com/picjumbo.com/wp-content/uploads/beautiful-nature-mountain-scenery-with-flowers-free-photo.jpg"
            },
            {
                "id": 2,
                "categories": ["city"],
                "image": "https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg"
            },
            {
                "id": 3,
                "categories": ["space"],
                "image": "https://webb.nasa.gov/content/webbLaunch/assets/images/images2023/oct-30-23-STScI-01HBBMJ8R6HTXP5W1EVEJ24D64-1K.jpg"
            },
            {
                "id": 4,
                "categories": ["nature", "landscape"],
                "image": "https://i0.wp.com/picjumbo.com/wp-content/uploads/beautiful-nature-mountain-scenery-with-flowers-free-photo.jpg"
            },
            {
                "id": 5,
                "categories": ["city"],
                "image": "https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg"
            },
            {
                "id": 6,
                "categories": ["space"],
                "image": "https://webb.nasa.gov/content/webbLaunch/assets/images/images2023/oct-30-23-STScI-01HBBMJ8R6HTXP5W1EVEJ24D64-1K.jpg"
            }
            // Add more images with image URLs
        ])
    }, []);

    const toggleCategory = (category) => {
        if (selectedCategories.includes(category)) {
            setSelectedCategories(selectedCategories.filter(cat => cat !== category));
        } else {
            setSelectedCategories([...selectedCategories, category]);
        }
    };

    const isCategorySelected = (category) => selectedCategories.includes(category) ? true : false;

    const handleSearchChange = (event) => {
        setSearchQuery(event.target.value);
    };

    // const handleFileChange = (e) => {
    //     const file = e.target.files[0];

    //     if (file) {
    //         // Check file size and type
    //         if (file.size <= 20 * 1024 * 1024 && file.type.startsWith('image/')) {
    //             setSelectedFile(file);
    //         } else {
    //             // Reset selected file if it doesn't meet the criteria
    //             setSelectedFile(null);
    //             alert('Please select a valid image file (<= 20MB).');
    //         }
    //     }
    // };

    // const handleUpload = () => {
    //     // Perform upload logic here using selectedFile
    //     if (selectedFile) {
    //         // Implement your upload logic (e.g., send to server)
    //         console.log('File uploaded:', selectedFile);
    //     } else {
    //         alert('Please select a file before uploading.');
    //     }
    // };

    const handleFileChange = (event) => {
        const selectedFile = event.target.files[0];
        if (selectedFile) {
            if (selectedFile.size <= 10 * 1024 * 1024) {
                setFile(selectedFile);
            } else {
                setFile(null);
                alert('File size exceeds 10MB limit. Please select a smaller file.');
            }
        }
    };

    const handleFileUpload = () => {
        // Handle the file upload logic, e.g., read the file content using FileReader API
        if (file) {
            console.log('File Uploaded:', file);
            // const reader = new FileReader();
            // reader.onload = (e) => {
            //     // e.target.result contains the file content (e.g., base64 data or text)
            //     // You can process the file content or upload it to a server
            //     console.log('File content:', e.target.result);
            // };
            // reader.readAsDataURL(file);
        } else {
            alert('Please select a file before uploading.');
        }
    };


    const filteredImages = images.filter(image => {
        const matchesCategories = selectedCategories.length === 0 || image.categories.some(category => selectedCategories.includes(category));
        const matchesSearchQuery = image.image.toLowerCase().includes(searchQuery.toLowerCase())
            || image.categories.some(category => category.toLowerCase().includes(searchQuery.toLowerCase()));

        return matchesCategories && matchesSearchQuery;
    });


    // const filteredImages = selectedCategories.length === 0 ? images : images.filter(image =>
    //     image.categories.some(category => selectedCategories.includes(category))
    // );

    return (
        <div>
            <div className="filter-buttons">
                <center>
                    <div>
                        <h1>Image Gallary</h1>

                    </div>

                    <Form style={{ maxWidth: 500, marginTop: 5, display: 'flex', marginLeft: 80 }}>
                        <Form.Group controlId="formFile" className="mb-3 p-1">
                            <Form.Control type="file" accept="image/*" onChange={handleFileChange} />
                        </Form.Group>
                        <Form.Group controlId="formButton" className="mb-3 p-1">
                            <Form.Control type="button" value="Upload" onClick={handleFileUpload} />
                        </Form.Group>
                    </Form>

                    <label style={{ marginRight: 10 }}>
                        <input
                            style={{ marginRight: 3, marginTop: 5 }}
                            type="checkbox"
                            value="nature"
                            checked={isCategorySelected('nature')}
                            onChange={() => toggleCategory('nature')}
                        />
                        Nature
                    </label>
                    <label style={{ marginLeft: 10 }}>
                        <input
                            style={{ marginRight: 3, marginTop: 5 }}
                            type="checkbox"
                            value="city"
                            checked={isCategorySelected('city')}
                            onChange={() => toggleCategory('city')}
                        />
                        City
                    </label>
                </center>
                {/* Add more category checkboxes */}
            </div>
            <div >
                <Form className="mb-3 p-1">
                    <center>
                        <FormControl
                            style={{ maxWidth: 500, marginTop: 5 }}
                            type="text"
                            placeholder="Search Image Tags"
                            className="mr-sm-2"
                            value={searchQuery}
                            onChange={handleSearchChange}
                        />
                    </center>
                </Form>
            </div>
            {/* <Form.Group>
                <Form.File
                    id="custom-file"
                    label="Choose File"
                    custom
                    onChange={handleFileChange}
                />
            </Form.Group>
            <Button
                variant="primary"
                disabled={!selectedFile}
                onClick={handleUpload}
            >
                Upload
            </Button> */}

            <div className="image-grid" style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))", gap: "8px", marginLeft: 25 }}>
                {
                    filteredImages.map(image => (
                        <div key={image.id} className="image" style={{ width: 300, padding: 10 }}>
                            <Container>
                                <Row>
                                    <Col xs={2} md={4}>
                                        <Image src={image.image} style={{ width: 171, height: 180 }} loading='lazy' rounded />

                                        <Stack direction="horizontal" gap={2} style={{ paddingTop: 4 }}>
                                            {
                                                image.categories.map(tag => (
                                                    <Badge key={tag.id} bg="primary">{tag}</Badge>
                                                ))
                                            }
                                        </Stack>
                                    </Col>
                                </Row>
                            </Container>
                            {/* <img src={image.image} style={{ width: 450, height: 450, margin: 5 }} alt={image.categories.join(', ')} /> */}
                        </div>
                    ))}
            </div>
        </div>
    );
};

export default Gallery;
