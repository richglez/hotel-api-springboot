import type {IRoom} from "../types/models/Room.ts";
import {ROOM_TYPE_META} from "../utils/roomTypeMapper.ts";
import {useNavigate} from "react-router-dom";
import styles from "./RoomCard.module.css"; // o desde RoomsPreview si no lo separas aún

// card props
interface RoomCardProps {
    room: IRoom;
}

const RoomCard = ({room}: RoomCardProps) => {
    const navigate = useNavigate();
    const meta = ROOM_TYPE_META[room.roomType] ?? {icon: 'ti-bed', badge: room.roomType}

    return (
        <div className={styles.card}>
            <div className={styles.cardImage}>
                <i className={`ti ${meta.icon}`} aria-hidden="true"/>
                <span className={styles.badge}>{meta.badge}</span>
            </div>
            <div className={styles.cardBody}>
                <p className={styles.cardName}>{room.name}</p>
                <div className={styles.features}>
                    <span className={styles.feature}>
                        <i className="ti ti-users" aria-hidden="true"/>
                        {room.capacity} personas
                    </span>
                    <span className={styles.feature}>
                        <i className="ti ti-ruler-2" aria-hidden="true"/>
                        {room.size} m²
                    </span>
                </div>
                <p className={styles.cardDesc}>{room.description}</p>
                <div className={styles.cardFooter}>
                    <p className={styles.price}>
                        $ {room.price} <span>/ noche</span>
                    </p>
                    <button className={styles.cardLink} onClick={() => navigate('/rooms')}>
                        Ver mas
                    </button>
                </div>
            </div>
        </div>
    );
};

export default RoomCard;
