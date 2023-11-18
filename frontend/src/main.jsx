import React from 'react'
import ReactDOM from 'react-dom/client'
import { Amplify, Auth } from 'aws-amplify';
import { Authenticator } from '@aws-amplify/ui-react';
import App from './App.jsx'
// import './index.css'
import awsExports from './aws-exports'
import '@aws-amplify/ui-react/styles.css'
import { BrowserRouter , Route, Routes } from 'react-router-dom';
import Translate from './components/Translate.jsx';

Amplify.configure(awsExports);
Auth.configure(awsExports)

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <Authenticator>
            <BrowserRouter>
                <Routes>
                    <Route path="/" exact element={<App />} />
                    <Route path="/app" element={<App />} />
                    <Route path="/translate" element={<Translate />} />
                </Routes>
            </BrowserRouter>
        </Authenticator>
    </React.StrictMode>,
)
