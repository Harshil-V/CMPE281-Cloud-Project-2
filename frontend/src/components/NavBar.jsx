import { useState } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Auth } from 'aws-amplify'

async function signOut() {
    try {
        await Auth.signOut();
    } catch (error) {
        console.log('error signing out: ', error);
    }
}

function NavigationBar() {
    const [authUser, setAuthUser] = useState("");

    Auth.currentAuthenticatedUser({
        bypassCache: false  // Optional, By default is false. If set to true, this call will send a request to Cognito to get the latest user data
    }).then(user => {
        console.log(user.attributes.email)
        setAuthUser(user.username)
    }).catch(err => {
        console.log(err)
    });


    return (
        <Navbar collapseOnSelect expand="lg" className="bg-body-tertiary" data-bs-theme="dark">
            <Container>
                <Navbar.Brand href="/">ExploreEase</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/">Images</Nav.Link>
                        <Nav.Link href="/translate">Translate</Nav.Link>
                        <Nav.Link href="/textract">OCR</Nav.Link>
                    </Nav>
                    <Nav>
                        <Nav.Link>User: {authUser}</Nav.Link>
                        <Nav.Link style={{  padding: 6, color: 'white', backgroundColor: "darkred", borderRadius: 10, width: 'fit-content' }} onClick={signOut}>
                            Log Out
                        </Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default NavigationBar;