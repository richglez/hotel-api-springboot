import {useState, useEffect} from "react";
import type {IRoom} from "../../types/Room.ts";
import roomsService from "../../services/roomService.ts";
import styles from "./RoomsPreview.module.css"

const RoomsPreview = () => {
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
                {rooms.map((room) => (
                    <div key={room.id} className={styles.card}>
                        {/*<i className={`ti ${room.icon}`}></i>*/}
                        <span >{room.roomType}</span>
                        <div className={styles.cardBody}>
                            <p className={styles.cardName}>{room.name}</p>
                        </div>
                    </div>
                ))}
            </div>

        </section>

    )
}

export default RoomsPreview;