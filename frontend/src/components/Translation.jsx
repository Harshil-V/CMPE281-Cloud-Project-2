import { useState } from 'react';
import { Container, Row, Col, Form, Button, FormControl, Dropdown, DropdownButton, Badge } from 'react-bootstrap';
// import '../styles.css'
import axios from 'axios';

const Translation = () => {
    const [selectLanguageCode, setSelectedLanguageCode] = useState('');
    const [selectedLanguage, setSelectedLanguage] = useState(''); // Default language is English
    const [inputText, setInputText] = useState('');
    const [outputText, setOutputText] = useState('');
    const [audioUrl, setAudioUrl] = useState('');
    const [segmentation, setSegmentation] = useState('');


    const handleLanguageChange = (code, language) => {
        setSelectedLanguageCode(code);
        setSelectedLanguage(language);
        console.log(language);
    };


    const callTranslationAPI = async () => {
        console.log(`Text: ${inputText}`)
        console.log(`Code: ${selectLanguageCode}`)
        // setSegmentation('');
        // setAudioUrl('');
        try {
            const response = await axios.post("http://34.213.200.68:80/translate/", {
                text: inputText,
                language: selectLanguageCode
            });

            setOutputText(response.data.translatedText)

            if (response.data.audioStream && response.data.audioStream.data && response.data.audioStream.data.length > 0) {
                const audioData = new Uint8Array(response.data.audioStream.data);
                const audioBlob = new Blob([audioData], { type: 'audio/mp3' });
                const audioURLGen = URL.createObjectURL(audioBlob);
                setAudioUrl(audioURLGen);
                console.log(audioURLGen);
                console.log("Audio URL", audioURLGen)
            } else {
                alert("No Audio Stream Received.")
            }

            setSegmentation(response.data.sentiment);
            console.log(response.data.sentiment)

        } catch (error) {
            console.error("There was an error!", error)
        }
    }

    const handleTranslate = () => {
        if (inputText == '') {
            alert("Input Field is Empty :(")
        } else {
            // Implement translation logic here
            // You may use a translation API like Google Translate or any other service

            // For demonstration purposes, let's just reverse the input text
            // const reversedText = inputText.split('').reverse().join('');
            // setOutputText(reversedText);

            // // For demonstration purposes, audio URL is set to Google Translate TTS API
            // const ttsUrl = `https://translate.google.com/translate_tts?ie=UTF-8&q=${encodeURIComponent(reversedText)}&tl=${selectedLanguage}&total=1&idx=0&textlen=${reversedText.length}`;
            // setAudioUrl(ttsUrl);
            // setAudioUrl("https://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Kangaroo_MusiQue_-_The_Neverwritten_Role_Playing_Game.mp3")
            // setSegmentation("Negative");

            // console.log(ttsUrl);
            callTranslationAPI();
        }
    };

    return (
        // <Container>
        //     <Row className="justify-content-md-center">
        //         <Col xs={12} md={6}>
        //             <Form>
        //                 <Form.Group controlId="exampleForm.ControlTextarea1">
        //                     <Form.Label>Text to Translate</Form.Label>
        //                     <Form.Control as="textarea" rows={3} />
        //                 </Form.Group>
        //                 <Form.Group controlId="exampleForm.ControlSelect1">
        //                     <Form.Label>Select Language</Form.Label>
        //                     <Form.Control as="select">
        //                         <option>US English</option>
        //                         {/* Add other language options here */}
        //                     </Form.Control>
        //                 </Form.Group>
        //                 <Button variant="primary" type="submit">
        //                     Translate
        //                 </Button>
        //             </Form>
        //         </Col>
        //     </Row>
        // </Container>
        <Container className="container" style={{ width: 800, backgroundColor: 'grey', padding: 14, borderRadius: 5, marginTop: 25 }} >
            <center>
                <h2 style={{ paddingLeft: 20 }}>Language Translation</h2>
            </center>
            <Row className="justify-content-md-center m-3 p-2" style={{ backgroundColor: '#D3D3D3', borderRadius: 5 }}>
                <Col>
                    <h3 style={{ paddingLeft: 30 }}>Input</h3>

                    <Form >
                        <Form.Group as={Row} controlId="languageDropdown" style={{
                            paddingLeft: 35, paddingRight: 35
                        }}>
                            <Form.Label column sm={3}>
                                Language:
                            </Form.Label>
                            <Col sm={6}>
                                <DropdownButton
                                    id="languageDropdown"
                                    title={`Language: ${selectedLanguage} - ${selectLanguageCode}`}
                                >
                                    <Dropdown.Item eventKey="US English" onClick={() => handleLanguageChange('en-US', 'US English')}>US English</Dropdown.Item>
                                    <Dropdown.Item eventKey="Indian English" onClick={() => handleLanguageChange('en-IN', 'Indian English')}>Indian English</Dropdown.Item>
                                    <Dropdown.Item eventKey="Spanish" onClick={() => handleLanguageChange('es', 'Spanish')}>Spanish</Dropdown.Item>
                                    <Dropdown.Item eventKey="French" onClick={() => handleLanguageChange('fr', 'French')}>French</Dropdown.Item>
                                    <Dropdown.Item eventKey="Arabic" onClick={() => handleLanguageChange('ar', 'Arabic')}>Arabic</Dropdown.Item>
                                    <Dropdown.Item eventKey="German" onClick={() => handleLanguageChange('de', 'German')}>German</Dropdown.Item>
                                    <Dropdown.Item eventKey="Italian" onClick={() => handleLanguageChange('it', 'Italian')}>Italian</Dropdown.Item>
                                    <Dropdown.Item eventKey="Japanese" onClick={() => handleLanguageChange('ja', 'Japanese')}>Japanese</Dropdown.Item>
                                    <Dropdown.Item eventKey="Hindi" onClick={() => handleLanguageChange('hi', 'Hindi')}>Hindi</Dropdown.Item>
                                    {/* Add more language options as needed */}
                                </DropdownButton>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row} controlId="inputText" style={{ paddingLeft: 35, paddingRight: 35 }}>
                            <Form.Label column sm={3}>
                                Enter Text:
                            </Form.Label>
                            <Col>
                                <FormControl
                                    style={{ marginTop: 10 }}
                                    as="textarea"
                                    rows={3}
                                    value={inputText}
                                    onChange={(e) => setInputText(e.target.value)}
                                />
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row} style={{ paddingLeft: 35, paddingRight: 35 }}>
                            <Col sm={{ span: 10, offset: 6 }}>
                                <Button variant="primary" onClick={handleTranslate} style={{ marginTop: 10 }}>
                                    Translate
                                </Button>
                            </Col>
                        </Form.Group>
                    </Form>
                </Col>
            </Row>
            <Row className="justify-content-md-center m-3" style={{ backgroundColor: '#D3D3D3', borderRadius: 5, paddingTop: 10}}>
                <Col>
                    <h3 style={{ paddingLeft: 30 }}>Output</h3>
                    <Form.Group controlId="outputText" style={{ paddingLeft: 35, paddingRight: 35 }}>
                        <Form.Label>Translated Text:</Form.Label>
                        <FormControl as="textarea" rows={3} value={outputText} readOnly />
                    </Form.Group>
                    <center> <br />
                        {audioUrl && (
                            <audio controls>
                                <source src={audioUrl} type="audio/mp3" />
                            </audio>
                        )}

                        {segmentation && (
                            <div className="p-2" >Sentiment Analysis: &nbsp;
                                <Badge pill bg="primary">
                                    {segmentation}
                                </Badge>
                            </div>
                        )}

                    </center>
                </Col>
            </Row>
        </Container>
    )
}

export default Translation