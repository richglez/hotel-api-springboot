import styles from './AmenitiesSection.module.css'

const AMENITIES = [
    {icon: 'ti-wifi', label: 'Wi-Fi gratis'},
    {icon: 'ti-swimming', label: 'Alberca'},
    {icon: 'ti-tools-kitchen-2', label: 'Restaurante'},
    {icon: 'ti-car', label: 'Valet parking'},
    {icon: 'ti-massage', label: 'Spa'},
    {icon: 'ti-barbell', label: 'Gimnasio'},
    {icon: 'ti-air-conditioning', label: 'Aire acondicionado'},
    {icon: 'ti-24-hours', label: 'Servicio 24h'},
];

const AmenitiesSection = () => {
    return(
        <section className={styles.section}>
            <div className={styles.inner}>
                <div className={styles.header}>
                    <p className={styles.tag}>What we offer</p>
                    <div className={styles.divider}/>
                    <h2 className={styles.title}>Amenities</h2>
                </div>
                <div className={styles.grid}>
                    {AMENITIES.map(({ icon, label }) => (
                        <div key={label} className={styles.item}>
                            <i className={`ti ${icon}`} aria-hidden="true"/>
                            <span>{label}</span>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    )
}

export default AmenitiesSection;