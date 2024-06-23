import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NotFoundPage from '../modules/public/notFound';
import Home from '../pages/Home';
import Signup from '../pages/Signup';
import Login from '../modules/public/login/login';
import UserDashboard from '../modules/public/dashboard/dashboard';

import AdminDashboard from '../modules/admin/dashboard/dashboard';
import LanguageManager from '../modules/admin/language-manager/language-manager';

const AppRouter = () => {
  return (
    <Router>
      <Routes>
        <Route path="*" element={<NotFoundPage />} />

        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/dashboard" element={<UserDashboard />} />

        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/admin/language-manager" element={<LanguageManager />} />
        <Route path="/admin/language-manager/:id" element={<LanguageManager />} />

      </Routes>
    </Router>
  );
}

export default AppRouter;
