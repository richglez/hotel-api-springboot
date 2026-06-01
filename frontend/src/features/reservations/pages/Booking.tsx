import reservationService from "../api/reservationService.ts";
import styles from './Booking.module.css'
import {type ChangeEvent, type SyntheticEvent, useEffect, useState} from "react";
import {ROOM_TYPE_LABELS} from "../../rooms/types/models/Room.ts";
import type {IRoom} from "../../rooms/types/models/Room.ts";
import type {ICreateReservation} from "../types/dtos/reservations.dto.create.ts";
import roomsService from "../../rooms/api/roomService.ts";
import {useAuth} from "../../auth/context/AuthContext.tsx";

// Decodificar el JWT para leer el id del usuario autenticado.
const getClientIdFromToken = (token: string | null): number => {
    if (!token) return 0;
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.id ?? 0;
    } catch {
        return 0;
    }
};


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


// ✅ Limpio — un solo setState por caso
    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {name, value} = e.target;

        if (name === "roomId") {
            const numericId = Number(value);
            const found = rooms.find(r => r.id === numericId) ?? null;
            setSelectedRoom(found);
            setReservation(prev => ({...prev, roomId: numericId})); // number desde el inicio
        } else {
            setReservation(prev => ({...prev, [name]: value}));
        }
    };

    const changeGuests = (field: "adults" | "children", delta: number): void => {
        setReservation(prev => ({
            ...prev,
            [field]: Math.max(0, (prev[field] ?? 0) + delta),
        }));
    };

    const today = new Date().toISOString().split('T')[0]; // "2026-06-01"

    const handleSubmit = async (e: SyntheticEvent<HTMLFormElement>) => {

        e.preventDefault();
        // validation before send
        if (!reservation.checkIn || !reservation.checkOut || reservation.roomId === 0) {
            setError("Please complete check-in, check-out and select a room.")
            return;
        }

        // server wait local date time value
        const payload: ICreateReservation = {
            ...reservation,
            checkIn: `${reservation.checkIn}T12:00:00`,
            checkOut: `${reservation.checkOut}T12:00:00`,
        };

        if (reservation.checkIn && reservation.checkOut) {
            if (new Date(reservation.checkOut) <= new Date(reservation.checkIn)) {
                setError("Check-out must be after check-in")
                return;
            }
        }

        try {
            setSubmitting(true);
            setError(null);
            await reservationService.create(payload);
            setSuccess(true);
        } catch (err: any) {
            setError(err.message ?? "Failed at create reservation.")
        } finally {
            setSubmitting(false); // finalmente para el caso try y catch ya no se esta mandando
        }
    };

    const nights = reservation.checkIn && reservation.checkOut
        ? Math.max(
            0,
            Math.ceil(
                (new Date(reservation.checkOut).getTime() - new Date(reservation.checkIn).getTime())
                / (1000 * 60 * 60 * 24)
            )
        ) : 0;

    const total = selectedRoom ? selectedRoom.price * nights : 0;

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