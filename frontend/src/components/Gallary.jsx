import { useState, useEffect } from 'react';
// import axios from 'axios';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Image from 'react-bootstrap/Image';
import Row from 'react-bootstrap/Row';
import Badge from 'react-bootstrap/Badge';
import Stack from 'react-bootstrap/Stack';

const Gallery = () => {
    const [images, setImages] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);



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

    const filteredImages = selectedCategories.length === 0 ? images : images.filter(image =>
        image.categories.some(category => selectedCategories.includes(category))
    );

    return (
        <div>
            <div className="filter-buttons">
                <center>
                    <h1>Image Gallary</h1>

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
            <div className="image-grid" style={{ display: "flex" }}>
                {
                    filteredImages.map(image => (
                        <div key={image.id} className="image" style={{ display: "block", width: 300, padding: 15 }}>
                            <Container>
                                <Row>
                                    <Col xs={2} md={4}>
                                        <Image src={image.image} style={{ width: 171, height: 180 }} loading='lazy' rounded />

                                        <Stack direction="horizontal" gap={2} style={{paddingTop: 4}}>
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
