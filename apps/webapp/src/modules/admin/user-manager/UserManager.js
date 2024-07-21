import React, { useState, useEffect } from 'react';
import {
  Box,
  Button,
  IconButton,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
} from '@mui/material';
import { Edit, Delete, Block, Lock } from '@mui/icons-material';

const UserManager = () => {
  const [users, setUsers] = useState([]);
  const [open, setOpen] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    // Replace with your backend API call
    const response = await fetch('/api/users');
    const data = await response.json();
    setUsers(data);
  };

  const handleEdit = (user) => {
    setCurrentUser(user);
    setOpen(true);
  };

  const handleDelete = async (userId) => {
    // Replace with your backend API call to delete the user
    await fetch(`/api/users/${userId}`, { method: 'DELETE' });
    fetchUsers();
  };

  const handleBan = async (userId) => {
    // Replace with your backend API call to ban the user
    await fetch(`/api/users/${userId}/ban`, { method: 'POST' });
    fetchUsers();
  };

  const handleFreeze = async (userId) => {
    // Replace with your backend API call to freeze the user
    await fetch(`/api/users/${userId}/freeze`, { method: 'POST' });
    fetchUsers();
  };

  const handleClose = () => {
    setOpen(false);
    setCurrentUser(null);
  };

  const handleSave = async () => {
    // Replace with your backend API call to save the user changes
    await fetch(`/api/users/${currentUser.id}`, {
      method: 'PUT',
      body: JSON.stringify(currentUser),
      headers: {
        'Content-Type': 'application/json',
      },
    });
    fetchUsers();
    handleClose();
  };

  return (
    <Box>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users.map((user) => (
              <TableRow key={user.id}>
                <TableCell>{user.id}</TableCell>
                <TableCell>{user.name}</TableCell>
                <TableCell>{user.email}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleEdit(user)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(user.id)}>
                    <Delete />
                  </IconButton>
                  <IconButton onClick={() => handleBan(user.id)}>
                    <Block />
                  </IconButton>
                  <IconButton onClick={() => handleFreeze(user.id)}>
                    <Lock />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Edit User</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To edit this user, please modify the fields below and click save.
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            label="Name"
            fullWidth
            value={currentUser?.name || ''}
            onChange={(e) => setCurrentUser({ ...currentUser, name: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Email"
            fullWidth
            value={currentUser?.email || ''}
            onChange={(e) => setCurrentUser({ ...currentUser, email: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSave}>Save</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default UserManager;
