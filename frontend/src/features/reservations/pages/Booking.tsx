import reservationService from "../api/reservationService.ts";
import styles from './Booking.module.css'
import {type ChangeEvent, type SyntheticEvent, useEffect, useState} from "react";
import {ROOM_TYPE_LABELS} from "../../rooms/types/models/Room.ts";
import type {IRoom} from "../../rooms/types/models/Room.ts";
import type {ICreateReservation} from "../types/dtos/reservations.dto.create.ts";
import roomsService from "../../rooms/api/roomService.ts";
import {useAuth} from "../../auth/context/AuthContext.tsx";
import {getClientIdFromToken} from "../../auth/utils/tokenUtils.ts";
import {calculateNights} from "../utils/reservationUtils.ts";
import useRooms from "../../rooms/hooks/useRooms.ts"; // <- rooms fetch

const Booking = () => {
    const {token} = useAuth();

    const [rooms, setRooms] = useState<IRoom[]>([]);
    const [selectedRoom, setSelectedRoom] = useState<IRoom | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [submitting, setSubmitting] = useState<boolean>(false);
    const [succes, setSuccess] = useState<boolean>(false);

    const [reservation, setReservation] = useState<ICreateReservation>({
        checkIn: "",
        checkOut: "",
        clientId: getClientIdFromToken(token),
        roomId: 0,
        adults: 1,
        children: 0
    })

    useEffect(() => {
        roomsService.getAll()
            .then(setRooms)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false))
    }, []);


    const today = new Date().toISOString().split('T')[0]; // "2026-06-01"

    return (
        <div className={styles.page}>

            {/* ── FORM ── */}
            <div className={styles.formSide}>
                <p className={styles.eyebrow}>Hotel Reservations</p>
                <h1 className={styles.title}>Reserve your <em>stay</em></h1>
                <p className={styles.subtitle}>Complete the details below and we'll confirm</p>
                <div className={styles.divider}/>

                <form noValidate onSubmit={handleSubmit} method={'POST'}>
                    <p className={styles.sectionLabel}>Stay dates</p>
                    <div className={styles.fieldRow}>
                        <div className={styles.field}>
                            <label>Check-in</label>
                            <input
                                type="date"
                                name={"checkIn"}
                                min={today}    // ← no puede elegir ayer
                                value={reservation.checkIn}
                                onChange={handleChange}/>
                        </div>
                        <div className={styles.field}>
                            <label>Check-out</label>
                            <input
                                type="date"
                                name={"checkOut"}
                                min={reservation.checkIn || today}
                                value={reservation.checkOut}
                                onChange={handleChange}/>
                        </div>

                        <div className={styles.field}>
                            <label>Room type</label>
                            <div className={styles.selectWrap}>
                                <select
                                    name="roomId"
                                    value={reservation.roomId}
                                    onChange={handleChange}
                                    disabled={loading}
                                >
                                    <option value={0} disabled>Select a room</option>
                                    {rooms.map(room => (
                                        <option key={room.id} value={room.id}>
                                            {room.roomType} · ${room.price}/night
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>

                    </div>
                    <hr className={styles.sep}/>

                    <p className={styles.sectionLabel}>Guest</p>
                    <div className={styles.guestsRow}>
                        <div className={styles.guestControl}>
                            <span className={styles.guestLabel}>Adults</span>
                            <div className={styles.counter}>
                                <button type={"button"} onClick={() => changeGuests("adults", -1)}>−</button>
                                <span>{reservation.adults}</span>
                                <button type={"button"} onClick={() => changeGuests("adults", 1)}>+</button>
                            </div>
                        </div>
                        <div className={styles.guestControl}>
                            <span className={styles.guestLabel}>Children</span>
                            <div className={styles.counter}>
                                <button type={"button"} onClick={() => changeGuests("children", -1)}>−</button>
                                <span>{reservation.children}</span>
                                <button type={"button"} onClick={() => changeGuests("children", 1)}>+</button>
                            </div>
                        </div>
                    </div>
                    {error && <p style={{color: "#e57373", fontSize: "0.8rem", marginTop: "1rem"}}>{error}</p>}
                    {succes && <p style={{color: "#81c784", fontSize: "0.8rem", marginTop: "1rem"}}>Reservation created
                        successfully!</p>}
                    <button
                        className={styles.submitBtn}
                        type={"submit"}
                        disabled={submitting}
                    >{submitting ? "Processing wait..." : "Confirm Reservation"}</button>
                </form>

            </div>
            {/* ── SUMMARY ── */}
            <div className={styles.summarySide}>
                <p className={styles.summaryEyebrow}>Reservation Summary</p>

                {selectedRoom ? (
                    <div className={styles.roomCard}>
                        <p className={styles.roomBadge}>{ROOM_TYPE_LABELS[selectedRoom.roomType]}</p>
                        <p className={styles.roomName}>{selectedRoom.name}</p>
                        <p className={styles.roomDesc}>{selectedRoom.description}</p>
                        <div className={styles.anemities}>
                            <span className={styles.tag}>{selectedRoom.capacity} people</span>
                            <span className={styles.tag}>{selectedRoom.size} m²</span>
                        </div>
                    </div>
                ) : (
                    <p className={styles.summaryEmpty}>No room selected yet.</p>
                )}

                <div className={styles.summaryRow}>
                    <span className={styles.summaryKey}>Check-in</span>
                    <span className={styles.summaryVal}>{reservation.checkIn || "—"}</span>
                </div>
                <div className={styles.summaryRow}>
                    <span className={styles.summaryKey}>Check-out</span>
                    <span className={styles.summaryVal}>{reservation.checkOut || "—"}</span>
                </div>
                <div className={styles.summaryRow}>
                    <span className={styles.summaryKey}>Nights</span>
                    <span className={styles.summaryVal}>{nights || "—"}</span>
                </div>
                <div className={styles.summaryRow}>
                    <span className={styles.summaryKey}>Guests</span>
                    <span className={styles.summaryVal}>{reservation.adults} · {reservation.children} children</span>
                </div>

                <div className={styles.priceTotal}>
                    <p className={styles.priceLabel}>Estmated Total</p>
                    <p className={styles.priceAmount}>
                        {total > 0 ? `$${total.toLocaleString()}` : "—"}
                    </p>
                    {nights > 0 && selectedRoom && (
                        <p className={styles.priceNote}>
                            ${selectedRoom.price} × {nights} nights
                        </p>
                    )}
                </div>
            </div>
        </div>
    )
}

export default Booking;