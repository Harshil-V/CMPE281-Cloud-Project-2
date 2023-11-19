// import { useState } from "react";
import NavigationBar from "./NavBar";
import Translation from "./Translation";
// import { Container, Row, Col, Form, Button } from "react-bootstrap";

const Translate = () => {
    // const [inputText, setInputText] = useState('');
    // const [targetLanguage, setTargetLanguage] = useState('');
    // // const [translatedOutput, setTranslatedOutput] = useState('');

    // const handleTranslate = () => {
    //     // Implement translation logic here
    //     // You can use an API or any other method for translation
    //     // For simplicity, let's just copy the input text for now
    //     // setTranslatedOutput(inputText);
    //     console.log(targetLanguage);
    // };


    return (
        <>
            <div>
                <NavigationBar />
            </div>
            <div>
                <Translation />
            </div>
        </>

    )
}

export default Translate