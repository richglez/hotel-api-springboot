import styles from './Navbar.module.css';
import {NavLink, useNavigate} from 'react-router-dom';

const Navbar = () => {
    const navigate = useNavigate();
    const token = localStorage.getItem('token')

    const logout = () => {
        localStorage.removeItem('token');
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
                        {token ? (
                            <button style={{border: "none", cursor: "pointer"}} onClick={logout}
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