import React, { useState } from 'react';
import { Box, TextField } from '@mui/material';
import { useThemeContext } from '../../../config/theme/themeContext';

const SearchForm = () => {
  const { mode } = useThemeContext();
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearch = (event) => {
    event.preventDefault();
    console.log("Searching for:", searchQuery);
    // Perform search or filtering logic here
  };

  return (
    <Box component="form" onSubmit={handleSearch}>
      <TextField
        variant="outlined"
        size="small"
        placeholder="Search..."
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
        className={mode === 'dark' ? 'placeholder-dark' : 'placeholder-light'}
        sx={{
          mr: 2,
          backgroundColor: 'white',
          borderRadius: '4px',
          input: {
            color: mode === 'dark' ? 'white' : 'black',
          },
        }}
      />
    </Box>
  );
};

export default SearchForm;
