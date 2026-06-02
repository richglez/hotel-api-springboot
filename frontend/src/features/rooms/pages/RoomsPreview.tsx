import styles from "./RoomsPreview.module.css"
import {useNavigate} from "react-router-dom";
import useRooms from "../hooks/useRooms.ts";
import RoomCard from "../components/RoomCard.tsx";

const RoomsPreview = () => {
    const navigate = useNavigate();
    const {rooms, loading, error} = useRooms();

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
                    <RoomCard key={room.id} room={room}/>
                ))}
            </div>
            <div className={styles.footer}>
                <button className={styles.allRoomsBtn} onClick={() => navigate('/rooms')}>Ver todas las habitaciones
                </button>
            </div>
        </section>

    )
}

export default RoomsPreview;