import { Link } from 'react-router-dom';
import { Container, Row, Col, Button } from 'react-bootstrap';
import '../NotFound.css'; 

const NotFound = () => {
    return (
        <>
            <center>
                <Container className="not-found-container" >
                    <Row >
                        <Col>
                            <div className="not-found-content" style={{marginTop: 200}}>
                                <h1>404</h1>
                                <h2>Page Not Found</h2>
                                <p>The page you are looking for might be in another castle.</p>
                                <Link to="/">
                                    <Button variant="primary">Go Home</Button>
                                </Link>
                            </div>
                        </Col>
                    </Row>
                </Container>
            </center>
        </>
    );
};

export default NotFound;
