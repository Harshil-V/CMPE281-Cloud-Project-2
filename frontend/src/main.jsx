import React from 'react'
import ReactDOM from 'react-dom/client'
import { Amplify, Auth } from 'aws-amplify';
import { Authenticator } from '@aws-amplify/ui-react';
import App from './App.jsx'
import './index.css'
import awsExports from './aws-exports'
import '@aws-amplify/ui-react/styles.css'
import { BrowserRouter , Route, Routes } from 'react-router-dom';
import Translate from './components/Translate.jsx';
import ImageGallery from './components/ImageGallary.jsx';
import NotFound from './pages/NotFound.jsx';
import TextAnalysis from './pages/TextAnalyasis.jsx';

Amplify.configure(awsExports);
Auth.configure(awsExports)

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <Authenticator style={{ marginTop: 50 }}>
            <BrowserRouter>
                <Routes>
                    <Route path="/" exact element={<App />} />
                    {/* <Route path="/app" element={<App />} /> */}
                    <Route path="/images" element={<ImageGallery />} />
                    <Route path="/translate" element={<Translate />} />
                    <Route path="/textract" element={<TextAnalysis />} />
                    <Route path="*" element={<NotFound />} />
                </Routes>
            </BrowserRouter>
        </Authenticator>
    </React.StrictMode>,
)
