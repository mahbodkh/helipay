// src/app/modules/public/login/Login.tsx
import React, { useState } from 'react';
import { Button, TextField, Container, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = (event: React.FormEvent) => {
    event.preventDefault();
    // Perform your login logic here
    console.log(username, password);
    // Navigate to the main page upon successful login
    navigate('/');
  };

  return (
    <Container component="main" maxWidth="xs">
      <Typography component="h1" variant="h5">Sign in</Typography>
      <form onSubmit={handleLogin}>
        <TextField
          variant="outlined"
          margin="normal"
          required
          fullWidth
          label="Username"
          autoComplete="username"
          autoFocus
          value={username}
          onChange={e => setUsername(e.target.value)}
        />
        <TextField
          variant="outlined"
          margin="normal"
          required
          fullWidth
          label="Password"
          type="password"
          autoComplete="current-password"
          value={password}
          onChange={e => setPassword(e.target.value)}
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          color="primary"
        >
          Sign In
        </Button>
      </form>
    </Container>
  );
};

export default Login;
