import { useNavigate } from 'react-router-dom';
import styles from './CtaSection.module.css';

const CtaSection = () => {
    const navigate = useNavigate();

    return (
        <section className={styles.section}>
            <div className={styles.inner}>
                <p className={styles.tag}>Ready to book?</p>
                <div className={styles.divider} />
                <h2 className={styles.title}>
                    Haz tu estancia <em>inolvidable</em>
                </h2>
                <p className={styles.subtitle}>
                    Disponibilidad limitada — asegura tu habitación con anticipación
                    y disfruta de tarifas exclusivas.
                </p>
                <div className={styles.actions}>
                    <button className={styles.btnPrimary} onClick={() => navigate('/booking')}>
                        Reservar ahora
                    </button>
                    <button className={styles.btnSecondary} onClick={() => navigate('/rooms')}>
                        Ver habitaciones
                    </button>
                </div>
                <p className={styles.note}>Cancelación gratuita hasta 48 hrs antes</p>
            </div>
        </section>
    );
};

export default CtaSection;