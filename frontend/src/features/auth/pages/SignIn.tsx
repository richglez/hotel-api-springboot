import styles from "./SignIn.module.css"
import {Link} from "react-router-dom";

const SignIn = () => {
    return (
        <div className={styles.container}>
            <div className={styles.card}>

                <p className={styles.eyebrow}>Welcome back again!</p>

                <div className={styles.ornament}>
                    <div className={styles.ornamentLine}/>
                    <div className={styles.ornamentDiamond}/>
                    <div className={styles.ornamentLine}/>
                </div>

                <h1 className={styles.brand}>Grand Palacio</h1>
                <p className={styles.sub}>Hotel &amp; Reservations</p>

                <div className={styles.fieldWrap}>
                    <label className={styles.label}>Email</label>
                    <input type="text" className={styles.input} placeholder={"email@domain.com"}/>
                </div>

                <div className={styles.fieldWrap}>
                    <label className={styles.label}>Password</label>
                    <input type="password" className={styles.input} placeholder={"*************"}/>
                </div>

                <div className={styles.forgot}>
                    <a href="#">Forget password?</a>
                </div>

                <button className={styles.btn}>Signin</button>

                <div className={styles.divider}>
                    <span>o</span>
                </div>

                <div className={styles.footer}>
                    <span>Don't have an account?</span>
                    <Link to="/register">Signup here</Link>
                </div>
            </div>
        </div>
    )
}

export default SignIn;