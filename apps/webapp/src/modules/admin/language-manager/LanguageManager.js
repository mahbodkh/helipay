import React, { useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from '@mui/material';

const initialLanguages = [
  { id: 1, name: 'English', code: 'EN' },
  { id: 2, name: 'Spanish', code: 'ES' },
  { id: 3, name: 'French', code: 'FR' },
];

const LanguageManager = () => {
  const [languages, setLanguages] = useState(initialLanguages);
  const [open, setOpen] = useState(false);
  const [editingLanguage, setEditingLanguage] = useState(null);

  const handleOpen = (language) => {
    setEditingLanguage(language);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleDelete = (id) => {
    setLanguages(languages.filter(language => language.id !== id));
  };

  const handleSave = () => {
    if (editingLanguage) {
      setLanguages(languages.map(lang => lang.id === editingLanguage.id ? editingLanguage : lang));
    }
    handleClose();
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setEditingLanguage(prev => ({ ...prev, [name]: value }));
  };

  return (
    <Paper sx={{ width: '100%', overflow: 'hidden' }}>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Code</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {languages.map((language) => (
              <TableRow key={language.id}>
                <TableCell>{language.id}</TableCell>
                <TableCell>{language.name}</TableCell>
                <TableCell>{language.code}</TableCell>
                <TableCell>
                  <Button onClick={() => handleOpen(language)}>Edit</Button>
                  <Button onClick={() => handleDelete(language.id)}>Delete</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      {editingLanguage && (
        <Dialog open={open} onClose={handleClose}>
          <DialogTitle>Edit Language</DialogTitle>
          <DialogContent>
            <TextField
              margin="dense"
              label="Name"
              type="text"
              fullWidth
              variant="standard"
              name="name"
              value={editingLanguage.name}
              onChange={handleChange}
            />
            <TextField
              margin="dense"
              label="Code"
              type="text"
              fullWidth
              variant="standard"
              name="code"
              value={editingLanguage.code}
              onChange={handleChange}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button onClick={handleSave}>Save</Button>
          </DialogActions>
        </Dialog>
      )}
    </Paper>
  );
};

export default LanguageManager;
