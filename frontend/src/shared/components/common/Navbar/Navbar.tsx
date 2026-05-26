import styles from './Navbar.module.css';
import {NavLink, useNavigate} from 'react-router-dom';
import {useAuth} from "../../../../features/auth/context/AuthContext.tsx";

const Navbar = () => {
    const {logout, isAuthenticated} = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/')
    }

    return (
        <div className={styles.navWrapper}>
            <nav className={styles.nav}>
                <NavLink to={"/"} className={styles.logo}>
                    Hotel-<span>Reservations</span>
                </NavLink>
                <ul className={styles.links}>
                    <li>
                        <NavLink to="/" className={styles.link}>Home</NavLink>
                    </li>
                    <li>
                        <NavLink to="/rooms" className={styles.link}>Rooms</NavLink>
                    </li>
                    <li>
                        <NavLink to="/booking" className={styles.cta}>Book now</NavLink>
                    </li>
                    <li>
                        {isAuthenticated ? (
                            <button style={{border: "none", cursor: "pointer"}} onClick={handleLogout}
                                    className={styles.cta}>Log out</button>
                        ) : (
                            <NavLink to="/login" className={styles.cta}>Sign In</NavLink>
                        )}
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default Navbar;