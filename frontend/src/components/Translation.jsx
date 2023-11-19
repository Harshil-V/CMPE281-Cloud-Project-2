import { useState } from 'react';
import { Container, Row, Col, Form, Button, FormControl, Dropdown, DropdownButton } from 'react-bootstrap';


const Translation = () => {
    const [selectedLanguage, setSelectedLanguage] = useState('en'); // Default language is English
    const [inputText, setInputText] = useState('');
    const [outputText, setOutputText] = useState('');
    const [audioUrl, setAudioUrl] = useState('');

    const handleLanguageChange = (language) => {
        setSelectedLanguage(language);
    };

    const handleTranslate = () => {
        // Implement translation logic here
        // You may use a translation API like Google Translate or any other service

        // For demonstration purposes, let's just reverse the input text
        const reversedText = inputText.split('').reverse().join('');
        setOutputText(reversedText);

        // For demonstration purposes, audio URL is set to Google Translate TTS API
        const ttsUrl = `https://translate.google.com/translate_tts?ie=UTF-8&q=${encodeURIComponent(reversedText)}&tl=${selectedLanguage}&total=1&idx=0&textlen=${reversedText.length}`;
        setAudioUrl(ttsUrl);
        console.log(ttsUrl)
    };

    return (
        <Container>
            <Row className="mt-3">
                <Col>
                    <Form>
                        <Form.Group as={Row} controlId="languageDropdown">
                            <Form.Label column sm={2}>
                                Select Language:
                            </Form.Label>
                            <Col sm={4}>
                                <DropdownButton
                                    id="languageDropdown"
                                    title={`Language: ${selectedLanguage.toUpperCase()}`}
                                >
                                    <Dropdown.Item onClick={() => handleLanguageChange('en')}>English</Dropdown.Item>
                                    {/* Add more language options as needed */}
                                </DropdownButton>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row} controlId="inputText">
                            <Form.Label column sm={2}>
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
                        <Form.Group as={Row}>
                            <Col sm={{ span: 10, offset: 2 }}>
                                <Button variant="primary" onClick={handleTranslate} style={{ marginTop: 10 }}>
                                    Translate
                                </Button>
                            </Col>
                        </Form.Group>
                    </Form>
                </Col>
            </Row>
            <Row className="mt-3">
                <Col>
                    <Form.Group controlId="outputText">
                        <Form.Label>Translated Text:</Form.Label>
                        <FormControl as="textarea" rows={3} value={outputText} readOnly />
                    </Form.Group>
                    {audioUrl && (
                        <audio controls>
                            <source src={audioUrl} type="audio/mp3" />
                            Your browser does not support the audio element.
                        </audio>
                    )}
                </Col>
            </Row>
        </Container>
    )
}

export default Translation