// src/app/modules/public/home/Home.tsx
import React, { useState } from 'react';
import { Button, TextField, Container, Typography, Box, Switch } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useThemeContext } from '../../../config/theme/themeContext';



const Home = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { toggleTheme, mode } = useThemeContext();



  const handleSearch = (event: React.FormEvent) => {
    event.preventDefault();
    console.log("Searching for:", searchQuery);
    // Perform search or filtering logic here
  };

  const handleLogin = (event: React.FormEvent) => {
    event.preventDefault();
    console.log("Login attempt with:", username, password);
    // Simulate login and navigate
    navigate('/dashboard'); // Assume a '/dashboard' route for successful login
  };

  return (
    <Container component="main" maxWidth="md">
      <Typography variant="h4" gutterBottom>Welcome to the Main Page</Typography>

      <Box component="form" onSubmit={handleSearch} sx={{ mt: 1 }}>
        <Switch
                checked={mode === 'dark'}
                onChange={toggleTheme}
                color="default"
                inputProps={{ 'aria-label': 'toggle theme' }}
              />
        <TextField
          fullWidth
          label="Search"
          variant="outlined"
          value={searchQuery}
          onChange={e => setSearchQuery(e.target.value)}
          sx={{ mb: 2 }}
        />
        <Button type="submit" variant="contained" color="primary">Search</Button>
      </Box>
      <Typography variant="h6" gutterBottom>Login</Typography>
      <Box component="form" onSubmit={handleLogin} sx={{ mt: 1 }}>
        <TextField
          margin="normal"
          required
          fullWidth
          label="Username"
          value={username}
          onChange={e => setUsername(e.target.value)}
          sx={{ mb: 2 }}
        />
        <TextField
          margin="normal"
          required
          fullWidth
          label="Password"
          type="password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          sx={{ mb: 2 }}
        />
        <Button type="submit" variant="contained" color="primary">Login</Button>
      </Box>
    </Container>
  );
};

export default Home;
