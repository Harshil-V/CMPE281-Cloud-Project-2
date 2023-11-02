import React from 'react'
import ReactDOM from 'react-dom/client'
import { Amplify, Auth } from 'aws-amplify';
import { Authenticator } from '@aws-amplify/ui-react';
import App from './App.jsx'
import './index.css'
import awsExports from './aws-exports'
import '@aws-amplify/ui-react/styles.css'

Amplify.configure(awsExports);
Auth.configure(awsExports)

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <Authenticator>
            <App />
        </Authenticator>
    </React.StrictMode>,
)
