import styles from './Navbar.module.css';
import {NavLink} from 'react-router-dom';

const Navbar = () => {
    return (
        <div className={styles.navWrapper }>
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
                        <NavLink to="/signin" className={styles.cta}>Sign In</NavLink>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default Navbar;