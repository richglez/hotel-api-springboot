import styles from "./Booking.module.css"
import {useState} from "react";

const Booking = () => {
    const [adults, setAdults] = useState(1);
    const [children, setChildren] = useState(0);

    return (
        <div className={styles.page}>
            {/* ── FORM ── */}
            <div className={styles.formSide}>
                <p className={styles.eyebrown}>Hotel Reservations</p>
                <h1 className={styles.title}>Reserve your <em>stay</em></h1>
                <p className={styles.subtitle}>Complete the details below and we'll confirm within 24 hours.</p>
                <div className={styles.divider}/>

                <p className={styles.sectionLabel}>Stay dates</p>
                <div className={styles.fieldRow}>
                    <div className={styles.field}>
                        <label>Check-in</label>
                        <input type="date"/>
                    </div>
                    <div className={styles.field}>
                        <label>Check-out</label>
                        <input type="date"/>
                    </div>
                </div>

                <div className={styles.field}>
                    <label>Room type</label>
                    <div className={styles.selectWrap}>
                        <select>
                            <option value="">Select a room</option>
                            <option value="standard">Standard Room — $120/night</option>
                            <option value="deluxe">Deluxe Room — $180/night</option>
                            <option value="suite">Junior Suite — $260/night</option>
                            <option value="presidential">Presidential Suite — 480/night</option>
                        </select>
                    </div>
                </div>

                <p className={styles.sectionLabel}>Guests</p>
                <div className={styles.guestsRow}>
                    {[
                        {label: 'Adults', value: adults, min: 1, set: setAdults},
                        {label: 'Children', value: children, min: 0, set: setChildren}
                    ].map(({label, value, min, set}) => (
                        <div className={styles.guestControl} key={label}>
                            <span className={styles.guestLabel}>{label}</span>
                            <div className={styles.counter}>
                                <button onClick={() => set(v => Math.max(min, v - 1))}>-</button>
                                <span>{value}</span>
                                <button onClick={() => set(v => v + 1)}>+</button>
                            </div>
                        </div>
                    ))
                    }
                </div>
            </div>

        </div>
    )
}

export default Booking;