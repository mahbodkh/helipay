import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from '../pages/Home';
import Signup from '../pages/Signup';
import NotFound from '../pages/NotFound';



import ForgetPassword from '../pages/ForgetPassword';
import Login from '../modules/public/login/login';
import UserDashboard from '../modules/public/dashboard/dashboard';

import AdminDashboard from '../pages/admin/Dashboard';
import AdminUsers from '../pages/admin/users/Users';
import AdminUsersEdit from '../pages/admin/users/UsersEdit';

import LanguageManager from '../modules/admin/language-manager/LanguageManager';

const AppRouter = () => {
  return (
    <Router>
      <Routes>
        <Route path="*" element={<NotFound />} />

        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/login/forgot-password" element={<ForgetPassword />} />

        <Route path="/signup" element={<Signup />} />

        <Route path="/users/:id/dashboard" element={<UserDashboard />} />

        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/admin/users" element={<AdminUsers />} />
        <Route path="/admin/users/:id/edit" element={<AdminUsersEdit />} />

        <Route path="/admin/language-manager" element={<LanguageManager />} />
        <Route path="/admin/language-manager/:id" element={<LanguageManager />} />

      </Routes>
    </Router>
  );
}

export default AppRouter;
