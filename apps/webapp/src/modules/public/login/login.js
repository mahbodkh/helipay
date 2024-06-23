import React, { useState } from 'react';
import Navbar from '../navbar/Navbar';
import { Box, Button, Container, TextField, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import { useThemeContext } from '../../../config/theme/themeContext';

const Login = () => {
  const { toggleTheme, mode } = useThemeContext();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = (event) => {
    event.preventDefault();
    console.log("Login attempt with:", username, password);
    // Handle login logic
  };

  return (
    <>
      <Navbar />
      <Container component="main" maxWidth="xs">
        <Box
          component="form"
          onSubmit={handleLogin}
          sx={{ mt: 1, display: 'flex', flexDirection: 'column', alignItems: 'center' }}
        >
          <Typography variant="h5">Login</Typography>
          <TextField
            margin="normal"
            required
            fullWidth
            label="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            sx={{ mb: 2 }}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            label="Password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            sx={{ mb: 2 }}
          />
          <Button type="submit" variant="contained" color="primary" fullWidth>
            Login
          </Button>
          <Box mt={2} display="flex" justifyContent="space-between" width="100%">
            <Button
              component={Link}
              to="/login/forgot-password"
              color="primary"
            >
              Forgot Password
            </Button>
            <Button
              component={Link}
              to="/signup"
              color="primary"
            >
              Signup
            </Button>
          </Box>
        </Box>
      </Container>
    </>
  );
};

export default Login;
