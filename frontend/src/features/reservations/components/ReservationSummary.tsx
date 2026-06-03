import type {IRoom} from "../../rooms/types/models/Room.ts";
import type {ICreateReservation} from "../types/dtos/reservations.dto.create.ts";
import {ROOM_TYPE_LABELS} from "../../rooms/types/models/Room.ts";
import styles from '../pages/Booking.module.css'

interface ReservationSummaryProps {
    reservation: ICreateReservation;
    selectedRoom: IRoom | null;
    nights: number;
    total: number;
}

const ReservationSummary = ({reservation, selectedRoom, nights, total}: ReservationSummaryProps) => (
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
            <p className={styles.priceLabel}>Estimated Total</p>
            <p className={styles.priceAmount}>{total > 0 ? `$${total.toLocaleString()}` : "—"}</p>
            {nights > 0 && selectedRoom && (
                <p className={styles.priceNote}>${selectedRoom.price} × {nights} nights</p>
            )}
        </div>
    </div>
);

export default ReservationSummary;