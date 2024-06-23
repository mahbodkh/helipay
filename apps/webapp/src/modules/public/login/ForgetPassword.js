import React, { useState } from 'react';
import { Box, Button, Container, TextField, Typography } from '@mui/material';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');

  const handleForgotPassword = (event) => {
    event.preventDefault();
    console.log("Forgot password for:", email);
    // Handle forgot password logic
  };

  return (
    <Container component="main" maxWidth="xs">
      <Box
        component="form"
        onSubmit={handleForgotPassword}
        sx={{ mt: 1, display: 'flex', flexDirection: 'column', alignItems: 'center' }}
      >
        <Typography variant="h5">Forgot Password</Typography>
        <TextField
          margin="normal"
          required
          fullWidth
          label="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          sx={{ mb: 2 }}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth>
          Reset Password
        </Button>
      </Box>
    </Container>
  );
};

export default ForgotPassword;
