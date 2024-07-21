import React from 'react';
import Navbar from '../modules/public/navbar/Navbar';

const NotFoundPage = () => {
    return (
    <>
    <Navbar />
        <div>
            <h1>404 - Not Found!</h1>
            <p>Sorry, the page you are looking for does not exist.</p>
        </div>
    </>
    );

};

export default NotFoundPage;
