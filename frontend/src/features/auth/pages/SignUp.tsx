import styles from "./SignUp.module.css"
import {Link} from "react-router-dom";

const SignUp = () => {
    return (
        <div className={styles.container}>
            <div className={styles.card}>

                <p className={styles.eyebrow}>Create your account</p>

                <div className={styles.ornament}>
                    <div className={styles.ornamentLine}/>
                    <div className={styles.ornamentDiamond}/>
                    <div className={styles.ornamentLine}/>
                </div>

                <h1 className={styles.brand}>Grand Palacio</h1>
                <p className={styles.sub}>Hotel &amp; Reservations</p>

                <div className={styles.fieldWrap}>
                    <label className={styles.label}>Email</label>
                    <input type="email" className={styles.input} placeholder="email@domain.com"/>
                </div>

                {/* Name y LastName en grid de dos columnas */}
                <div className={styles.row}>
                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Name</label>
                        <input type="text" className={styles.input} placeholder="John"/>
                    </div>
                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Last name</label>
                        <input type="text" className={styles.input} placeholder="Doe"/>
                    </div>
                </div>

                <div className={styles.fieldWrap}>
                    <label className={styles.label}>Password</label>
                    <input type="password" className={styles.input} placeholder="••••••••••••"/>
                    <p className={styles.hint}>Minimum 8 characters</p>
                </div>

                <button className={styles.btn}>Create account</button>

                <div className={styles.divider}>
                    <span>o</span>
                </div>

                <div className={styles.footer}>
                    <span>Already have an account?</span>
                    <Link to="/login">Sign in</Link>
                </div>
            </div>
        </div>
    )
}

export default SignUp