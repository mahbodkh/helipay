import React from 'react';
import {
  AppBar,
  Toolbar,
  Box,
  List,
  ListItem,
  Typography,
  styled,
  ListItemButton,
  ListItemText,
  IconButton,
  Menu,
  MenuItem,
  Switch
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from 'react-router-dom';
import SearchForm from './SearchForm';
import { useThemeContext } from '../../../config/theme/themeContext';

// personalizacao
const StyledToolbar = styled(Toolbar)({
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
});

const ListMenu = styled(List)(({ theme }) => ({
  display: 'none',
  [theme.breakpoints.up("sm")]: {
    display: "flex",
  },
}));

const Navbar = () => {
  const { toggleTheme, mode } = useThemeContext();
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const itemList = [
    {
      text: "Dashboard",
      to: "/admin"
    },
     {
       text: "Users",
       to: "/admin/users"
     },
    {
      text: "Products",
      to: "/products"
    }
  ];

  return (
    <AppBar
      component="nav"
      position="sticky"
      sx={{
        backgroundColor: 'orange',
      }}
      elevation={0}
    >
      <StyledToolbar>
      <Box sx={{ display: { xs: 'none', sm: 'flex' }, alignItems: 'left' }}>

        <Typography
          variant="h6"
          component="h2"
        >
          HelyPay
        </Typography>
        <Typography
          variant="h10"
          component="h2"
        >
          Admin Dashboard
        </Typography>
        </Box>
        <Box sx={{ display: { xs: 'none', sm: 'flex' }, alignItems: 'center' }}>
          <SearchForm />
          <Switch
            checked={mode === 'dark'}
            onChange={toggleTheme}
            color="default"
            inputProps={{ 'aria-label': 'toggle theme' }}
            sx={{ mr: 2 }}
          />
        </Box>
        <Box sx={{ display: { xs: 'block', sm: 'none' } }}>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="menu"
            onClick={handleMenu}
          >
            <MenuIcon />
          </IconButton>
          <Menu
            anchorEl={anchorEl}
            anchorOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            keepMounted
            transformOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            open={Boolean(anchorEl)}
            onClose={handleClose}
          >
            {itemList.map((item) => (
              <MenuItem key={item.text} onClick={handleClose} component={Link} to={item.to}>
                {item.text}
              </MenuItem>
            ))}
          </Menu>
        </Box>
        <ListMenu>
          {itemList.map((item) => {
            const { text } = item;
            return (
              <ListItem key={text}>
                <ListItemButton component={Link} to={item.to}
                  sx={{
                    color: '#fff',
                    "&:hover": {
                      backgroundColor: 'transparent',
                      color: '#1e2a5a',
                    }
                  }}
                >
                  <ListItemText primary={text} />
                </ListItemButton>
              </ListItem>
            )
          })}
        </ListMenu>
      </StyledToolbar>
    </AppBar>
  )
}

export default Navbar;
