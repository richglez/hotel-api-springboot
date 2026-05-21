import {useState, useEffect} from "react";
import type {IRoom} from "../../types/Room.ts";
import roomsService from "../../services/roomService.ts";
import styles from "./RoomsPreview.module.css"
import {ROOM_TYPE_META} from "../../utils/roomTypeMapper.ts"
import {useNavigate} from "react-router-dom";

const RoomsPreview = () => {
    const navigate = useNavigate();

    const [rooms, setRooms] = useState<IRoom[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        roomsService.getAll()
            .then(setRooms)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false))
    })

    if (loading) return <p>Cargando...</p>
    if (error) return <p>Error: {error}</p>

    return (
        <section className={styles.section}>
            <div className={styles.header}>
                <p className={styles.tag}>Our rooms</p>
                <div className={styles.divider}></div>
                <h2 className={styles.title}>Discover your ideal space</h2>
            </div>
            <div className={styles.grid}>
                {rooms.map((room) => {
                        const meta = ROOM_TYPE_META[room.roomType] ?? { icon: 'ti-bed', badge: room.roomType };
                    console.log('roomType:', room.roomType, '→ meta:', meta);
                        return (
                            <div key={room.id} className={styles.card}>
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
                                        <p className={styles.price}>$ {room.price} <span>/ noche</span></p>
                                        <button className={styles.cardLink} onClick={() => navigate('/rooms')}>Ver mas
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )
                    }
                )
                }
            </div>
            <div className={styles.footer}>
                <button className={styles.allRoomsBtn} onClick={() => navigate('/rooms')}>Ver todas las habitaciones
                </button>
            </div>
        </section>

    )
}

export default RoomsPreview;