import React, { useState } from 'react';
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
    TextField,
    Switch
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from 'react-router-dom';
import { useThemeContext } from '../../../config/theme/themeContext';

// Personalization
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
    const [anchorEl, setAnchorEl] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');
    const { toggleTheme, mode } = useThemeContext();

    const handleMenu = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleSearch = (event) => {
        event.preventDefault();
        console.log("Searching for:", searchQuery);
        // Perform search or filtering logic here
    };

    const itemList = [
        {
            text: "Home",
            to: "/"
        },
        {
            text: "signup",
            to: "/signup"
        },
        {
            text: "About",
            to: "/about"
        },
        {
            text: "Contact",
            to: "/contact"
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
                <Typography
                    variant="h6"
                    component="h2"
                >
                    HBSales
                </Typography>

                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                    <form onSubmit={handleSearch}>
                        <TextField
                            variant="outlined"
                            size="small"
                            placeholder="Search..."
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            sx={{
                                mr: 2,
                                backgroundColor: 'white',
                                borderRadius: '4px',
                            }}
                        />
                    </form>
                    <Switch
                        checked={mode === 'dark'}
                        onChange={toggleTheme}
                        color="default"
                        inputProps={{ 'aria-label': 'toggle theme' }}
                        sx={{ mr: 2 }}
                    />
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
                            <MenuItem onClick={handleClose} component={Link} to="/signup">
                                Sign Up
                            </MenuItem>
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
                </Box>
            </StyledToolbar>
        </AppBar>
    )
}

export default Navbar;
