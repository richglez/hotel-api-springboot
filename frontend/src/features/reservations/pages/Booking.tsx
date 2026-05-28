import reservationService from "../api/reservationService.ts";
import styles from './Booking.module.css'
import {type ChangeEvent, useState} from "react";
import type {IRoom} from "../../rooms/types/models/Room.ts";
import type {ICreateReservation} from "../types/dtos/reservations.dto.create.ts";

const Booking = () => {
    const [rooms, setRooms] = useState<IRoom[]>([])
    const [reservation, setReservation] = useState<ICreateReservation>({
        checkIn: "",
        checkOut: "",
        clientId: 0,
        roomId: 0
    })

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;

        setReservation(prev => ({...prev, [name]: value})); // implicit return

    }

    return (
        <div className={styles.page}>

            {/* ── FORM ── */}
            <div className={styles.formSide}>
                <p className={styles.eyebrow}>Hotel Reservations</p>
                <h1 className={styles.title}>Reserve your <em>stay</em></h1>
                <p className={styles.subtitle}>Complete the details below and we'll confirm</p>
                <div className={styles.divider}/>

                <form noValidate action="">
                    <p className={styles.sectionLabel}>Stay dates</p>
                    <div className={styles.fieldRow}>
                        <div className={styles.field}>
                            <label>Check-in</label>
                            <input type="date" value={reservation.checkIn}/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Booking;