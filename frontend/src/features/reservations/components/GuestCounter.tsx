import styles from '../pages/Booking.module.css'

interface GuestCounterProps {
    label: string;
    value: number;
    onDecrement: () => void;
    onIncrement: () => void;
}

const GuestCounter = ({label, value, onDecrement, onIncrement}: GuestCounterProps) => (
    <div className={styles.guestControl}>
        <span className={styles.guestLabel}>{label}</span>
        <div className={styles.counter}>
            <button type="button" onClick={onDecrement}>−</button>
            <span>{value}</span>
            <button type="button" onClick={onIncrement}>+</button>
        </div>
    </div>
);

export default GuestCounter;