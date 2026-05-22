import styles from "./Booking.module.css"
import {useEffect, useState} from "react";
import type {IRoom} from "../../rooms/types/models/Room.ts";
import roomsService from "../../rooms/api/roomService.ts";

const Booking = () => {
    const [adults, setAdults] = useState(1);
    const [children, setChildren] = useState(0);
    const [checkIn, setCheckIn] = useState("");
    const [checkOut, setCheckOut] = useState("");
    const [error, setError] = useState<string | null>(null);

    const [rooms, setRooms] = useState<IRoom[]>([])
    const [selectedRoom, setSelectedRoom] = useState<number | null>(null);

    const [client, setClient] = useState({
        name: "",
        email: "",
        phone: ""
    })


    useEffect(() => {

        const fetchRooms = async () => {
            try {
                const data = await roomsService.getAll()
                setRooms(data);
            } catch (err) {
                console.log(err)
            }
        };

        fetchRooms()
        
    }, []);

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
                        <input type="date" value={checkIn} onChange={e => setCheckIn(e.target.value)}/>
                    </div>
                    <div className={styles.field}>
                        <label>Check-out</label>
                        <input type="date" value={checkOut} onChange={e => setCheckOut(e.target.value)}/>
                    </div>
                </div>

                <div className={styles.field}>
                    <label>Room type</label>
                    <div className={styles.selectWrap}>
                        <select value={selectedRoom || ""} onChange={e => setSelectedRoom(Number(e.target.value))}>
                            <option value="">Select a room</option>

                            {rooms}
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
                    ))}
                </div>

                <hr className={styles.sep}/>

                <p className={styles.sectionLabel}>Your details</p>
                <div className={styles.fieldRow}>
                    <div className={styles.field}>
                        <label>First name</label>
                        <input type="text" placeholder={"John"}/>
                    </div>
                    <div className={styles.field}>
                        <label>Last name</label>
                        <input type="text" placeholder={"Doe"}/>
                    </div>
                </div>
                <div className={styles.fieldRow}>
                    <div className={styles.field}>
                        <label>Email</label>
                        <input type="email" placeholder={"john@email.com"}/>
                    </div>
                    <div className={styles.field}>
                        <label>Phone</label>
                        <input type="tel" placeholder={"+1 (555) 000-0000"}/>
                    </div>
                </div>
                <div className={styles.field}>
                    <label>Special request</label>
                    <textarea rows={3} placeholder={"Early check-in, dietary needs, celebrations..."}></textarea>
                </div>

                <button className={styles.submitBtn}>Confirm Reservations</button>
            </div>

            {/* ── SUMMARY ── */}
            <div className={styles.summarySide}>
                <p className={styles.summaryEyebrow}>Reservations Summary</p>

                <div className={styles.roomCard}>
                    <p className={styles.roomBadge}>Selected Room</p>
                    <p className={styles.roomName}>Room</p>
                    <p className={styles.roomDesc}>Desc</p>
                </div>
            </div>

        </div>
    )
}

export default Booking;