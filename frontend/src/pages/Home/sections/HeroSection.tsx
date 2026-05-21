import {useNavigate} from "react-router-dom";
import styles from './HeroSection.module.css'

const HeroSection = () => {
    const navigate = useNavigate()

    return (
        <section className={styles.hero}>
            <p className={styles.tag}>Welcome to Hotel Reservations</p>
            <div className={styles.divider}></div>
            <h1 className={styles.title}>An unforgettable <em>stay</em><br/>waiting for you! </h1>
            <p className={styles.subtitle}>
                Discover luxury and comfort in the heart of the city. Every detail designed for your well-being.
            </p>
            <div className={styles.actions}>
                <button className={styles.btnPrimary} onClick={() => navigate('/booking')}>
                    Book now
                </button>
                <button className={styles.btnSecondary} onClick={() => navigate('/rooms')}>
                    View rooms
                </button>
            </div>
            {/*Stats*/}
            <div className={styles.stats}>
                <div className={styles.stat}>
                    <p className={styles.statNumber}>48</p>
                    <p className={styles.statLabel}>Rooms</p>
                </div>
                <div>
                    <p className={styles.statNumber}>4.9</p>
                    <p className={styles.statLabel}>Rating</p>
                </div>
                <div>
                    <p className={styles.statNumber}>12</p>
                    <p className={styles.statLabel}>Years of experience</p>
                </div>
            </div>
        </section>
    );
}

export default HeroSection;