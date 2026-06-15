import styles from './Booking.module.css';
import {useAuth} from "../../auth/hooks/useAuth.ts";
import useRooms from "../../rooms/hooks/useRooms.ts";
import useReservationForm from "../hooks/useReservationForm.ts";
import GuestCounter from "../components/GuestCounter.tsx";
import ReservationSummary from "../components/ReservationSummary.tsx";

const Booking = () => {
    const {token} = useAuth();
    const {rooms, loading, error: fetchError} = useRooms(); // renaming destructuring
    const {
        reservation, selectedRoom, submitting, success,
        formError, nights, total,
        handleChange, changeGuests, handleSubmit,
    } = useReservationForm(rooms, token);

    const today = new Date().toISOString().split('T')[0];

    return (
        <div className={styles.page}>
            <div className={styles.formSide}>
                <p className={styles.eyebrow}>Hotel Reservations</p>
                <h1 className={styles.title}>Reserve your <em>stay</em></h1>
                <p className={styles.subtitle}>Complete the details below and we'll confirm</p>
                <div className={styles.divider}/>

                <form noValidate onSubmit={handleSubmit} method="POST">
                    <p className={styles.sectionLabel}>Stay dates</p>
                    <div className={styles.fieldRow}>
                        <div className={styles.field}>
                            <label>Check-in</label>
                            <input type="date" name="checkIn" min={today}
                                   value={reservation.checkIn} onChange={handleChange}/>
                        </div>
                        <div className={styles.field}>
                            <label>Check-out</label>
                            <input type="date" name="checkOut" min={reservation.checkIn || today}
                                   value={reservation.checkOut} onChange={handleChange}/>
                        </div>
                        <div className={styles.field}>
                            <label>Room type</label>
                            <div className={styles.selectWrap}>
                                <select name="roomId" value={reservation.roomId}
                                        onChange={handleChange} disabled={loading}>
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
                    <p className={styles.sectionLabel}>Guests</p>
                    <div className={styles.guestsRow}>
                        <GuestCounter label="Adults" value={reservation.adults}
                                      onDecrement={() => changeGuests("adults", -1)}
                                      onIncrement={() => changeGuests("adults", 1)}/>
                        <GuestCounter label="Children" value={reservation.children}
                                      onDecrement={() => changeGuests("children", -1)}
                                      onIncrement={() => changeGuests("children", 1)}/>
                    </div>

                    {fetchError && <p style={{color: "#e57373", fontSize: "0.8rem", marginTop: "1rem"}}>Error cargando habitaciones: {fetchError}</p>}
                    {formError  && <p style={{color: "#e57373", fontSize: "0.8rem", marginTop: "1rem"}}>{formError}</p>}
                    {success    && <p style={{color: "#81c784", fontSize: "0.8rem", marginTop: "1rem"}}>Reservation created successfully!</p>}

                    <button className={styles.submitBtn} type="submit" disabled={submitting}>
                        {submitting ? "Processing wait..." : "Confirm Reservation"}
                    </button>
                </form>
            </div>

            <ReservationSummary
                reservation={reservation}
                selectedRoom={selectedRoom}
                nights={nights}
                total={total}
            />
        </div>
    );
};

export default Booking;